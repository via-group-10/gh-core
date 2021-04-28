

--need to drop the constraints and add them again for testing. will most probably be removed later

alter table [GrinHouseDW].[stage].[FactMeasurementTemperature] drop constraint FK_titleTemperature
alter table [GrinHouseDW].[stage].[FactMeasurementTemperature] drop constraint FK_greenhouseIdTemperature
alter table [GrinHouseDW].[stage].[FactMeasurementTemperature] drop constraint FK_acStateId

alter table [GrinHouseDW].[stage].[FactMeasurementHumidity] drop constraint FK_titleHumidity
alter table [GrinHouseDW].[stage].[FactMeasurementHumidity] drop constraint FK_greenhouseIdHumidity
alter table [GrinHouseDW].[stage].[FactMeasurementHumidity] drop constraint FK_humidifierId

alter table [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] drop constraint FK_titleCarbonDioxide
alter table [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] drop constraint FK_greenhouseIdCarbonDioxide
alter table [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] drop constraint FK_carbonDioxideGeneratorId

TRUNCATE TABLE [GrinHouseDW].[stage].[DimGreenhouse];
TRUNCATE TABLE [GrinHouseDW].[stage].[DimMeasurementType];
TRUNCATE TABLE [GrinHouseDW].[stage].[DimACState];
TRUNCATE TABLE [GrinHouseDW].[stage].[DimHumidifierState];
TRUNCATE TABLE [GrinHouseDW].[stage].[DimCarbonDioxideState];
TRUNCATE TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature];
TRUNCATE TABLE [GrinHouseDW].[stage].[FactMeasurementHumidity];
TRUNCATE TABLE [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide];


ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_titleTemperature FOREIGN KEY (title) REFERENCES [GrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_greenhouseIdTemperature FOREIGN KEY (greenhouseId) REFERENCES [GrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementTemperature] ADD CONSTRAINT FK_acStateId FOREIGN KEY (acStateId) REFERENCES [GrinHouseDW].[stage].[DimACState] (acStateId);

ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementHumidity] ADD CONSTRAINT FK_titleHumidity FOREIGN KEY (title) REFERENCES [GrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementHumidity] ADD CONSTRAINT FK_greenhouseIdHumidity FOREIGN KEY (greenhouseId) REFERENCES [GrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementHumidity] ADD CONSTRAINT FK_humidifierId FOREIGN KEY (humidifierId) REFERENCES [GrinHouseDW].[stage].[DimHumidifierState] (humidifierId);

ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_titleCarbonDioxide FOREIGN KEY (title) REFERENCES [GrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_greenhouseIdCarbonDioxide FOREIGN KEY (greenhouseId) REFERENCES [GrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_carbonDioxideGeneratorId FOREIGN KEY (carbonDioxideGeneratorId) REFERENCES [GrinHouseDW].[stage].[DimCarbonDioxideState] (carbonDioxideGeneratorId);




--inserting into dimension tables
INSERT INTO [GrinHouseDW].[stage].[DimGreenhouse](
[greenhouseId], [greenhouseName])
SELECT [greenhouseId], [greenhouseName]
FROM [Grinhouse].[dbo].[Greenhouse] 

INSERT INTO [GrinHouseDW].[stage].[DimMeasurementType](
[title])
SELECT [title]
FROM [Grinhouse].[dbo].[MeasurementType]

INSERT INTO [GrinHouseDW].[stage].[DimACState](
[acStateId])
SELECT [acStateId]
FROM [Grinhouse].[dbo].[ACState] 

INSERT INTO [GrinHouseDW].[stage].[DimHumidifierState](
[humidifierId])
SELECT [humidifierId]
FROM [Grinhouse].[dbo].[HumidifierState] 

INSERT INTO [GrinHouseDW].[stage].[DimCarbonDioxideState](
[carbonDioxideGeneratorId])
SELECT [carbonDioxideGeneratorId]
FROM [Grinhouse].[dbo].[CarbonDioxideGeneratorState] 




--inserting into fact table
INSERT INTO [GrinHouseDW].[stage].[FactMeasurementTemperature](
[measurementId], [measuredValue], [measurementDateTime], [isHeaterOn], [isCoolerOn], [greenhouseId], [title], [acStateId])
SELECT [measurementId], [measuredValue], [measurementDateTime], ac.[isHeaterOn], ac.[isCoolerOn], [belongsTo], [isOfType], ac.[acStateId]
FROM [GrinHouse].[dbo].[Measurement] m
INNER JOIN [GrinHouse].[dbo].[ACState] as ac on ac.stateDateTime = m.measurementDateTime
WHERE isOfType = 'temperature'

INSERT INTO [GrinHouseDW].[stage].[FactMeasurementHumidity](
[measurementId], [measuredValue], [measurementDateTime], [isHumidifierOn], [isDehumidifierOn], [greenhouseId], [title], [humidifierId])
SELECT [measurementId], [measuredValue], [measurementDateTime], h.[isHumidifierOn], h.[isDehumidifierOn], [belongsTo], [isOfType], h.[humidifierId]
FROM [GrinHouse].[dbo].[Measurement] m
INNER JOIN [GrinHouse].[dbo].[HumidifierState] as h on h.stateDateTime = m.measurementDateTime
WHERE isOfType = 'humidity'

INSERT INTO [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide](
[measurementId], [measuredValue], [measurementDateTime], [isCarbonDioxideGeneratorOn], [greenhouseId], [title], [carbonDioxideGeneratorId])
SELECT [measurementId], [measuredValue], [measurementDateTime], c.[isCarbonDioxideGeneratorOn], [belongsTo], [isOfType], c.[carbonDioxideGeneratorId]
FROM [GrinHouse].[dbo].[Measurement] m
INNER JOIN [GrinHouse].[dbo].[CarbonDioxideGeneratorState] as c on c.stateDateTime = m.measurementDateTime
WHERE isOfType = 'carbonDioxide'