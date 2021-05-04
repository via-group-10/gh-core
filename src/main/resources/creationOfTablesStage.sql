

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[DimGreenhouse]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[DimGreenhouse](
	greenhouseId INT PRIMARY KEY NOT NULL,
	greenhouseName NCHAR VARYING(100) NOT NULL
	);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[DimMeasurementType]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[DimMeasurementType](
	title NCHAR VARYING(20) PRIMARY KEY NOT NULL CHECK(title IN ('temperature', 'humidity', 'carbonDioxide'))
);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[FactMeasurementTemperature]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature](
	measurementId INT,
	measuredValue FLOAT,
	measurementDateTime DATETIME,
	isHeaterOn BIT NOT NULL,
	isCoolerOn BIT NOT NULL,
	greenhouseId INT,
	title NCHAR VARYING(20)
);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[FactMeasurementHumidity]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[FactMeasurementHumidity](
	measurementId INT,
	measuredValue FLOAT,
	measurementDateTime DATETIME,
	isHumidifierOn BIT NOT NULL,
	isDehumidifierOn BIT NOT NULL,
	greenhouseId INT,
	title NCHAR VARYING(20)
);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[FactMeasurementCarbonDioxide]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide](
	measurementId INT,
	measuredValue FLOAT,
	measurementDateTime DATETIME,
	isCarbonDioxideGeneratorOn BIT NOT NULL,
	greenhouseId INT,
	title NCHAR VARYING(20),
);


ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_titleTemperature FOREIGN KEY (title) REFERENCES [GrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_greenhouseIdTemperature FOREIGN KEY (greenhouseId) REFERENCES [GrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);

ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementHumidity] ADD CONSTRAINT FK_titleHumidity FOREIGN KEY (title) REFERENCES [GrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementHumidity] ADD CONSTRAINT FK_greenhouseIdHumidity FOREIGN KEY (greenhouseId) REFERENCES [GrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);

ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_titleCarbonDioxide FOREIGN KEY (title) REFERENCES [GrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_greenhouseIdCarbonDioxide FOREIGN KEY (greenhouseId) REFERENCES [GrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);


/*
alter table [GrinHouseDW].[stage].[FactMeasurementTemperature] drop constraint FK_titleTemperature
alter table [GrinHouseDW].[stage].[FactMeasurementTemperature] drop constraint FK_greenhouseIdTemperature

alter table [GrinHouseDW].[stage].[FactMeasurementHumidity] drop constraint FK_titleHumidity
alter table [GrinHouseDW].[stage].[FactMeasurementHumidity] drop constraint FK_greenhouseIdHumidity

alter table [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] drop constraint FK_titleCarbonDioxide
alter table [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] drop constraint FK_greenhouseIdCarbonDioxide

ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_title FOREIGN KEY (title) REFERENCES [GrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_greenhouseId FOREIGN KEY (greenhouseId) REFERENCES [GrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);

alter table [GrinHouseDW].[stage].[FactMeasurementTemperature] drop constraint FK_acStateId
alter table [GrinHouseDW].[stage].[FactMeasurementHumidity] drop constraint FK_humidifierId
alter table [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] drop constraint FK_carbonDioxideGeneratorId

DROP TABLE [GrinHouseDW].[stage].[DimGreenhouse];
DROP TABLE [GrinHouseDW].[stage].[DimMeasurementType];
DROP TABLE [GrinHouseDW].[stage].[DimACState];
DROP TABLE [GrinHouseDW].[stage].[DimHumidifierState];
DROP TABLE [GrinHouseDW].[stage].[DimCarbonDioxideState];
DROP TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature];
DROP TABLE [GrinHouseDW].[stage].[FactMeasurementHumidity];
DROP TABLE [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide];
*/