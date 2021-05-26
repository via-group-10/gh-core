

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[TestGrinHouseDW].[stage].[DimGreenhouse]') AND type in (N'U'))

CREATE TABLE [TestGrinHouseDW].[stage].[DimGreenhouse](
	greenhouseId INT PRIMARY KEY NOT NULL,
	greenhouseName NCHAR VARYING(100) NOT NULL
	);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[TestGrinHouseDW].[stage].[DimMeasurementType]') AND type in (N'U'))

CREATE TABLE [TestGrinHouseDW].[stage].[DimMeasurementType](
	title NCHAR VARYING(20) PRIMARY KEY NOT NULL CHECK(title IN ('temperature', 'humidity', 'carbonDioxide'))
);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[TestGrinHouseDW].[stage].[FactMeasurementTemperature]') AND type in (N'U'))

CREATE TABLE [TestGrinHouseDW].[stage].[FactMeasurementTemperature](
	measurementId INT,
	measuredValue FLOAT,
	measurementDateTime DATETIME,
	isHeaterOn BIT NOT NULL,
	isCoolerOn BIT NOT NULL,
	greenhouseId INT,
	title NCHAR VARYING(20)
);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[TestGrinHouseDW].[stage].[FactMeasurementHumidity]') AND type in (N'U'))

CREATE TABLE [TestGrinHouseDW].[stage].[FactMeasurementHumidity](
	measurementId INT,
	measuredValue FLOAT,
	measurementDateTime DATETIME,
	isHumidifierOn BIT NOT NULL,
	isDehumidifierOn BIT NOT NULL,
	greenhouseId INT,
	title NCHAR VARYING(20)
);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[TestGrinHouseDW].[stage].[FactMeasurementCarbonDioxide]') AND type in (N'U'))

CREATE TABLE [TestGrinHouseDW].[stage].[FactMeasurementCarbonDioxide](
	measurementId INT,
	measuredValue FLOAT,
	measurementDateTime DATETIME,
	isCarbonDioxideGeneratorOn BIT NOT NULL,
	greenhouseId INT,
	title NCHAR VARYING(20),
);


ALTER TABLE [TestGrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_titleTemperature FOREIGN KEY (title) REFERENCES [TestGrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [TestGrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_greenhouseIdTemperature FOREIGN KEY (greenhouseId) REFERENCES [TestGrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);

ALTER TABLE [TestGrinHouseDW].[stage].[FactMeasurementHumidity] ADD CONSTRAINT FK_titleHumidity FOREIGN KEY (title) REFERENCES [TestGrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [TestGrinHouseDW].[stage].[FactMeasurementHumidity] ADD CONSTRAINT FK_greenhouseIdHumidity FOREIGN KEY (greenhouseId) REFERENCES [TestGrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);

ALTER TABLE [TestGrinHouseDW].[stage].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_titleCarbonDioxide FOREIGN KEY (title) REFERENCES [TestGrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [TestGrinHouseDW].[stage].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_greenhouseIdCarbonDioxide FOREIGN KEY (greenhouseId) REFERENCES [TestGrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);


/*
alter table [TestGrinHouseDW].[stage].[FactMeasurementTemperature] drop constraint FK_titleTemperature
alter table [TestGrinHouseDW].[stage].[FactMeasurementTemperature] drop constraint FK_greenhouseIdTemperature

alter table [TestGrinHouseDW].[stage].[FactMeasurementHumidity] drop constraint FK_titleHumidity
alter table [TestGrinHouseDW].[stage].[FactMeasurementHumidity] drop constraint FK_greenhouseIdHumidity

alter table [TestGrinHouseDW].[stage].[FactMeasurementCarbonDioxide] drop constraint FK_titleCarbonDioxide
alter table [TestGrinHouseDW].[stage].[FactMeasurementCarbonDioxide] drop constraint FK_greenhouseIdCarbonDioxide

ALTER TABLE [TestGrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_title FOREIGN KEY (title) REFERENCES [TestGrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [TestGrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_greenhouseId FOREIGN KEY (greenhouseId) REFERENCES [TestGrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);

alter table [TestGrinHouseDW].[stage].[FactMeasurementTemperature] drop constraint FK_acStateId
alter table [TestGrinHouseDW].[stage].[FactMeasurementHumidity] drop constraint FK_humidifierId
alter table [TestGrinHouseDW].[stage].[FactMeasurementCarbonDioxide] drop constraint FK_carbonDioxideGeneratorId

DROP TABLE [TestGrinHouseDW].[stage].[DimGreenhouse];
DROP TABLE [TestGrinHouseDW].[stage].[DimMeasurementType];
DROP TABLE [TestGrinHouseDW].[stage].[DimACState];
DROP TABLE [TestGrinHouseDW].[stage].[DimHumidifierState];
DROP TABLE [TestGrinHouseDW].[stage].[DimCarbonDioxideState];
DROP TABLE [TestGrinHouseDW].[stage].[FactMeasurementTemperature];
DROP TABLE [TestGrinHouseDW].[stage].[FactMeasurementHumidity];
DROP TABLE [TestGrinHouseDW].[stage].[FactMeasurementCarbonDioxide];
*/