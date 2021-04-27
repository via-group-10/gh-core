

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[DimGreenhouse]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[DimGreenhouse](
	greenhouseId INT PRIMARY KEY NOT NULL,
	greenhouseName NCHAR VARYING(100) NOT NULL
	);

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[DimMeasurementType]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[DimMeasurementType](
	title NCHAR VARYING(20) PRIMARY KEY NOT NULL CHECK(title IN ('temperature', 'humidity', 'carbonDioxide'))
);

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[DimDeviceState]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[DimDeviceState](
	deviceStateId INT  IDENTITY PRIMARY KEY NOT NULL,
	stateDateTime DATETIME NOT NULL,
	acStateId INT NOT NULL,
	isHeaterOn BIT NOT NULL,
	isCoolerOn BIT NOT NULL,
	humidifierId INT NOT NULL,
	isHumidifierOn BIT NOT NULL,
	isDeHumidifierOn BIT NOT NULL,
	carbonDioxideGeneratorId INT NOT NULL,
	isCarbonDioxideGeneratorOn BIT NOT NULL
);

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[GrinHouseDW].[stage].[FactMeasurement]') AND type in (N'U'))

CREATE TABLE [GrinHouseDW].[stage].[FactMeasurement](
	measurementId INT,
	measuredValue FLOAT,
	measurementDateTime DATETIME,
	title NCHAR VARYING(20),
	deviceStateId INT,
	greenhouseId INT,
);

ALTER TABLE [GrinHouseDW].[stage].[FactMeasurement] ADD CONSTRAINT FK_title FOREIGN KEY (title) REFERENCES [GrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurement] ADD CONSTRAINT FK_deviceStateId FOREIGN KEY (deviceStateId) REFERENCES [GrinHouseDW].[stage].[DimDeviceState] (deviceStateId);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurement] ADD CONSTRAINT FK_greenhouseId FOREIGN KEY (greenhouseId) REFERENCES [GrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);