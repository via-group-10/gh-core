package dk.grinhouse.api;

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

     private final LocalDateTime DATE_TIME = LocalDateTime.of(2021, 5, 24, 11, 20);
     private final LocalDateTime DATE_TIME_OLDER = LocalDateTime.of(2021, 5, 24, 10, 0);

     @Test
     public void givenLatestMeasurements_whenGetLatestMeasurements_thenReturnJsonArray()
     {
          List<Measurement> measurementList = createMeasurements(15f, 90f, 900f);

          given(measurementService.getLatestMeasurements()).willReturn(measurementList);

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
     public void notGivenAllLatestMeasurements_whenGetLatestMeasurements_thenReturnJsonArray() {
          List<Measurement> measurementList = createMeasurements(15f, 90f);

          given(measurementService.getLatestMeasurements()).willReturn(measurementList);

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
     public void givenNoMeasurements_whenGetLatestMeasurements_throwNotFound() {
//          given(measurementService.getLatestMeasurements()).willReturn(null);

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


     private List<Measurement> createMeasurements(Float... values)
     {
          List<Measurement> measurements = new ArrayList<>();
          LocalDateTime time = LocalDateTime.from(DATE_TIME);
          for (int i = 0; i < values.length; i++) {
               int mod = i % 3;
               MeasurementTypeEnum type = MeasurementTypeEnum.temperature;
               switch (mod)
               {
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
               Measurement measurement = new Measurement(values[i], Timestamp.valueOf(time), 1, type);
               measurements.add(measurement);
          }
          return measurements;
     }

     private List<Measurement> createMeasurements(MeasurementTypeEnum type, Float... values)
     {
          List<Measurement> measurements = new ArrayList<>();
          for (Float value : values) {
               Measurement measurement = new Measurement(value, Timestamp.valueOf(DATE_TIME), 1, type);
               measurements.add(measurement);
          }
          return measurements;
     }
}
