

--need to drop the constraints and add them again for testing. will most probably be removed later

alter table [TestGrinHouseDW].[stage].[FactMeasurementTemperature] drop constraint FK_titleTemperature
alter table [TestGrinHouseDW].[stage].[FactMeasurementTemperature] drop constraint FK_greenhouseIdTemperature

alter table [TestGrinHouseDW].[stage].[FactMeasurementHumidity] drop constraint FK_titleHumidity
alter table [TestGrinHouseDW].[stage].[FactMeasurementHumidity] drop constraint FK_greenhouseIdHumidity

alter table [TestGrinHouseDW].[stage].[FactMeasurementCarbonDioxide] drop constraint FK_titleCarbonDioxide
alter table [TestGrinHouseDW].[stage].[FactMeasurementCarbonDioxide] drop constraint FK_greenhouseIdCarbonDioxide

TRUNCATE TABLE [TestGrinHouseDW].[stage].[DimGreenhouse];
TRUNCATE TABLE [TestGrinHouseDW].[stage].[DimMeasurementType];
TRUNCATE TABLE [TestGrinHouseDW].[stage].[FactMeasurementTemperature];
TRUNCATE TABLE [TestGrinHouseDW].[stage].[FactMeasurementHumidity];
TRUNCATE TABLE [TestGrinHouseDW].[stage].[FactMeasurementCarbonDioxide];


ALTER TABLE [TestGrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_titleTemperature FOREIGN KEY (title) REFERENCES [TestGrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [TestGrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_greenhouseIdTemperature FOREIGN KEY (greenhouseId) REFERENCES [TestGrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);

ALTER TABLE [TestGrinHouseDW].[stage].[FactMeasurementHumidity] ADD CONSTRAINT FK_titleHumidity FOREIGN KEY (title) REFERENCES [TestGrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [TestGrinHouseDW].[stage].[FactMeasurementHumidity] ADD CONSTRAINT FK_greenhouseIdHumidity FOREIGN KEY (greenhouseId) REFERENCES [TestGrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);

ALTER TABLE [TestGrinHouseDW].[stage].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_titleCarbonDioxide FOREIGN KEY (title) REFERENCES [TestGrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [TestGrinHouseDW].[stage].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_greenhouseIdCarbonDioxide FOREIGN KEY (greenhouseId) REFERENCES [TestGrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);




--inserting into dimension tables
INSERT INTO [TestGrinHouseDW].[stage].[DimGreenhouse](
[greenhouseId], [greenhouseName])
SELECT [greenhouseId], [greenhouseName]
FROM [Grinhouse].[dbo].[Greenhouse] 

INSERT INTO [TestGrinHouseDW].[stage].[DimMeasurementType](
[title])
SELECT [title]
FROM [Grinhouse].[dbo].[MeasurementType]


--inserting into fact table
INSERT INTO [TestGrinHouseDW].[stage].[FactMeasurementTemperature](
[measurementId], [measuredValue], [measurementDateTime], [isHeaterOn], [isCoolerOn], [greenhouseId], [title])
SELECT [measurementId], [measuredValue], [measurementDateTime], ac.[isHeaterOn], ac.[isCoolerOn], [belongsTo], [isOfType]
FROM [TestGrinHouse].[dbo].[Measurement] m
INNER JOIN [TestGrinHouse].[dbo].[ACState] as ac on ac.stateDateTime = m.measurementDateTime
WHERE isOfType = 'temperature'

INSERT INTO [TestGrinHouseDW].[stage].[FactMeasurementHumidity](
[measurementId], [measuredValue], [measurementDateTime], [isHumidifierOn], [isDehumidifierOn], [greenhouseId], [title])
SELECT [measurementId], [measuredValue], [measurementDateTime], h.[isHumidifierOn], h.[isDehumidifierOn], [belongsTo], [isOfType]
FROM [TestGrinHouse].[dbo].[Measurement] m
INNER JOIN [TestGrinHouse].[dbo].[HumidifierState] as h on h.stateDateTime = m.measurementDateTime
WHERE isOfType = 'humidity'

INSERT INTO [TestGrinHouseDW].[stage].[FactMeasurementCarbonDioxide](
[measurementId], [measuredValue], [measurementDateTime], [isCarbonDioxideGeneratorOn], [greenhouseId], [title])
SELECT [measurementId], [measuredValue], [measurementDateTime], c.[isCarbonDioxideGeneratorOn], [belongsTo], [isOfType]
FROM [TestGrinHouse].[dbo].[Measurement] m
INNER JOIN [TestGrinHouse].[dbo].[CarbonDioxideGeneratorState] as c on c.stateDateTime = m.measurementDateTime
WHERE isOfType = 'carbonDioxide'