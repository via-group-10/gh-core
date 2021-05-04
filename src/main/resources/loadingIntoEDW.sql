


alter table [GrinHouseDW].[edw].[FactMeasurementTemperature] drop constraint FK_G_ID_Temperature
alter table [GrinHouseDW].[edw].[FactMeasurementTemperature] drop constraint FK_MT_ID_Temperature
alter table [GrinHouseDW].[edw].[FactMeasurementTemperature] drop constraint FK_D_ID_Temperature
alter table [GrinHouseDW].[edw].[FactMeasurementTemperature] drop constraint FK_T_ID_Temperature

alter table [GrinHouseDW].[edw].[FactMeasurementHumidity] drop constraint FK_G_ID_Humidity
alter table [GrinHouseDW].[edw].[FactMeasurementHumidity] drop constraint FK_MT_ID_Humidity
alter table [GrinHouseDW].[edw].[FactMeasurementHumidity] drop constraint FK_D_ID_Humidity
alter table [GrinHouseDW].[edw].[FactMeasurementHumidity] drop constraint FK_T_ID_Humidity

alter table [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] drop constraint FK_G_ID_CarbonDioxide
alter table [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] drop constraint FK_MT_ID_CarbonDioxide
alter table [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] drop constraint FK_D_ID_CarbonDioxide
alter table [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] drop constraint FK_T_ID_CarbonDioxide


truncate table [GrinHouseDW].[edw].[DimGreenhouse]
truncate table [GrinHouseDW].[edw].[DimMeasurementType]
truncate table [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide]
truncate table [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide]

truncate table [GrinHouseDW].[edw].[FactMeasurementTemperature]
truncate table [GrinHouseDW].[edw].[FactMeasurementHumidity]

alter table [GrinHouseDW].[edw].[FactMeasurementTemperature] add constraint PK_FactMeasurementTemperature PRIMARY KEY(G_ID, MT_ID, D_ID, T_ID)
alter table [GrinHouseDW].[edw].[FactMeasurementHumidity] add constraint PK_FactMeasurementHumidity PRIMARY KEY(G_ID, MT_ID, D_ID, T_ID)
alter table [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide] add constraint PK_FactMeasurementCarbonDioxide PRIMARY KEY(G_ID, MT_ID, D_ID, T_ID)

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





--inserting into dimension tables

INSERT INTO [GrinHouseDW].[edw].[DimGreenhouse](
[greenhouseId], [greenhouseName])
SELECT [greenhouseId], [greenhouseName]
FROM [GrinhouseDW].[stage].[DimGreenhouse] 

INSERT INTO [GrinHouseDW].[edw].[DimMeasurementType](
[title])
SELECT [title]
FROM [GrinhouseDW].[stage].[DimMeasurementType]



--inserting into fact table
INSERT INTO [GrinHouseDW].[edw].[FactMeasurementTemperature](
[G_ID], [MT_ID], [D_ID], [T_ID], [measurementId], [measuredValue], [isHeaterOn], [isCoolerOn])
SELECT g.[G_ID], mt.[title], d.[D_ID], t.[T_ID], m.[measurementId], m.[measuredValue], m.[isHeaterOn], m.[isCoolerOn] 
from [GrinHouseDW].[stage].[FactMeasurementTemperature] m 
inner join [GrinHouseDW].[edw].[DimGreenhouse] as g on g.greenhouseId = m.greenhouseId
inner join [GrinHouseDW].[edw].[DimMeasurementType] as mt on mt.title = m.title
inner join [GrinHouseDW].[edw].[DimDate] as d on d.Date = (SELECT CONVERT(VARCHAR(10),m.measurementDateTime,111))
inner join [GrinHouseDW].[edw].[DimTime] as t on t.Time = (SELECT CONVERT(VARCHAR(8), m.measurementDateTime,108))



insert into [GrinHouseDW].[edw].[FactMeasurementHumidity](
[G_ID], [MT_ID], [D_ID], [T_ID], [measurementId], [measuredValue], [isHumidifierOn], [isDehumidifierOn])
select g.[G_ID], mt.[title], d.[D_ID], t.[T_ID], m.[measurementId], m.[measuredValue], m.[isHumidifierOn], m.[isDehumidifierOn]
from [GrinHouseDW].[stage].[FactMeasurementHumidity] m 
inner join [GrinHouseDW].[edw].[DimGreenhouse] as g on g.greenhouseId = m.greenhouseId
inner join [GrinHouseDW].[edw].[DimMeasurementType] as mt on mt.title = m.title
inner join [GrinHouseDW].[edw].[DimDate] as d on d.Date = (SELECT CONVERT(VARCHAR(10),m.measurementDateTime,111))
inner join [GrinHouseDW].[edw].[DimTime] as t on t.Time = (SELECT CONVERT(VARCHAR(8), m.measurementDateTime,108))



insert into [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide](
[G_ID], [MT_ID], [D_ID], [T_ID], [measurementId], [measuredValue], [isCarbonDioxideGeneratorOn])
select g.[G_ID], mt.[title], d.[D_ID], t.[T_ID], m.[measurementId], m.[measuredValue], m.[isCarbonDioxideGeneratorOn]
from [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] m 
inner join [GrinHouseDW].[edw].[DimGreenhouse] as g on g.greenhouseId = m.greenhouseId
inner join [GrinHouseDW].[edw].[DimMeasurementType] as mt on mt.title = m.title
inner join [GrinHouseDW].[edw].[DimDate] as d on d.Date = (SELECT CONVERT(VARCHAR(10),m.measurementDateTime,111))
inner join [GrinHouseDW].[edw].[DimTime] as t on t.Time = (SELECT CONVERT(VARCHAR(8), m.measurementDateTime,108))