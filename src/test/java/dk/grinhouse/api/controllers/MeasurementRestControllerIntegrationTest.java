package dk.grinhouse.api.controllers;

import dk.grinhouse.api.controllers.MeasurementController;
import dk.grinhouse.api.services.MeasurementService;
import dk.grinhouse.models.Measurement;
import dk.grinhouse.models.MeasurementTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MeasurementController.class)
public class MeasurementRestControllerIntegrationTest
{
     @Autowired
     private MockMvc mvc;

     @MockBean
     private MeasurementService measurementService;

     private final LocalDateTime DATE_TIME = LocalDateTime.of(2021, 5, 24, 10, 0, 0, 0);
     private final LocalDateTime DATE_TIME_OLDER = LocalDateTime.of(2021, 5, 23, 10, 0, 0, 0);

     private final Random random = new Random();

     @Test
     public void givenLatestMeasurements_whenGetLatestMeasurements_thenReturnJsonArray()
     {
          List<Measurement> measurementList = createMeasurements(15f, 90f, 900f);

          willReturn(measurementList).given(measurementService).getLatestMeasurements();

          try {
               mvc.perform(get("/api/measurement/latest").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].measurementValue").value(15))
                    .andExpect(jsonPath("$[0].measurementTypeEnum").value("temperature"))
                    .andExpect(jsonPath("$[1].measurementValue").value(90))
                    .andExpect(jsonPath("$[1].measurementTypeEnum").value("humidity"))
                    .andExpect(jsonPath("$[2].measurementValue").value(900))
                    .andExpect(jsonPath("$[2].measurementTypeEnum").value("carbonDioxide"));
          }
          catch (Exception e) {
               e.printStackTrace();
               fail("Could not perform mvc");
          }
     }

     @Test
     public void notGivenAllLatestMeasurements_whenGetLatestMeasurements_thenReturnJsonArray()
     {
          List<Measurement> measurementList = createMeasurements(15f, 90f);

          willReturn(measurementList).given(measurementService).getLatestMeasurements();

          try {
               mvc.perform(get("/api/measurement/latest").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].measurementValue").value(15))
                    .andExpect(jsonPath("$[0].measurementTypeEnum").value("temperature"))
                    .andExpect(jsonPath("$[1].measurementValue").value(90))
                    .andExpect(jsonPath("$[1].measurementTypeEnum").value("humidity"))
                    .andExpect(jsonPath("$[2]").doesNotExist());
          }
          catch (Exception e) {
               e.printStackTrace();
               fail("Could not perform mvc");
          }
     }

     @Test
     public void givenNoMeasurements_whenGetLatestMeasurements_throwNotFound()
     {
          willReturn(null).given(measurementService).getLatestMeasurements();

          try {
               mvc.perform(get("/api/measurement/latest").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
          }
          catch (Exception e) {
               e.printStackTrace();
               fail("Could not perform mvc");
          }
     }

     @Test
     public void givenNoTempMeasurements_whenGetLatest5TempMeasurements_returnEmptyList()
     {
          List<Measurement> expected = createMeasurements(MeasurementTypeEnum.temperature);

          willReturn(expected).given(measurementService).getTemperatureMeasurements(0);

          try {
               mvc.perform(get("/api/measurement/temperature?top=5")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0]").doesNotExist());
          }
          catch (Exception e) {
               e.printStackTrace();
               fail("Could not perform mvc");
          }
     }

     @Test
     public void given5TempMeasurements_whenGetLatest5TempMeasurements_return5TempMeasurements()
     {
          List<Measurement> expected = createMeasurements(MeasurementTypeEnum.temperature, 20f, 21f,
               22f, 23f, 24f);

          willReturn(expected).given(measurementService).getTemperatureMeasurements(5);

          try {
               mvc.perform(get("/api/measurement/temperature?top=5")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0]").exists())
                    .andExpect(jsonPath("$[4]").exists())
                    .andExpect(jsonPath("$[5]").doesNotExist());
          }
          catch (Exception e) {
               e.printStackTrace();
               fail("Could not perform mvc");
          }
     }

     @Test
     public void given10TempMeasurements_whenGetLatest10TempMeasurements_return10TempMeasurements()
     {
          List<Measurement> expected = createMeasurements(MeasurementTypeEnum.temperature, 20f, 21f,
               22f, 23f, 24f, 20f, 21f, 22f, 23f, 24f);

          willReturn(expected).given(measurementService).getTemperatureMeasurements(10);

          try {
               mvc.perform(get("/api/measurement/temperature?top=10")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0]").exists())
                    .andExpect(jsonPath("$[9]").exists())
                    .andExpect(jsonPath("$[10]").doesNotExist());
          }
          catch (Exception e) {
               e.printStackTrace();
               fail("Could not perform mvc");
          }
     }

     @Test
     public void givenNoHumMeasurements_whenGetLatest5HumMeasurements_returnEmptyList()
     {
          List<Measurement> expected = createMeasurements(MeasurementTypeEnum.humidity);

          willReturn(expected).given(measurementService).getHumidityMeasurements(0);

          try {
               mvc.perform(
                    get("/api/measurement/humidity?top=5").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0]").doesNotExist());
          }
          catch (Exception e) {
               e.printStackTrace();
               fail("Could not perform mvc");
          }
     }

     @Test
     public void given5HumMeasurements_whenGetLatest5HumMeasurements_return5HumMeasurements()
     {
          List<Measurement> expected = createMeasurements(MeasurementTypeEnum.humidity, 80f, 81f,
               82f, 83f, 84f);

          willReturn(expected).given(measurementService).getHumidityMeasurements(5);

          try {
               mvc.perform(
                    get("/api/measurement/humidity?top=5").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0]").exists())
                    .andExpect(jsonPath("$[4]").exists())
                    .andExpect(jsonPath("$[5]").doesNotExist());
          }
          catch (Exception e) {
               e.printStackTrace();
               fail("Could not perform mvc");
          }
     }

     @Test
     public void given10HumMeasurements_whenGetLatest10HumMeasurements_return10HumMeasurements()
     {
          List<Measurement> expected = createMeasurements(MeasurementTypeEnum.humidity, 80f, 81f,
               82f, 83f, 84f, 80f, 81f, 82f, 83f, 84f);

          willReturn(expected).given(measurementService).getHumidityMeasurements(10);

          try {
               mvc.perform(
                    get("/api/measurement/humidity?top=10").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0]").exists())
                    .andExpect(jsonPath("$[9]").exists())
                    .andExpect(jsonPath("$[10]").doesNotExist());
          }
          catch (Exception e) {
               e.printStackTrace();
               fail("Could not perform mvc");
          }
     }

     @Test
     public void given1DayTempMeasurements_whenRangeFromOneDayOlderDateTimeToDateTime_return289Measurements()
     {
          List<Measurement> expected = createMeasurements(MeasurementTypeEnum.temperature);

          willReturn(expected).given(measurementService)
               .getTemperatureWithinDate(Timestamp.valueOf(DATE_TIME_OLDER),
                    Timestamp.valueOf(DATE_TIME));

          String url =
               "/api/measurement/temperature/range?from=" + Timestamp.valueOf(DATE_TIME_OLDER)
                    + "&to=" + Timestamp.valueOf(DATE_TIME);

          try {
               mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0]").exists())
                    .andExpect(jsonPath("$[288]").exists())
                    .andExpect(jsonPath("$[289]").doesNotExist());
          }
          catch (Exception e) {
               e.printStackTrace();
               fail("Could not perform mvc");
          }
     }

     private List<Measurement> createMeasurements()
     {
          List<Measurement> measurements = new ArrayList<>();
          LocalDateTime time = LocalDateTime.from(DATE_TIME);
          MeasurementTypeEnum type;
          while (DATE_TIME_OLDER.isBefore(time) || DATE_TIME_OLDER.isEqual(time)) {
               for (int j = 0; j < 3; j++) {
                    float value;
                    switch (j) {
                         case 0:
                              type = MeasurementTypeEnum.temperature;
                              value = random.nextInt(10) + 20;
                              break;
                         case 1:
                              type = MeasurementTypeEnum.humidity;
                              value = random.nextInt(20) + 75;
                              break;
                         case 2:
                              type = MeasurementTypeEnum.carbonDioxide;
                              value = random.nextInt(400) + 700;
                              break;
                         default:
                              type = MeasurementTypeEnum.temperature;
                              value = 20;
                              break;
                    }
                    Measurement measurement = new Measurement(value, Timestamp.valueOf(time), 1,
                         type);
                    measurements.add(measurement);
               }
               time = time.minusMinutes(5);
          }
          return measurements;
     }

     private List<Measurement> createMeasurements(MeasurementTypeEnum type)
     {
          List<Measurement> measurements = new ArrayList<>();
          LocalDateTime time = LocalDateTime.from(DATE_TIME);
          while (DATE_TIME_OLDER.isBefore(time) || DATE_TIME_OLDER.isEqual(time)) {
               float value = random.nextInt(10) + 20;
               Measurement measurement = new Measurement(value, Timestamp.valueOf(time), 1, type);
               measurements.add(measurement);
               time = time.minusMinutes(5);
          }
          return measurements;
     }

     private List<Measurement> createMeasurements(Float... values)
     {
          List<Measurement> measurements = new ArrayList<>();
          LocalDateTime time = LocalDateTime.from(DATE_TIME);
          for (int i = 0; i < values.length; i++) {
               int mod = i % 3;
               MeasurementTypeEnum type = MeasurementTypeEnum.temperature;
               switch (mod) {
                    case 0:
                         type = MeasurementTypeEnum.temperature;
                         time = time.minusMinutes(5);
                         break;
                    case 1:
                         type = MeasurementTypeEnum.humidity;
                         break;
                    case 2:
                         type = MeasurementTypeEnum.carbonDioxide;
                         break;
               }
               Measurement measurement = new Measurement(values[i], Timestamp.valueOf(time), 1,
                    type);
               measurements.add(measurement);
          }
          return measurements;
     }

     private List<Measurement> createMeasurements(MeasurementTypeEnum type, Float... values)
     {
          List<Measurement> measurements = new ArrayList<>();
          for (Float value : values) {
               Measurement measurement = new Measurement(value, Timestamp.valueOf(DATE_TIME), 1,
                    type);
               measurements.add(measurement);
          }
          return measurements;
     }
}
