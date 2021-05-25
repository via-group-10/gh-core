import random

#ac-state
#humidifier
#carbon-dioxide

def create_measurement(hour, minute, second, day, month, year, mType, value):
    datetime = ("%04d-%02d-%02d %02d:%02d:%02d.000" % (year, month, day, hour, minute, second))
    return "INSERT INTO [dbo].[Measurement] VALUES(%2.2f, '%s', 1, '%s')\n" %  (value, datetime, mType)

def create_states(hour, minute, second, day, month, year, heater, cooler, humidifier, deHumidifier, generator):
    datetime = ("%04d-%02d-%02d %02d:%02d:%02d.000" % (year, month, day, hour, minute, second))
    q = ""
    q += "INSERT INTO [dbo].[ACState] VALUES(%d, %d, '%s', %d)\n" %  (heater, cooler, datetime, 1)
    q += "INSERT INTO [dbo].[HumidifierState] VALUES(%d, %d, '%s', %d)\n" %  (humidifier, deHumidifier, datetime, 1)
    q += "INSERT INTO [dbo].[CarbonDioxideGeneratorState] VALUES(%d, '%s', %d)\n" %  (generator, datetime, 1)
    return q

for year in range(2021, 2022, 1):
    for month in range(4, 7, 1):
        f = open("3measurements-"+str(month)+".sql", "w")
        f.write("USE [GrinHouse]\n\n")
        f.write("go\n")
        for day in range(1, 29, 1):
            for hour in range(0, 24, 1):
                for minute in range(0, 60, 5):
                    second=31
                    tmp=random.uniform(15, 26)
                    hum=random.uniform(0, 100)
                    cd=random.uniform(0, 10000)

                    heater=0
                    cooler=0
                    humidifier=0
                    generator=0
                    deHumidifier=0

                    if tmp>20:
                        cooler=1
                    else:
                        heater=1
                    if hum<50:
                        humidifier=1
                    else:
                        deHumidifier=1
                    if cd<5000:
                        generator=1
                        
                    f.write(create_measurement(hour, minute, second, day, month, year, 'temperature', tmp))
                    f.write(create_measurement(hour, minute, second, day, month, year, 'humidity', hum))
                    f.write(create_measurement(hour, minute, second, day, month, year, 'carbonDioxide', cd))
                    f.write(create_states(hour, minute, second, day, month, year, heater, cooler, humidifier, deHumidifier, generator))
                    f.write("\n")
        f.write("go\n")
        f.close()
