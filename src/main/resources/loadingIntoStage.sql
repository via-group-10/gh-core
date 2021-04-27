
--need to drop the constraints and add them again for testing. will most probably be removed later
alter table [GrinHouseDW].[stage].[FactMeasurement] drop constraint FK_title
alter table [GrinHouseDW].[stage].[FactMeasurement] drop constraint FK_greenhouseId

truncate table [GrinHouseDW].[stage].[DimMeasurementType]
truncate table [GrinHouseDW].[stage].[DimGreenhouse]

ALTER TABLE [GrinHouseDW].[stage].[FactMeasurement] ADD CONSTRAINT FK_title FOREIGN KEY (title) REFERENCES [GrinHouseDW].[stage].[DimMeasurementType] (title);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurement] ADD CONSTRAINT FK_greenhouseId FOREIGN KEY (greenhouseId) REFERENCES [GrinHouseDW].[stage].[DimGreenhouse] (greenhouseId);
ALTER TABLE [GrinHouseDW].[stage].[FactMeasurement] ADD CONSTRAINT FK_deviceStateId FOREIGN KEY (deviceStateId) REFERENCES [GrinHouseDW].[stage].[DimDeviceState] (deviceStateId);




--inserting into dimension tables
INSERT INTO [GrinHouseDW].[stage].[DimGreenhouse](
[greenhouseId], [greenhouseName])
SELECT [greenhouseId], [greenhouseName]
FROM [Grinhouse].[dbo].[Greenhouse] 

INSERT INTO [GrinHouseDW].[stage].[DimMeasurementType](
[title])
SELECT [title]
FROM [Grinhouse].[dbo].[MeasurementType]

INSERT INTO [GrinHouseDW].[stage].[DimDeviceState](
[stateDateTime], [acStateId], [isHeaterOn], [isCoolerOn], [humidifierId], [isHumidifierOn], [isDehumidifierOn], [carbonDioxideGeneratorId], [isCarbonDioxideGeneratorOn])
SELECT ac.[stateDateTime], ac.[acStateId], ac.[isHeaterOn], ac.[isCoolerOn], h.[humidifierId], h.[isHumidifierOn], h.[isDehumidifierOn], c.[carbonDioxideGeneratorId], c.[isCarbonDioxideGeneratorOn]
FROM [GrinHouse].[dbo].[ACState] ac 
inner join [GrinHouse].[dbo].[HumidifierState] as h on h.stateDateTime = ac.stateDateTime
inner join [GrinHouse].[dbo].[CarbonDioxideGeneratorState] as c on c.stateDateTime = ac.stateDateTime



--inserting into fact table
INSERT INTO [GrinHouseDW].[stage].[FactMeasurement](
[measurementId], [measuredValue], [measurementDateTime], [title], [deviceStateId], [greenhouseId])
SELECT m.[measurementId], m.[measuredValue], m.[measurementDateTime], m.[isOfType], d.[deviceStateId] , m.[belongsTo]
FROM [GrinHouse].[dbo].[Measurement] m
inner join [GrinHouseDW].[stage].[DimDeviceState] as d on d.stateDateTime = m.measurementDateTime