use GrinHouse
	
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[Greenhouse]') AND type in (N'U'))

CREATE TABLE Greenhouse(
	greenhouseId INT PRIMARY KEY NOT NULL,
	greenhouseName NCHAR VARYING(100) NOT NULL,
	loginName NCHAR VARYING(100) NOT NULL,
	loginPassword NCHAR VARYING(100) NOT NULL
);

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[HumidifierState]') AND type in (N'U'))

CREATE TABLE HumidifierState(
	humidifierId INT IDENTITY PRIMARY KEY NOT NULL,
	isHumidifierOn BIT NOT NULL,
	isDeHumidifierOn BIT NOT NULL,
	stateDateTime DATETIME NOT NULL,
	logs INT NOT NULL,
	FOREIGN KEY(logs) REFERENCES Greenhouse(greenhouseId)
);

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[ACSTate]') AND type in (N'U'))

CREATE TABLE ACState(
	acStateId INT IDENTITY PRIMARY KEY NOT NULL,
	isHeaterOn BIT NOT NULL,
	isCoolerOn BIT NOT NULL,
	stateDateTime DATETIME NOT NULL,
	logs INT NOT NULL,
	FOREIGN KEY(logs) REFERENCES Greenhouse(greenhouseId)
);

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[CarbonDioxideGeneratorState]') AND type in (N'U'))

CREATE TABLE CarbonDioxideGeneratorState(
	carbonDioxideGeneratorId INT IDENTITY PRIMARY KEY NOT NULL,
	isCarbonDioxideGeneratorOn BIT NOT NULL,
	stateDateTime DATETIME NOT NULL,
	logs INT NOT NULL,
	FOREIGN KEY(logs) REFERENCES Greenhouse(greenhouseId)
);

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[ThresholdProfile]') AND type in (N'U'))

CREATE TABLE ThresholdProfile(
	thresholdProfileId INT  IDENTITY PRIMARY KEY NOT NULL,
	profileName NCHAR VARYING(100) NOT NULL,
	active BIT NOT NULL,
	minimumTemperature FLOAT NOT NULL,
	maximumTemperature FLOAT NOT NULL,
	minimumHumidity FLOAT NOT NULL,
	maximumHumidity FLOAT NOT NULL,
	minimumCarbonDioxide FLOAT NOT NULL,
	maximumCarbonDioxide FLOAT NOT NULL,
	storedIn INT NOT NULL,
	FOREIGN KEY(storedIn) REFERENCES Greenhouse(greenhouseId)
);

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[MeasurementType]') AND type in (N'U'))


CREATE TABLE MeasurementType(
	title NCHAR VARYING(20) PRIMARY KEY NOT NULL CHECK(title IN ('temperature', 'humidity', 'carbonDioxide'))
);


IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[Measurement]') AND type in (N'U'))

CREATE TABLE Measurement(
	measurementId INT IDENTITY PRIMARY KEY NOT NULL,
	measuredValue FLOAT NOT NULL,
	measurementDateTime DATETIME NOT NULL,
	belongsTo INT NOT NULL,
	isOfType NCHAR VARYING(20),
	FOREIGN KEY(belongsTo) REFERENCES Greenhouse(greenhouseId),
	FOREIGN KEY(isOfType) REFERENCES MeasurementType(title)
);


INSERT INTO [GrinHouse].[dbo].[MeasurementType] values ('temperature');
INSERT INTO [GrinHouse].[dbo].[MeasurementType] values ('humidity');
INSERT INTO [GrinHouse].[dbo].[MeasurementType] values ('carbonDioxide');

/*
INSERT INTO [GrinHouse].[dbo].[HumidifierState] values (1, 1, 0,  '2021-03-01 23:26:30.300', 1);
INSERT INTO [GrinHouse].[dbo].[ACState] values (1, 1, 0,  '2021-03-01 23:26:30.300', 1);
INSERT INTO [GrinHouse].[dbo].[CarbonDioxideGeneratorState] values (1, 0,  '2021-03-01 23:26:30.300', 1);

INSERT INTO [GrinHouse].[dbo].[Measurement] values ('25.0', '2021-03-01 23:26:30.300', 1, 'temperature');
INSERT INTO [GrinHouse].[dbo].[Measurement] values ('35.0', '2021-03-01 23:26:30.300', 1, 'humidity');
INSERT INTO [GrinHouse].[dbo].[Measurement] values ('45.0', '2021-03-01 23:26:30.300', 1, 'carbonDioxide');
*/