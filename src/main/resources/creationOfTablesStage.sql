

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[DimGreenhouse]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[DimGreenhouse](
	greenhouseId INT PRIMARY KEY NOT NULL,
	greenhouseName NCHAR VARYING(100) NOT NULL
	);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[DimMeasurementType]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[DimMeasurementType](
	title NCHAR VARYING(20) PRIMARY KEY NOT NULL CHECK(title IN ('temperature', 'humidity', 'carbonDioxide'))
);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[DimACState]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[DimACState](
	acStateId INT  PRIMARY KEY NOT NULL
);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[DimHumidifierState]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[DimHumidifierState](
	humidifierId INT PRIMARY KEY NOT NULL
);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[DimCarbonDioxideState]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[DimCarbonDioxideState](
	carbonDioxideGeneratorId INT PRIMARY KEY NOT NULL
);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[FactMeasurementTemperature]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature](
	measurementId INT,
	measuredValue FLOAT,
	measurementDateTime DATETIME,
	isHeaterOn BIT NOT NULL,
	isCoolerOn BIT NOT NULL,
	greenhouseId INT,
	title NCHAR VARYING(20),
	acStateId INT
);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[FactMeasurementHumidity]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[FactMeasurementHumidity](
	measurementId INT,
	measuredValue FLOAT,
	measurementDateTime DATETIME,
	isHumidifierOn BIT NOT NULL,
	isDehumidifierOn BIT NOT NULL,
	greenhouseId INT,
	title NCHAR VARYING(20),
	humidifierId INT
);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[FactMeasurementCarbonDioxide]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide](
	measurementId INT,
	measuredValue FLOAT,
	measurementDateTime DATETIME,
	isCarbonDioxideGeneratorOn BIT NOT NULL,
	greenhouseId INT,
	title NCHAR VARYING(20),
	carbonDioxideGeneratorId INT
);




ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_titleTemperature FOREIGN KEY (title) REFERENCES [GrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_greenhouseIdTemperature FOREIGN KEY (greenhouseId) REFERENCES [GrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_acStateId FOREIGN KEY (acStateId) REFERENCES [GrinHouseDW].[stage].[DimACState] (acStateId);

ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementHumidity] ADD CONSTRAINT FK_titleHumidity FOREIGN KEY (title) REFERENCES [GrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementHumidity] ADD CONSTRAINT FK_greenhouseIdHumidity FOREIGN KEY (greenhouseId) REFERENCES [GrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementHumidity] ADD CONSTRAINT FK_humidifierId FOREIGN KEY (humidifierId) REFERENCES [GrinHouseDW].[stage].[DimHumidifierState] (humidifierId);

ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_titleCarbonDioxide FOREIGN KEY (title) REFERENCES [GrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_greenhouseIdCarbonDioxide FOREIGN KEY (greenhouseId) REFERENCES [GrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_carbonDioxideGeneratorId FOREIGN KEY (carbonDioxideGeneratorId) REFERENCES [GrinHouseDW].[stage].[DimCarbonDioxideState] (carbonDioxideGeneratorId);


/*
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_title FOREIGN KEY (title) REFERENCES [GrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_greenhouseId FOREIGN KEY (greenhouseId) REFERENCES [GrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);

DROP TABLE [GrinHouseDW].[stage].[DimGreenhouse];
DROP TABLE [GrinHouseDW].[stage].[DimMeasurementType];
DROP TABLE [GrinHouseDW].[stage].[DimACState];
DROP TABLE [GrinHouseDW].[stage].[DimHumidifierState];
DROP TABLE [GrinHouseDW].[stage].[DimCarbonDioxideState];
DROP TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature];
DROP TABLE [GrinHouseDW].[stage].[FactMeasurementHumidity];
DROP TABLE [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide];
*/