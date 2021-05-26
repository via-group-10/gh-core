

/****** Populating test source db ******/


--insert data into Greenhouse
INSERT INTO [TestGrinHouse].[dbo].[Greenhouse]
(
	   [greenhouseId]
      ,[greenhouseName]
      ,[loginName]
      ,[loginPassword])
SELECT [greenhouseId]
      ,[greenhouseName]
      ,[loginName]
      ,[loginPassword]
  FROM [GrinHouse].[dbo].[Greenhouse]



--insert data into MeasurementType
  INSERT INTO [TestGrinHouse].[dbo].[MeasurementType]
  (
	[title])
  SELECT [title]
  FROM [GrinHouse].[dbo].[MeasurementType]

  --insert data into ThresholdProfile
  SET IDENTITY_INSERT [TestGrinHouse].[dbo].[ThresholdProfile] ON
  INSERT INTO [TestGrinHouse].[dbo].[ThresholdProfile]
  ([thresholdProfileId]
      ,[profileName]
      ,[active]
      ,[minimumTemperature]
      ,[maximumTemperature]
      ,[minimumHumidity]
      ,[maximumHumidity]
      ,[minimumCarbonDioxide]
      ,[maximumCarbonDioxide]
      ,[storedIn])
  SELECT [thresholdProfileId]
      ,[profileName]
      ,[active]
      ,[minimumTemperature]
      ,[maximumTemperature]
      ,[minimumHumidity]
      ,[maximumHumidity]
      ,[minimumCarbonDioxide]
      ,[maximumCarbonDioxide]
      ,[storedIn]
  FROM [GrinHouse].[dbo].[ThresholdProfile]
  SET IDENTITY_INSERT [TestGrinHouse].[dbo].[ThresholdProfile] OFF



  --insert data into Measurement
  SET IDENTITY_INSERT [TestGrinHouse].[dbo].[Measurement] ON
  INSERT INTO [TestGrinHouse].[dbo].[Measurement]
  ([measurementId]
      ,[measuredValue]
      ,[measurementDateTime]
      ,[belongsTo]
      ,[isOfType])
  SELECT [measurementId]
      ,[measuredValue]
      ,[measurementDateTime]
      ,[belongsTo]
      ,[isOfType]
  FROM [GrinHouse].[dbo].[Measurement]
  WHERE [measurementId] % 100 = 0
  SET IDENTITY_INSERT [TestGrinHouse].[dbo].[Measurement] OFF



  --truncate table [TestGrinHouse].[dbo].[ACState]
    --insert data into ACState
  SET IDENTITY_INSERT [TestGrinHouse].[dbo].[ACState] ON
  INSERT INTO [TestGrinHouse].[dbo].[ACState]
  ([acStateId]
      ,[isHeaterOn]
      ,[isCoolerOn]
      ,[stateDateTime]
      ,[logs])
  SELECT [acStateId]
      ,[isHeaterOn]
      ,[isCoolerOn]
      ,[stateDateTime]
      ,[logs]
  FROM [GrinHouse].[dbo].[ACState] ac
  INNER JOIN [TestGrinHouse].[dbo].[Measurement] as m on ac.stateDateTime = m.measurementDateTime
  WHERE isOfType = 'temperature'
  SET IDENTITY_INSERT [TestGrinHouse].[dbo].[ACState] OFF

  --truncate table [TestGrinHouse].[dbo].[HumidifierState]
  --insert data into HumidifierState
  SET IDENTITY_INSERT [TestGrinHouse].[dbo].[HumidifierState] ON
  INSERT INTO [TestGrinHouse].[dbo].[HumidifierState]
  ([humidifierId]
      ,[isHumidifierOn]
      ,[isDeHumidifierOn]
      ,[stateDateTime]
      ,[logs])
  SELECT [humidifierId]
      ,[isHumidifierOn]
      ,[isDeHumidifierOn]
      ,[stateDateTime]
      ,[logs]
  FROM [GrinHouse].[dbo].[HumidifierState] h
  INNER JOIN [TestGrinHouse].[dbo].[Measurement] as m on h.stateDateTime = m.measurementDateTime
  WHERE isOfType = 'humidity'
  SET IDENTITY_INSERT [TestGrinHouse].[dbo].[HumidifierState] OFF


  --truncate table [TestGrinHouse].[dbo].[CarbonDioxideGeneratorState]
    --insert data into CarbonDioxideGeneratorState
  SET IDENTITY_INSERT [TestGrinHouse].[dbo].[CarbonDioxideGeneratorState] ON
  INSERT INTO [TestGrinHouse].[dbo].[CarbonDioxideGeneratorState]
  ([carbonDioxideGeneratorId]
      ,[isCarbonDioxideGeneratorOn]
      ,[stateDateTime]
      ,[logs])
  SELECT [carbonDioxideGeneratorId]
      ,[isCarbonDioxideGeneratorOn]
      ,[stateDateTime]
      ,[logs]
  FROM [GrinHouse].[dbo].[CarbonDioxideGeneratorState] cd
  INNER JOIN [TestGrinHouse].[dbo].[Measurement] as m on cd.stateDateTime = m.measurementDateTime
  WHERE isOfType = 'carbonDioxide'
  SET IDENTITY_INSERT [TestGrinHouse].[dbo].[CarbonDioxideGeneratorState] OFF