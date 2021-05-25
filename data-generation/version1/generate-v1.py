import random
    
def create_sql(hour, minute, second, day, month, year, mType, value):
    datetime = ("%04d-%02d-%02d %02d:%02d:%02d.000" % (year, month, day, hour, minute, second))
    return "INSERT INTO [dbo].[Measurement] VALUES(%2.2f, '%s', 1, '%s')\n" %  (value, datetime, mType)



for year in range(2021, 2022, 1):
    for month in range(1, 13, 1):
        f = open("3measurements-"+str(month)+".sql", "w")
        f.write("USE [GrinHouse]\n\n")
        f.write("go\n")
        for day in range(1, 29, 1):
            for hour in range(0, 24, 1):
                for minute in range(0, 60, 5):
                    f.write(create_sql(hour, minute, 32, day, month, year, 'temperature', random.uniform(15, 26)))
                    f.write(create_sql(hour, minute, 32, day, month, year, 'humidity', random.uniform(0, 100)))
                    f.write(create_sql(hour, minute, 32, day, month, year, 'carbonDioxide', random.uniform(0, 10000)))
        f.write("go\n")
        f.close()
