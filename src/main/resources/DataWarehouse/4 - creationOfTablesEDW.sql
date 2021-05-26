

--Create Dim Tables
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[edw].[DimGreenhouse]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[edw].[DimGreenhouse](
	G_ID INT IDENTITY PRIMARY KEY NOT NULL,
	greenhouseId INT NOT NULL,
	greenhouseName NCHAR VARYING(100) NOT NULL
	);

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[edw].[DimMeasurementType]') AND type in (N'U'))



CREATE TABLE [GrinHouseDW].[edw].[DimMeasurementType](
	title NCHAR VARYING(20) PRIMARY KEY NOT NULL CHECK(title IN ('temperature', 'humidity', 'carbonDioxide'))
);


--Create DimDate
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[edw].[DimDate]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[edw].[DimDate] (
 D_ID INT NOT NULL,
 Date DATE,
 Day INT,
 Month INT,
 MonthName NCHAR VARYING(10),
 Week INT,
 Quarter INT,
 Year INT,
 DayOfWeek INT,
 WeekdayName NCHAR VARYING(10)
CONSTRAINT [PK_DimDate]  PRIMARY KEY CLUSTERED
 (
	[D_ID] ASC
 )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]

/* Adding data from start of times until end of times, for 100 years */
DECLARE @StartDate DATETIME;
DECLARE @EndDate DATETIME;

SET @StartDate = 2021-01-01
SET @EndDate = DATEADD(YEAR, 100, getdate())


WHILE @StartDate <= @EndDate
BEGIN
	INSERT INTO [GrinHouseDW].[edw].[DimDate]
	(
		[D_ID],
		[Date],
		[Day],
		[Month],
		[MonthName],
		[Week],
		[Quarter],
		[Year],
		[DayOfWeek],
		[WeekdayName]
	)
	SELECT
		CONVERT(CHAR(8), @StartDate, 112) as D_ID,
		@StartDate as [Date],
		DATEPART(day, @StartDate) as Day,
		DATEPART(month, @StartDate) as Month,
		DATENAME(month, @StartDate) as MonthName,
		DATEPART(week, @StartDate) as Week,
		DATEPART(QUARTER, @StartDate) as Quarter,
		DATEPART(YEAR, @StartDate) as Year,
		DATEPART(WEEKDAY, @StartDate) as DayOfWeek,
		DATENAME(weekday, @StartDate) as WeekdayName

	SET @StartDate = DATEADD(dd, 1, @StartDate)
END



--Create DimTime
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[edw].[DimTime]') AND type in (N'U'))

-- Then create a new table
CREATE TABLE [GrinHouseDW].[edw].[DimTime](
    [T_ID] [int] IDENTITY(1,1) PRIMARY KEY NOT NULL,
    [Time] [time](0) NULL,
    [Hour] [int] NULL,
    [Minute] [int] NULL,
	[Seconds] [int] NULL
);
 
-- Needed if the dimension already existed
-- with other column, otherwise the validation
-- of the insert could fail.
GO
 
-- Create a time and a counter variable for the loop
DECLARE @Time as time;
SET @Time = '0:00:00';
 
DECLARE @counter as int;
SET @counter = 0;
 
 
 
-- Loop 1440 times (24hours * 60minutes)
WHILE @counter < 86400
BEGIN

    INSERT INTO [GrinHouseDW].[edw].[DimTime] ([Time]
                       , [Hour]
                       , [Minute]
					   , [Seconds])

                VALUES (@Time
                       , DATEPART(Hour, @Time) + 1
                       , DATEPART(Minute, @Time) + 1
					   , DATEPART(Second, @Time) + 1
                       );
 
    -- Raise time with one minute
    SET @Time = DATEADD(SECOND, 1, @Time);
 
    -- Raise counter by one
    set @counter = @counter + 1;
END


--Create Fact tables
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[edw].[FactMeasurementTemperature]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[edw].[FactMeasurementTemperature](
	G_ID INT NOT NULL,
	MT_ID NCHAR VARYING(20)NOT NULL,
	D_ID INT NOT NULL,
	T_ID INT NOT NULL,
	measurementId INT,
	measuredValue FLOAT,
	isHeaterOn BIT NOT NULL,
	isCoolerOn BIT NOT NULL
);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[edw].[FactMeasurementHumidity]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[edw].[FactMeasurementHumidity](
	G_ID INT NOT NULL,
	MT_ID NCHAR VARYING(20) NOT NULL,
	D_ID INT NOT NULL,
	T_ID INT NOT NULL,
	measurementId INT NOT NULL,
	measuredValue FLOAT NOT NULL,
	isHumidifierOn BIT NOT NULL,
	isDehumidifierOn BIT NOT NULL
);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[edw].[FactMeasurementCarbonDioxide]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide](
	G_ID INT NOT NULL,
	MT_ID NCHAR VARYING(20) NOT NULL,
	D_ID INT NOT NULL,
	T_ID INT NOT NULL,
	measurementId INT,
	measuredValue FLOAT,
	isCarbonDioxideGeneratorOn BIT NOT NULL
);

--adding foreign surrogate keys in [FactMeasurementTemperature]
ALTER TABLE [GrinHouseDW].[edw].[FactMeasurementTemperature] ADD CONSTRAINT FK_G_ID_Temperature FOREIGN KEY (G_ID) REFERENCES [GrinHouseDW].[edw].[DimGreenhouse] (G_ID);
ALTER TABLE [GrinHouseDW].[edw].[FactMeasurementTemperature] ADD CONSTRAINT FK_MT_ID_Temperature FOREIGN KEY (MT_ID) REFERENCES [GrinHouseDW].[edw].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[edw].[FactMeasurementTemperature] ADD CONSTRAINT FK_D_ID_Temperature FOREIGN KEY (D_ID) REFERENCES [GrinHouseDW].[edw].[DimDate] (D_ID);
ALTER TABLE [GrinHouseDW].[edw].[FactMeasurementTemperature] ADD CONSTRAINT FK_T_ID_Temperature FOREIGN KEY (T_ID) REFERENCES [GrinHouseDW].[edw].[DimTime] (T_ID);

--adding foreign surrogate keys in [FactMeasurementHumidity]
ALTER TABLE [GrinHouseDW].[edw].[FactMeasurementHumidity] ADD CONSTRAINT FK_G_ID_Humidity FOREIGN KEY (G_ID) REFERENCES [GrinHouseDW].[edw].[DimGreenhouse] (G_ID);
ALTER TABLE [GrinHouseDW].[edw].[FactMeasurementHumidity] ADD CONSTRAINT FK_MT_ID_Humidity FOREIGN KEY (MT_ID) REFERENCES [GrinHouseDW].[edw].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[edw].[FactMeasurementHumidity] ADD CONSTRAINT FK_D_ID_Humidity FOREIGN KEY (D_ID) REFERENCES [GrinHouseDW].[edw].[DimDate] (D_ID);
ALTER TABLE [GrinHouseDW].[edw].[FactMeasurementHumidity] ADD CONSTRAINT FK_T_ID_Humidity FOREIGN KEY (T_ID) REFERENCES [GrinHouseDW].[edw].[DimTime] (T_ID);

--adding foreign surrogate keys in [FactMeasurementCarbonDioxide]
ALTER TABLE [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_G_ID_CarbonDioxide FOREIGN KEY (G_ID) REFERENCES [GrinHouseDW].[edw].[DimGreenhouse] (G_ID);
ALTER TABLE [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_MT_ID_CarbonDioxide FOREIGN KEY (MT_ID) REFERENCES [GrinHouseDW].[edw].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_D_ID_CarbonDioxide FOREIGN KEY (D_ID) REFERENCES [GrinHouseDW].[edw].[DimDate] (D_ID);
ALTER TABLE [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_T_ID_CarbonDioxide FOREIGN KEY (T_ID) REFERENCES [GrinHouseDW].[edw].[DimTime] (T_ID);

/*
--adding the surrogate keys are primary key
alter table [GrinHouseDW].[edw].[FactMeasurementTemperature] add constraint PK_FactMeasurementTemperature PRIMARY KEY(G_ID, MT_ID, D_ID, T_ID)
alter table [GrinHouseDW].[edw].[FactMeasurementHumidity] add constraint PK_FactMeasurementHumidity PRIMARY KEY(G_ID, MT_ID, D_ID, T_ID)
alter table [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] add constraint PK_FactMeasurementCarbonDioxide PRIMARY KEY(G_ID, MT_ID, D_ID, T_ID)

alter table [GrinHouseDW].[edw].[FactMeasurementTemperature] drop constraint PK_FactMeasurementTemperature
alter table [GrinHouseDW].[edw].[FactMeasurementHumidity] drop constraint PK_FactMeasurementHumidity
alter table [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] drop constraint PK_FactMeasurementCarbonDioxide


drop table [GrinHouseDW].[edw].[DimACState]
drop table [GrinHouseDW].[edw].[DimHumidifierState]
drop table [GrinHouseDW].[edw].[DimCarbonDioxideState]

alter table [GrinHouseDW].[edw].[FactMeasurementTemperature] drop constraint FK_G_ID_Temperature
alter table [GrinHouseDW].[edw].[FactMeasurementTemperature] drop constraint FK_MT_ID_Temperature
alter table [GrinHouseDW].[edw].[FactMeasurementTemperature] drop constraint FK_D_ID_Temperature
alter table [GrinHouseDW].[edw].[FactMeasurementTemperature] drop constraint FK_T_ID_Temperature

alter table [GrinHouseDW].[edw].[FactMeasurementHumidity] drop constraint PK_FactMeasurementHumidity
alter table [GrinHouseDW].[edw].[FactMeasurementHumidity] drop constraint FK_G_ID_Humidity
alter table [GrinHouseDW].[edw].[FactMeasurementHumidity] drop constraint FK_MT_ID_Humidity
alter table [GrinHouseDW].[edw].[FactMeasurementHumidity] drop constraint FK_D_ID_Humidity
alter table [GrinHouseDW].[edw].[FactMeasurementHumidity] drop constraint FK_T_ID_Humidity

alter table [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] drop constraint FK_G_ID_CarbonDioxide
alter table [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] drop constraint FK_MT_ID_CarbonDioxide
alter table [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] drop constraint FK_D_ID_CarbonDioxide
alter table [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] drop constraint FK_T_ID_CarbonDioxide


drop table [GrinHouseDW].[edw].[FactMeasurementTemperature];
drop table [GrinHouseDW].[edw].[FactMeasurementHumidity];
drop table [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide];

alter table [GrinHouseDW].[edw].[FactMeasurementHumidity] drop constraint FK_H_ID_Humidity

alter table [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] drop constraint FK_H_ID_CarbonDioxide

*/

