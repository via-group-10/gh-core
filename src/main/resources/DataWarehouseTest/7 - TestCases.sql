/* Temperature */
--average temperature
SELECT AVG(measuredValue) as AverageTemperatureSource
FROM [TestGrinHouse].[dbo].[Measurement]
WHERE [isOfType] = 'temperature'

SELECT AVG(measuredValue) as AverageTemperatureEDW
FROM [TestGrinHouseDW].[edw].[FactMeasurementTemperature]

--sum temperature
SELECT SUM(measuredValue) as SumTemperatureSource
FROM [TestGrinHouse].[dbo].[Measurement]
WHERE [isOfType] = 'temperature'

SELECT SUM(measuredValue) as SumTemperatureEDW
FROM [TestGrinHouseDW].[edw].[FactMeasurementTemperature]


/* Humidity */
--average humidity
SELECT AVG(measuredValue) as AverageHumiditySource
FROM [TestGrinHouse].[dbo].[Measurement]
WHERE [isOfType] = 'humidity'

SELECT AVG(measuredValue) as AverageHumidityEDW
FROM [TestGrinHouseDW].[edw].[FactMeasurementHumidity]


/* Carbon dioxide */
--average carbon dioxide
SELECT AVG(measuredValue) as AverageCarbonDioxideSource
FROM [TestGrinHouse].[dbo].[Measurement]
WHERE [isOfType] = 'carbonDioxide'

SELECT AVG(measuredValue) as AverageCarbonDioxideEDW
FROM [TestGrinHouseDW].[edw].[FactMeasurementCarbonDioxide]


/* Greenhouse */
--number of greenhouses
SELECT COUNT(greenhouseId) as CountGreenhousesSource
FROM [TestGrinHouse].[dbo].[Greenhouse]

SELECT COUNT(greenhouseId) as CountGreenhousesEDW
FROM [TestGrinHouseDW].[edw].[DimGreenhouse]


/* ACState */
--number of times ac is on
SELECT COUNT(*) as CountACIsOnSource
FROM [TestGrinHouse].[dbo].[ACState]
WHERE [isHeaterOn] = 1

SELECT COUNT(*) as CountACIsOnEDW
FROM [TestGrinHouseDW].[edw].[FactMeasurementTemperature]
WHERE [isHeaterOn] = 1

--number of times cooler is on
SELECT COUNT(*) as CountCoolerIsOnSource
FROM [TestGrinHouse].[dbo].[ACState]
WHERE [isCoolerOn] = 1

SELECT COUNT(*) as CountCoolerIsOnEDW
FROM [TestGrinHouseDW].[edw].[FactMeasurementTemperature]
WHERE [isCoolerOn] = 1


/* HumidifierState */
--number of times humdifier is on
SELECT COUNT(*) as CountHumdifierIsOnSource
FROM [TestGrinHouse].[dbo].[HumidifierState]
WHERE [isHumidifierOn] = 1

SELECT COUNT(*) as CountHumdifierIsOnEDW
FROM [TestGrinHouseDW].[edw].[FactMeasurementHumidity]
WHERE [isHumidifierOn] = 1

--number of times dehumdifier is on
SELECT COUNT(*) as CountDeHumdifierIsOnSource
FROM [TestGrinHouse].[dbo].[HumidifierState]
WHERE [isDeHumidifierOn] = 1

SELECT COUNT(*) as CountDeHumdifierIsOnEDW
FROM [TestGrinHouseDW].[edw].[FactMeasurementHumidity]
WHERE [isDeHumidifierOn] = 1


/* CarbonDioxideGeneratorState */
--number of times carbon dioxide generator is on
SELECT COUNT(*) as CountCarbonDioxideIsOnSource
FROM [TestGrinHouse].[dbo].[CarbonDioxideGeneratorState]
WHERE [isCarbonDioxideGeneratorOn] = 1

SELECT COUNT(*) as CountCarbonDioxideIsOnEDW
FROM [TestGrinHouseDW].[edw].[FactMeasurementCarbonDioxide]
WHERE [isCarbonDioxideGeneratorOn] = 1