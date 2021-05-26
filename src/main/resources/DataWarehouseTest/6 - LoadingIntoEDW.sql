


alter table TestGrinHouseDW.[edw].[FactMeasurementTemperature] drop constraint FK_G_ID_Temperature
alter table TestGrinHouseDW.[edw].[FactMeasurementTemperature] drop constraint FK_MT_ID_Temperature
alter table TestGrinHouseDW.[edw].[FactMeasurementTemperature] drop constraint FK_D_ID_Temperature
alter table TestGrinHouseDW.[edw].[FactMeasurementTemperature] drop constraint FK_T_ID_Temperature

alter table TestGrinHouseDW.[edw].[FactMeasurementHumidity] drop constraint FK_G_ID_Humidity
alter table TestGrinHouseDW.[edw].[FactMeasurementHumidity] drop constraint FK_MT_ID_Humidity
alter table TestGrinHouseDW.[edw].[FactMeasurementHumidity] drop constraint FK_D_ID_Humidity
alter table TestGrinHouseDW.[edw].[FactMeasurementHumidity] drop constraint FK_T_ID_Humidity

alter table TestGrinHouseDW.[edw].[FactMeasurementCarbonDioxide] drop constraint FK_G_ID_CarbonDioxide
alter table TestGrinHouseDW.[edw].[FactMeasurementCarbonDioxide] drop constraint FK_MT_ID_CarbonDioxide
alter table TestGrinHouseDW.[edw].[FactMeasurementCarbonDioxide] drop constraint FK_D_ID_CarbonDioxide
alter table TestGrinHouseDW.[edw].[FactMeasurementCarbonDioxide] drop constraint FK_T_ID_CarbonDioxide


truncate table TestGrinHouseDW.[edw].[DimGreenhouse]
truncate table TestGrinHouseDW.[edw].[DimMeasurementType]
truncate table TestGrinHouseDW.[edw].[FactMeasurementCarbonDioxide]
truncate table TestGrinHouseDW.[edw].[FactMeasurementCarbonDioxide]

truncate table TestGrinHouseDW.[edw].[FactMeasurementTemperature]
truncate table TestGrinHouseDW.[edw].[FactMeasurementHumidity]

alter table TestGrinHouseDW.[edw].[FactMeasurementTemperature] add constraint PK_FactMeasurementTemperature PRIMARY KEY(G_ID, MT_ID, D_ID, T_ID)
alter table TestGrinHouseDW.[edw].[FactMeasurementHumidity] add constraint PK_FactMeasurementHumidity PRIMARY KEY(G_ID, MT_ID, D_ID, T_ID)
alter table TestGrinHouseDW.[edw].[FactMeasurementCarbonDioxide] add constraint PK_FactMeasurementCarbonDioxide PRIMARY KEY(G_ID, MT_ID, D_ID, T_ID)

--adding foreign surrogate keys in [FactMeasurementTemperature]
ALTER TABLE TestGrinHouseDW.[edw].[FactMeasurementTemperature] ADD CONSTRAINT FK_G_ID_Temperature FOREIGN KEY (G_ID) REFERENCES TestGrinHouseDW.[edw].[DimGreenhouse] (G_ID);
ALTER TABLE TestGrinHouseDW.[edw].[FactMeasurementTemperature] ADD CONSTRAINT FK_MT_ID_Temperature FOREIGN KEY (MT_ID) REFERENCES TestGrinHouseDW.[edw].[DimMeasurementType] (title);
ALTER TABLE TestGrinHouseDW.[edw].[FactMeasurementTemperature] ADD CONSTRAINT FK_D_ID_Temperature FOREIGN KEY (D_ID) REFERENCES TestGrinHouseDW.[edw].[DimDate] (D_ID);
ALTER TABLE TestGrinHouseDW.[edw].[FactMeasurementTemperature] ADD CONSTRAINT FK_T_ID_Temperature FOREIGN KEY (T_ID) REFERENCES TestGrinHouseDW.[edw].[DimTime] (T_ID);

--adding foreign surrogate keys in [FactMeasurementHumidity]
ALTER TABLE TestGrinHouseDW.[edw].[FactMeasurementHumidity] ADD CONSTRAINT FK_G_ID_Humidity FOREIGN KEY (G_ID) REFERENCES TestGrinHouseDW.[edw].[DimGreenhouse] (G_ID);
ALTER TABLE TestGrinHouseDW.[edw].[FactMeasurementHumidity] ADD CONSTRAINT FK_MT_ID_Humidity FOREIGN KEY (MT_ID) REFERENCES TestGrinHouseDW.[edw].[DimMeasurementType] (title);
ALTER TABLE TestGrinHouseDW.[edw].[FactMeasurementHumidity] ADD CONSTRAINT FK_D_ID_Humidity FOREIGN KEY (D_ID) REFERENCES TestGrinHouseDW.[edw].[DimDate] (D_ID);
ALTER TABLE TestGrinHouseDW.[edw].[FactMeasurementHumidity] ADD CONSTRAINT FK_T_ID_Humidity FOREIGN KEY (T_ID) REFERENCES TestGrinHouseDW.[edw].[DimTime] (T_ID);

--adding foreign surrogate keys in [FactMeasurementCarbonDioxide]
ALTER TABLE TestGrinHouseDW.[edw].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_G_ID_CarbonDioxide FOREIGN KEY (G_ID) REFERENCES TestGrinHouseDW.[edw].[DimGreenhouse] (G_ID);
ALTER TABLE TestGrinHouseDW.[edw].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_MT_ID_CarbonDioxide FOREIGN KEY (MT_ID) REFERENCES TestGrinHouseDW.[edw].[DimMeasurementType] (title);
ALTER TABLE TestGrinHouseDW.[edw].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_D_ID_CarbonDioxide FOREIGN KEY (D_ID) REFERENCES TestGrinHouseDW.[edw].[DimDate] (D_ID);
ALTER TABLE TestGrinHouseDW.[edw].[FactMeasurementCarbonDioxide] ADD CONSTRAINT FK_T_ID_CarbonDioxide FOREIGN KEY (T_ID) REFERENCES TestGrinHouseDW.[edw].[DimTime] (T_ID);





--inserting into dimension tables

INSERT INTO TestGrinHouseDW.[edw].[DimGreenhouse](
[greenhouseId], [greenhouseName])
SELECT [greenhouseId], [greenhouseName]
FROM [TestGrinhouseDW].[stage].[DimGreenhouse] 

INSERT INTO TestGrinHouseDW.[edw].[DimMeasurementType](
[title])
SELECT [title]
FROM [TestGrinhouseDW].[stage].[DimMeasurementType]



--inserting into fact table
INSERT INTO TestGrinHouseDW.[edw].[FactMeasurementTemperature](
[G_ID], [MT_ID], [D_ID], [T_ID], [measurementId], [measuredValue], [isHeaterOn], [isCoolerOn])
SELECT g.[G_ID], mt.[title], d.[D_ID], t.[T_ID], m.[measurementId], m.[measuredValue], m.[isHeaterOn], m.[isCoolerOn] 
from TestGrinHouseDW.[stage].[FactMeasurementTemperature] m 
inner join TestGrinHouseDW.[edw].[DimGreenhouse] as g on g.greenhouseId = m.greenhouseId
inner join TestGrinHouseDW.[edw].[DimMeasurementType] as mt on mt.title = m.title
inner join TestGrinHouseDW.[edw].[DimDate] as d on d.Date = (SELECT CONVERT(VARCHAR(10),m.measurementDateTime,111))
inner join TestGrinHouseDW.[edw].[DimTime] as t on t.Time = (SELECT CONVERT(VARCHAR(8), m.measurementDateTime,108))



insert into TestGrinHouseDW.[edw].[FactMeasurementHumidity](
[G_ID], [MT_ID], [D_ID], [T_ID], [measurementId], [measuredValue], [isHumidifierOn], [isDehumidifierOn])
select g.[G_ID], mt.[title], d.[D_ID], t.[T_ID], m.[measurementId], m.[measuredValue], m.[isHumidifierOn], m.[isDehumidifierOn]
from TestGrinHouseDW.[stage].[FactMeasurementHumidity] m 
inner join TestGrinHouseDW.[edw].[DimGreenhouse] as g on g.greenhouseId = m.greenhouseId
inner join TestGrinHouseDW.[edw].[DimMeasurementType] as mt on mt.title = m.title
inner join TestGrinHouseDW.[edw].[DimDate] as d on d.Date = (SELECT CONVERT(VARCHAR(10),m.measurementDateTime,111))
inner join TestGrinHouseDW.[edw].[DimTime] as t on t.Time = (SELECT CONVERT(VARCHAR(8), m.measurementDateTime,108))



insert into TestGrinHouseDW.[edw].[FactMeasurementCarbonDioxide](
[G_ID], [MT_ID], [D_ID], [T_ID], [measurementId], [measuredValue], [isCarbonDioxideGeneratorOn])
select g.[G_ID], mt.[title], d.[D_ID], t.[T_ID], m.[measurementId], m.[measuredValue], m.[isCarbonDioxideGeneratorOn]
from TestGrinHouseDW.[stage].[FactMeasurementCarbonDioxide] m 
inner join TestGrinHouseDW.[edw].[DimGreenhouse] as g on g.greenhouseId = m.greenhouseId
inner join TestGrinHouseDW.[edw].[DimMeasurementType] as mt on mt.title = m.title
inner join TestGrinHouseDW.[edw].[DimDate] as d on d.Date = (SELECT CONVERT(VARCHAR(10),m.measurementDateTime,111))
inner join TestGrinHouseDW.[edw].[DimTime] as t on t.Time = (SELECT CONVERT(VARCHAR(8), m.measurementDateTime,108))