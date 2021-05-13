use GrinHouse

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[Greenhouse]') AND type in (N'U'))

CREATE TABLE Greenhouse(
	greenhouseId INT IDENTITY PRIMARY KEY NOT NULL,
	greenhouseName NCHAR VARYING(100) NOT NULL,
	loginName NCHAR VARYING(100) NOT NULL,
	loginPassword NCHAR VARYING(100) NOT NULL
);

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[HumidifierState]') AND type in (N'U'))

CREATE TABLE HumidifierState(
	humidifierId INT PRIMARY KEY NOT NULL,
	isHumidifierOn BIT NOT NULL,
	isDeHumidifierOn BIT NOT NULL,
	stateDateTime DATETIME NOT NULL,
	logs INT NOT NULL,
	FOREIGN KEY(logs) REFERENCES Greenhouse(greenhouseId)
);

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[ACSTate]') AND type in (N'U'))

CREATE TABLE ACState(
	acStateId INT PRIMARY KEY NOT NULL,
	isHeaterOn BIT NOT NULL,
	isCoolerOn BIT NOT NULL,
	stateDateTime DATETIME NOT NULL,
	logs INT NOT NULL,
	FOREIGN KEY(logs) REFERENCES Greenhouse(greenhouseId)
);

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[CarbonDioxideGeneratorState]') AND type in (N'U'))

CREATE TABLE CarbonDioxideGeneratorState(
	carbonDioxideGeneratorId INT PRIMARY KEY NOT NULL,
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

insert into [GrinHouse].[dbo].[Greenhouse] values ('my greeen', 'user', 'pass')