package dk.grinhouse.api.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.grinhouse.api.exceptions.InvalidGreenhouseIDException;
import dk.grinhouse.api.services.ProfileService;
import dk.grinhouse.models.ThresholdProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProfileController.class)
public class ProfileControllerTest
{
     @Autowired
     private MockMvc mvc;

     @MockBean
     private ProfileService profileService;

     private Gson jsonConverter = new GsonBuilder().setPrettyPrinting().create();
     private final LocalDateTime DATE_TIME = LocalDateTime.of(2021, 5, 24, 11, 20);
     private ThresholdProfile profile;


     @Before
     public void setup()
     {
          profile = new ThresholdProfile(1, "default", true, 15, 25, 80, 90, 900, 1000, 1);
     }

     @Test
     public void givenNoProfile_postCompleteInactiveProfile_returnCreated()
     {
          profile.setActive(false);
          String jsonProfile = jsonConverter.toJson(profile);

          try {
               mvc.perform(post("/api/profile/")
                         .content(jsonProfile)
                         .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("\"CREATED\""));
          }
          catch (Exception e) {
               e.printStackTrace();
               fail("Could not perform mvc");
          }
     }

     @Test
     public void givenNoProfile_postCompleteActiveProfile_returnCreated()
     {
          String jsonProfile = jsonConverter.toJson(profile);

          try {
               mvc.perform(post("/api/profile/")
                         .content(jsonProfile)
                         .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("\"CREATED\""));
          }
          catch (Exception e) {
               e.printStackTrace();
               fail("Could not perform mvc");
          }
     }

     @Test
     public void givenNoProfile_postProfileIncorrectGreenhouse_returnWrongGreenhouseId()
     {
          profile.setGreenhouseId(0);
          String jsonProfile = jsonConverter.toJson(profile);

          willThrow(new InvalidGreenhouseIDException()).willDoNothing().given(profileService).InsertNewProfile(profile);

          try {
               mvc.perform(post("/api/profile/")
                         .content(jsonProfile)
                         .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string("Wrong Greenhouse ID entered"));
          }
          catch (Exception e) {
               e.printStackTrace();
               fail("Could not perform mvc");
          }
     }

     @Test
     public void givenNoProfile_postProfileMissingParameter_returnCreated()
     {
          profile.setGreenhouseId(0);
          String jsonProfile = jsonConverter.toJson(profile);

          try {
               mvc.perform(post("/api/profile/")
                         .content(jsonProfile)
                         .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string("Wrong Greenhouse ID entered"));
          }
          catch (Exception e) {
               e.printStackTrace();
               fail("Could not perform mvc");
          }
     }
}