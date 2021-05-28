/*Creating the etl schema to store our log table in*/
Create schema etl

CREATE TABLE etl.[LogUpdate](
	[Table] [nvarchar](50) NULL,
	[LastLoadDate] int NULL
) ON [PRIMARY]
GO

/*Populating log update table with table names and dates*/
INSERT INTO [etl].[LogUpdate] ([Table],[LastLoadDate]) VALUES ('DimGreenhouse',20200401)
INSERT INTO [etl].[LogUpdate] ([Table],[LastLoadDate]) VALUES ('DimMeasurementType',20200401)
INSERT INTO [etl].[LogUpdate] ([Table],[LastLoadDate]) VALUES ('FactMeasurementCarbonDioxide',20200401)
INSERT INTO [etl].[LogUpdate] ([Table],[LastLoadDate]) VALUES ('FactMeasurementHumidity',20200401)
INSERT INTO [etl].[LogUpdate] ([Table],[LastLoadDate]) VALUES ('FactMeasurementTemperature',20200401)


/*Adding validTo and from to dimensions*/
alter table [GrinHouseDW].[edw].[DimGreenhouse] add ValidFrom int, ValidTo int;
alter table [GrinHouseDW].[edw].[DimMeasurementType] add ValidFrom int, ValidTo int;

/*Updating values in tables*/
update [GrinHouseDW].[edw].[DimGreenhouse] set ValidFrom = 20200401, ValidTo = 99990101;
update [GrinHouseDW].[edw].[DimMeasurementType] set ValidFrom = 20200401, ValidTo = 99990101;

----------GREENHOUSE----------


/*Include these with each  greenhouse stage > edw*/
declare @LastLoadDateGreenhouse int set @LastLoadDateGreenhouse = (select MAX([LastLoadDate]) from [etl].[LogUpdate] where [Table] = 'DimGreenhouse')
declare @NewLoadDateGreenhouse int set @NewLoadDateGreenhouse = CONVERT(CHAR(8), GETDATE(), 112)
declare @FutureDateGreenhouse int set @FutureDateGreenhouse = 99990101

/*INSERTING greenhouse stage > edw*/

  SELECT [greenhouseId] --Select all the records from the stage production database
  ,[greenhouseName]
  INTO #tmp -- All the selected records (except those that are already in edw and new records in edw) are inserted into tmp
  FROM [GrinHouseDW].[stage].[DimGreenhouse]
  EXCEPT SELECT [greenhouseId]  --Except those which are already in edw
  ,[greenhouseName]
  FROM [GrinHouseDW].[edw].[DimGreenhouse]

  INSERT INTO [GrinHouseDW].[edw].[DimGreenhouse]( -- Here is where we start inserting all the records from tmp into edw
  [greenhouseId]
  ,[greenhouseName]
  ,[ValidFrom]
  ,[ValidTo])
  SELECT  [greenhouseId]
  ,[greenhouseName]
  ,@NewLoadDateGreenhouse
  ,@FutureDateGreenhouse
  from #tmp

INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('DimGreenhouse', @NewLoadDateGreenhouse) -- We create a log in the LogUpdate table

drop table if exists #tmp -- we must make sure to drop the tmp table at the end
  
  /*End of INSERTING greenhouse stage > edw*/


  /*Updating greenhouse. ETL stage > edw*/


	 SELECT [greenhouseId] --Select all the records from the stage production database
	,[greenhouseName]
	 INTO #tmp -- All the selected records (except those that are already in edw and new records in edw) are inserted into tmp
	 FROM [GrinHouseDW].[stage].[DimGreenhouse]
	 EXCEPT SELECT [greenhouseId]  --Except those which are already in edw
	,[greenhouseName]
	 FROM [GrinHouseDW].[edw].[DimGreenhouse]
	 EXCEPT SELECT [greenhouseId]  --Except new records
	,[greenhouseName] from [GrinHouseDW].[stage].[DimGreenhouse]
	WHERE greenhouseId in (SELECT [greenhouseId] FROM [GrinHouseDW].[stage].[DimGreenhouse] EXCEPT SELECT [greenhouseId] FROM [GrinHouseDW].[edw].[DimGreenhouse] WHERE [ValidTo] = 99990101)

	UPDATE [GrinHouseDW].[edw].[DimGreenhouse]
	SET [greenhouseName] = t.greenhouseName,
	[ValidTo] = @FutureDateGreenhouse																																																																																																																																																																																																																																																																																																																																																																																																																																																																															
	FROM #tmp t
	WHERE [GrinHouseDW].[edw].[DimGreenhouse].[greenhouseId] = t.greenhouseId AND [GrinHouseDW].[edw].[DimGreenhouse].[ValidTo] = 99990101
	

	INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('DimGreenhouse', @NewLoadDateGreenhouse) -- We create a log in the LogUpdate table

	drop table if exists #tmp -- we must make sure to drop the tmp table at the end

  /*End of updating greenhouse. ETL stage > edw*/

  /*DELETING Greenhouse stage > edw*/

UPDATE [GrinHouseDW].[edw].[DimGreenhouse]
SET ValidTo = @NewLoadDateGreenhouse-1
WHERE [greenhouseId] in (
SELECT [greenhouseId]
FROM [GrinHouseDW].[edw].[DimGreenhouse]
WHERE [greenhouseId] IN (
SELECT [greenhouseId] FROM [GrinHouseDW].[edw].[DimGreenhouse]
EXCEPT SELECT [greenhouseId] from [GrinHouseDW].[stage].[dimGreenhouse]))

INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('DimGreenhouse', @NewLoadDateGreenhouse)

/*End of deleting Greenhouse*/

----------END GREENHOUSE----------

----------MEASUREMENT TYPE----------

  /*Include these with each measurement type stage > edw*/
declare @LastLoadDateMeasurementType int set @LastLoadDateMeasurementType = (select MAX([LastLoadDate]) from [etl].[LogUpdate] where [Table] = 'DimMeasurementType')
declare @NewLoadDateMeasurementType int set @NewLoadDateMeasurementType = CONVERT(CHAR(8), GETDATE(), 112)
declare @FutureDateMeasurementType int set @FutureDateMeasurementType = 99990101

/*Inserting measurement type. ETL stage > edw*/
SELECT [title] --Select all the records from the stage production database
  INTO #tmp -- All the selected records (except those that are already in edw and new records in edw) are inserted into tmp
  FROM [GrinHouseDW].[stage].[DimMeasurementType]
  EXCEPT SELECT [title]  --Except those which are already in edw
  FROM [GrinHouseDW].[edw].[DimMeasurementType]

  INSERT INTO [GrinHouseDW].[edw].[DimMeasurementType]( -- Here is where we start inserting all the records from tmp into edw
  [title], [ValidFrom],[ValidTo])
  SELECT  [title]
  ,@NewLoadDateMeasurementType
  ,@FutureDateMeasurementType
  from #tmp

  INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('DimMeasurementType', @NewLoadDateMeasurementType) -- We create a log in the LogUpdate table

	DROP TABLE IF EXISTS #tmp -- we must make sure to drop the tmp table at the end
  
 /*End of inserting measurement type. ETL stage > edw*/

 /*Updating measurement type. ETL stage > edw*/

 SELECT [title] --Select all the EDITED records in stage database
  INTO #tmp -- Which means we use an EXCEPT 
  FROM [GrinHouseDW].[stage].[DimMeasurementType]
  EXCEPT SELECT [title]  --For those which are already in edw
  FROM [GrinHouseDW].[edw].[DimMeasurementType]
  EXCEPT SELECT [title]  --And except the new records
  FROM [GrinHouseDW].[stage].[DimMeasurementType]
  WHERE [title] in (SELECT [title] FROM [GrinHouseDW].[stage].[DimMeasurementType] EXCEPT SELECT [title] FROM [GrinHouseDW].[edw].[DimGreenhouse] WHERE [ValidTo] = 99990101)


	UPDATE [GrinHouseDW].[edw].[DimMeasurementType]
	SET [title] = t.title,
	[ValidTo] = @FutureDateGreenhouse																																																																																																																																																																																																																																																																																																																																																																																																																																																																															
	FROM #tmp t
	WHERE [GrinHouseDW].[edw].[DimMeasurementType].[title] = t.title AND [GrinHouseDW].[edw].[DimMeasurementType].[ValidTo] = 99990101
 

	INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('DimGreenhouse', @NewLoadDateGreenhouse) -- We create a log in the LogUpdate table

	drop table if exists #tmp -- we must make sure to drop the tmp table at the end

 /*End of Updating measurement type. ETL stage > edw*/

/*DELETING MeasurementType stage > edw*/
  UPDATE [GrinHouseDW].[edw].[DimMeasurementType]
  SET ValidTo = @NewLoadDateMeasurementType-1
  WHERE [title] IN (
  SELECT [title] 
  FROM [GrinHouseDW].[edw].[DimMeasurementType] 
  WHERE [title] IN (
  SELECT [title] FROM [GrinHouseDW].[edw].[DimMeasurementType]
  EXCEPT SELECT [title] FROM [GrinHouseDW].[stage].[DimMeasurementType]))

  INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('DimMeasurementType', @NewLoadDateMeasurementType)

  /*End of Deleting MeasurementType stage > edw*/


  ----------END MEASUREMENT TYPE----------


 ----------STAGING FACTS----------

--TEMPERATURE--
declare @LastLoadDateFactMeasurementTemperature int set @LastLoadDateFactMeasurementTemperature = (select MAX([LastLoadDate]) from [etl].[LogUpdate] where [Table] = 'FactMeasurementTemperature')
declare @NewLoadDateFactMeasurementTemperature int set @NewLoadDateFactMeasurementTemperature = (CONVERT(CHAR(8), GETDATE(), 112))
declare @FutureDateFactMeasurementTemperature int set @FutureDateFactMeasurementTemperature = 99990101

TRUNCATE TABLE [GrinhouseDW].[edw].[FactMeasurementTemperature]

INSERT INTO [GrinHouseDW].[edw].[FactMeasurementTemperature](
[G_ID], [MT_ID], [D_ID], [T_ID], [measurementId], [measuredValue], [isHeaterOn], [isCoolerOn])

SELECT [greenhouseId], [title], d.[D_ID], t.[T_ID], m.[measurementId], m.[measuredValue], m.[isHeaterOn], m.[isCoolerOn] 
from [GrinHouseDW].[stage].[FactMeasurementTemperature] m 
inner join [GrinHouseDW].[edw].[DimDate] as d on d.Date = (SELECT CONVERT(VARCHAR(10),m.measurementDateTime,111))
inner join [GrinHouseDW].[edw].[DimTime] as t on t.Time = (SELECT CONVERT(VARCHAR(8), m.measurementDateTime,108))
 WHERE (CONVERT(CHAR(8), measurementDateTime , 112)) < (@LastLoadDateFactMeasurementTemperature)

 INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('FactMeasurementTemperature', @NewLoadDateFactMeasurementTemperature)

 drop table if exists #tmp -- we must make sure to drop the tmp table at the end
 --end of loading TEMPERATURE fact--

--HUMIDITY--
declare @LastLoadDateFactMeasurementHumidity int set @LastLoadDateFactMeasurementHumidity= (select MAX([LastLoadDate]) from [etl].[LogUpdate] where [Table] = 'FactMeasurementHumidity')
declare @NewLoadDateFactMeasurementHumidity int set @NewLoadDateFactMeasurementHumidity = (CONVERT(CHAR(8), GETDATE(), 112))
declare @FutureDateFactMeasurementHumidity int set @FutureDateFactMeasurementHumidity = 99990101

TRUNCATE TABLE [GrinhouseDW].[edw].[FactMeasurementHumidity]

 insert into [GrinHouseDW].[edw].[FactMeasurementHumidity](
[G_ID], [MT_ID], [D_ID], [T_ID], [measurementId], [measuredValue], [isHumidifierOn], [isDehumidifierOn])
select [greenhouseId], [title], d.[D_ID], t.[T_ID], m.[measurementId], m.[measuredValue], m.[isHumidifierOn], m.[isDehumidifierOn]
from [GrinHouseDW].[stage].[FactMeasurementHumidity] m 
 inner join [GrinHouseDW].[edw].[DimDate] as d on d.Date = (SELECT CONVERT(VARCHAR(10),m.measurementDateTime,111))
inner join [GrinHouseDW].[edw].[DimTime] as t on t.Time = (SELECT CONVERT(VARCHAR(8), m.measurementDateTime,108))
 WHERE (CONVERT(CHAR(8), measurementDateTime , 112)) < (@LastLoadDateFactMeasurementHumidity)
 

  INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('FactMeasurementHumidity', @NewLoadDateFactMeasurementHumidity)

 drop table if exists #tmp -- we must make sure to drop the tmp table at the end
  --end of loading HUMIDITY fact--

  --Carbon dioxide--
 declare @LastLoadDateFactMeasurementCarbonDioxide int set @LastLoadDateFactMeasurementCarbonDioxide= (select MAX([LastLoadDate]) from [etl].[LogUpdate] where [Table] = 'FactMeasurementCarbonDioxide')
declare @NewLoadDateFactMeasurementCarbonDioxide int set @NewLoadDateFactMeasurementCarbonDioxide = (CONVERT(CHAR(8), GETDATE(), 112))
declare @FutureDateFactMeasurementCarbonDioxide int set @FutureDateFactMeasurementCarbonDioxide = 99990101

TRUNCATE TABLE [GrinhouseDW].[edw].[FactMeasurementCarbonDioxide]

 insert into [GrinHouseDW].[edw].[FactMeasurementCarbonDioxide](
[G_ID], [MT_ID], [D_ID], [T_ID], [measurementId], [measuredValue], [isCarbonDioxideGeneratorOn])
select greenhouseId, [title], d.[D_ID], t.[T_ID], m.[measurementId], m.[measuredValue], m.[isCarbonDioxideGeneratorOn]
from [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] m 
 inner join [GrinHouseDW].[edw].[DimDate] as d on d.Date = (SELECT CONVERT(VARCHAR(10),m.measurementDateTime,111))
inner join [GrinHouseDW].[edw].[DimTime] as t on t.Time = (SELECT CONVERT(VARCHAR(8), m.measurementDateTime,108))
 WHERE (CONVERT(CHAR(8), measurementDateTime , 112)) < (@LastLoadDateFactMeasurementCarbonDioxide)


  INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('FactMeasurementCarbonDioxide', @NewLoadDateFactMeasurementCarbonDioxide)

 drop table if exists #tmp
 --end of loading Carbon dioxide fact--