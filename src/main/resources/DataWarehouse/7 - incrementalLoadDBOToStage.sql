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
alter table [GrinHouseDW].[stage].[DimGreenhouse] add ValidFrom int, ValidTo int;
alter table [GrinHouseDW].[stage].[DimMeasurementType] add ValidFrom int, ValidTo int;

/*Updating values in tables*/
update [GrinHouseDW].[stage].[DimGreenhouse] set ValidFrom = 20200401, ValidTo = 99990101;
update [GrinHouseDW].[stage].[DimMeasurementType] set ValidFrom = 20200401, ValidTo = 99990101;


----------GREENHOUSE----------

/*Include these with each  greenhouse dbo > stage*/
declare @LastLoadDateGreenhouse int set @LastLoadDateGreenhouse = (select MAX([LastLoadDate]) from [etl].[LogUpdate] where [Table] = 'DimGreenhouse')
declare @NewLoadDateGreenhouse int set @NewLoadDateGreenhouse = CONVERT(CHAR(8), GETDATE(), 112)
declare @FutureDateGreenhouse int set @FutureDateGreenhouse = 99990101


/*INSERTING greenhouse dbo > stage*/

  SELECT [greenhouseId] --Select all the records from the dbo production database
  ,[greenhouseName]
  INTO #tmp -- All the selected records (except those that are already in stage and new records in stage) are inserted into tmp
  FROM [GrinHouse].[dbo].[greenhouse]
  EXCEPT SELECT [greenhouseId]  --Except those which are already in stage
  ,[greenhouseName]
  FROM [GrinHouseDW].[stage].[DimGreenhouse]

  INSERT INTO [GrinHouseDW].[stage].[DimGreenhouse]( -- Here is where we start inserting all the records from tmp into stage
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
  
/*End of INSERTING greenhouse dbo > stage*/


/*UPDATING greenhouse dbo > stage*/

	 SELECT [greenhouseId] --Select all the records from the dbo production database
	,[greenhouseName]
	 INTO #tmp -- All the selected records (except those that are already in stage and new records in stage) are inserted into tmp
	 FROM [GrinHouse].[dbo].[greenhouse]
	 EXCEPT SELECT [greenhouseId]  --Except those which are already in stage
	,[greenhouseName]
	 FROM [GrinHouseDW].[stage].[DimGreenhouse]
	 EXCEPT SELECT [greenhouseId]  --Except new records
	,[greenhouseName] from [GrinHouse].[dbo].[greenhouse]
	WHERE greenhouseId in (SELECT [greenhouseId] FROM [GrinHouse].[dbo].[greenhouse] EXCEPT SELECT [greenhouseId] FROM [GrinHouseDW].[stage].[DimGreenhouse] WHERE [ValidTo] = 99990101)

	UPDATE [GrinHouseDW].[stage].[DimGreenhouse]
	SET [greenhouseName] = t.greenhouseName,
	[ValidTo] = @FutureDateGreenhouse																																																																																																																																																																																																																																																																																																																																																																																																																																																																															
	FROM #tmp t
	WHERE [GrinHouseDW].[stage].[DimGreenhouse].[greenhouseId] = t.greenhouseId AND [GrinHouseDW].[stage].[DimGreenhouse].[ValidTo] = 99990101
	

	INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('DimGreenhouse', @NewLoadDateGreenhouse) -- We create a log in the LogUpdate table

	drop table if exists #tmp -- we must make sure to drop the tmp table at the end

/*End of UPDATING greenhouse dbo > stage*/

/*DELETING Greenhouse dbo > stage*/

UPDATE [GrinHouseDW].[stage].[DimGreenhouse]
SET ValidTo = @NewLoadDateGreenhouse-1
WHERE [greenhouseId] in (
SELECT [greenhouseId]
FROM [GrinHouseDW].[stage].[DimGreenhouse]
WHERE [greenhouseId] IN (
SELECT [greenhouseId] FROM [GrinHouseDW].[stage].[DimGreenhouse]
EXCEPT SELECT [greenhouseId] from [GrinHouse].[dbo].[greenhouse]))

INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('DimGreenhouse', @NewLoadDateGreenhouse)

/*End of DELETING Greenhouse dbo > stage*/


----------END GREENHOUSE----------


----------MEASUREMENT TYPE----------


  /*INSERTING measurement type dbo > stage*/

declare @LastLoadDateMeasurementType int set @LastLoadDateMeasurementType = (select MAX([LastLoadDate]) from [etl].[LogUpdate] where [Table] = 'DimMeasurementType')
declare @NewLoadDateMeasurementType int set @NewLoadDateMeasurementType = CONVERT(CHAR(8), GETDATE(), 112)
declare @FutureDateMeasurementType int set @FutureDateMeasurementType = 99990101


SELECT [title] --Select all the records from the dbo production database
  INTO #tmp -- All the selected records (except those that are already in stage and new records in stage) are inserted into tmp
  FROM [GrinHouse].[dbo].[measurementType]
  EXCEPT SELECT [title]  --Except those which are already in stage
  FROM [GrinHouseDW].[stage].[DimMeasurementType]

  INSERT INTO [GrinHouseDW].[stage].[DimMeasurementType]( -- Here is where we start inserting all the records from tmp into stage
  [title], [ValidFrom],[ValidTo])
  SELECT  [title]
  ,@NewLoadDateMeasurementType
  ,@FutureDateMeasurementType
  from #tmp

  INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('DimMeasurementType', @NewLoadDateMeasurementType) -- We create a log in the LogUpdate table

	DROP TABLE IF EXISTS #tmp -- we must make sure to drop the tmp table at the end
  
 /*End of INSERTING measurement type dbo > stage*/
 

 /*UPDATING measurement type dbo > stage*/

SELECT [title] --Select all the EDITED records in dbo database
  INTO #tmp -- Which means we use an EXCEPT 
  FROM [GrinHouse].[dbo].[measurementType]
  EXCEPT SELECT [title]  --For those which are already in stage
  FROM [GrinHouseDW].[stage].[DimMeasurementType]
  EXCEPT SELECT [title]  --And except the new records
  FROM [GrinHouse].[dbo].[measurementType]
  WHERE [title] in (SELECT [title] FROM [GrinHouse].[dbo].[measurementType] EXCEPT SELECT [title] FROM [GrinHouseDW].[stage].[DimGreenhouse] WHERE [ValidTo] = 99990101)


	UPDATE [GrinHouseDW].[stage].[DimMeasurementType]
	SET [title] = t.title,
	[ValidTo] = @FutureDateGreenhouse																																																																																																																																																																																																																																																																																																																																																																																																																																																																															
	FROM #tmp t
	WHERE [GrinHouseDW].[stage].[DimMeasurementType].[title] = t.title AND [GrinHouseDW].[stage].[DimMeasurementType].[ValidTo] = 99990101
 

	INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('DimGreenhouse', @NewLoadDateGreenhouse) -- We create a log in the LogUpdate table

	drop table if exists #tmp -- we must make sure to drop the tmp table at the end

 /*End of UPDATING measurement type dbo > stage*/
 

  /*DELETING MeasurementType dbo > stage*/

  UPDATE [GrinHouseDW].[stage].[DimMeasurementType]
  SET ValidTo = @NewLoadDateMeasurementType-1
  WHERE [title] IN (
  SELECT [title] 
  FROM [GrinHouseDW].[stage].[DimMeasurementType] 
  WHERE [title] IN (
  SELECT [title] FROM [GrinHouseDW].[stage].[DimMeasurementType]
  EXCEPT SELECT [title] FROM [GrinHouse].[dbo].[measurementType]))

  INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('DimMeasurementType', @NewLoadDateMeasurementType)

  /*End of DELETING MeasurementType dbo > stage*/


  ----------END MEASUREMENT TYPE----------


  ----------STAGING FACTS----------

declare @LastLoadDateFactMeasurementTemperature int set @LastLoadDateFactMeasurementTemperature = (select MAX([LastLoadDate]) from [etl].[LogUpdate] where [Table] = 'FactMeasurementTemperature')
declare @NewLoadDateFactMeasurementTemperature int set @NewLoadDateFactMeasurementTemperature = (CONVERT(CHAR(8), GETDATE(), 112))
declare @FutureDateFactMeasurementTemperature int set @FutureDateFactMeasurementTemperature = 99990101


TRUNCATE TABLE [GrinhouseDW].[stage].[FactMeasurementTemperature]

/*Staging facts*/

--TEMPERATURE--
INSERT INTO [GrinHouseDW].[stage].[FactMeasurementTemperature] ([measurementId]
, [measuredValue]
, [measurementDateTime]
, [isHeaterOn]
, [isCoolerOn]
, [greenhouseId]
, [title])
SELECT [measurementId]
 ,[measuredValue]
 ,[measurementDateTime]
 ,ac.[isHeaterOn]
 ,ac.[isCoolerOn]
 ,[belongsTo]
 ,[isOfType]
 FROM [GrinHouse].[dbo].[measurement] m
 INNER JOIN [GrinHouse].[dbo].[ACState] as ac on ac.stateDateTime = m.measurementDateTime
 WHERE (CONVERT(CHAR(8), measurementDateTime , 112)) < (@LastLoadDateFactMeasurementTemperature) AND [isOfType] = 'temperature'
 EXCEPT SELECT 
 [measurementId]
 ,[measuredValue]
 ,[measurementDateTime]
  ,ac.[isHeaterOn]
 ,ac.[isCoolerOn]
 ,[greenhouseId]
 ,[title]
 FROM [GrinHouseDW].[stage].[FactMeasurementTemperature] m
 INNER JOIN [GrinHouse].[dbo].[ACState] as ac on ac.stateDateTime = m.measurementDateTime
 

  INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('FactMeasurementTemperature', @NewLoadDateFactMeasurementTemperature)

 drop table if exists #tmp -- we must make sure to drop the tmp table at the end

-----End of staging temperature facts-----

--HUMIDITY--
 declare @LastLoadDateFactMeasurementHumidity int set @LastLoadDateFactMeasurementHumidity= (select MAX([LastLoadDate]) from [etl].[LogUpdate] where [Table] = 'FactMeasurementHumidity')
declare @NewLoadDateFactMeasurementHumidity int set @NewLoadDateFactMeasurementHumidity = (CONVERT(CHAR(8), GETDATE(), 112))
declare @FutureDateFactMeasurementHumidity int set @FutureDateFactMeasurementHumidity = 99990101

TRUNCATE TABLE [GrinhouseDW].[stage].[FactMeasurementHumidity]

 INSERT INTO [GrinHouseDW].[stage].[FactMeasurementHumidity] ([measurementId]
, [measuredValue]
, [measurementDateTime]
, [isHumidifierOn]
, [isDehumidifierOn]
, [greenhouseId]
, [title])
SELECT [measurementId]
 ,[measuredValue]
 ,[measurementDateTime]
 ,hs.[isHumidifierOn]
 ,hs.[isDeHumidifierOn]
 ,[belongsTo]
 ,[isOfType]
 FROM [GrinHouse].[dbo].[measurement] m
 INNER JOIN [GrinHouse].[dbo].[humidifierState] as hs on hs.stateDateTime = m.measurementDateTime 
 WHERE (CONVERT(CHAR(8), measurementDateTime , 112)) < (@LastLoadDateFactMeasurementHumidity) AND [isOfType] = 'humidity'
 EXCEPT SELECT 
 [measurementId]
 ,[measuredValue]
 ,[measurementDateTime]
  ,hs.[isHumidifierOn]
 ,hs.[isDeHumidifierOn]
 ,[greenhouseId]
 ,[title]
 FROM [GrinHouseDW].[stage].[FactMeasurementHumidity] m
 INNER JOIN [GrinHouse].[dbo].[humidifierState] as hs on hs.stateDateTime = m.measurementDateTime
 

  INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('FactMeasurementHumidity', @NewLoadDateFactMeasurementHumidity)

 drop table if exists #tmp -- we must make sure to drop the tmp table at the end

-----End of staging humidity facts-----
--Carbon dioxide--
 declare @LastLoadDateFactMeasurementCarbonDioxide int set @LastLoadDateFactMeasurementCarbonDioxide= (select MAX([LastLoadDate]) from [etl].[LogUpdate] where [Table] = 'FactMeasurementCarbonDioxide')
declare @NewLoadDateFactMeasurementCarbonDioxide int set @NewLoadDateFactMeasurementCarbonDioxide = (CONVERT(CHAR(8), GETDATE(), 112))
declare @FutureDateFactMeasurementCarbonDioxide int set @FutureDateFactMeasurementCarbonDioxide = 99990101

TRUNCATE TABLE [GrinhouseDW].[stage].[FactMeasurementCarbonDioxide]

 INSERT INTO [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] ([measurementId]
, [measuredValue]
, [measurementDateTime]
, isCarbonDioxideGeneratorOn
, [greenhouseId]
, [title])
SELECT [measurementId]
 ,[measuredValue]
 ,[measurementDateTime]
 ,hs.[isCarbonDioxideGeneratorOn]
 ,[belongsTo]
 ,[isOfType]
 FROM [GrinHouse].[dbo].[measurement] m
 INNER JOIN [GrinHouse].[dbo].CarbonDioxideGeneratorState as hs on hs.stateDateTime = m.measurementDateTime
 WHERE (CONVERT(CHAR(8), measurementDateTime , 112)) < (@LastLoadDateFactMeasurementCarbonDioxide) AND [isOfType] = 'carbonDioxide'
 EXCEPT SELECT 
 [measurementId]
 ,[measuredValue]
 ,[measurementDateTime]
  ,hs.isCarbonDioxideGeneratorOn
 ,[greenhouseId]
 ,[title]
 FROM [GrinHouseDW].[stage].[FactMeasurementCarbonDioxide] m
 INNER JOIN [GrinHouse].[dbo].CarbonDioxideGeneratorState as hs on hs.stateDateTime = m.measurementDateTime
 

  INSERT INTO [etl].[LogUpdate] ([Table], [LastLoadDate]) VALUES ('FactMeasurementCarbonDioxide', @NewLoadDateFactMeasurementCarbonDioxide)

 drop table if exists #tmp
 /*End of staging facts*/