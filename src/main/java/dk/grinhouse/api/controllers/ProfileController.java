package dk.grinhouse.api.controllers;


import dk.grinhouse.api.services.ProfileService;
import dk.grinhouse.models.ThresholdProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProfileController
{
  private final ProfileService profileService;

  public ProfileController(ProfileService profileService)
  {
    this.profileService = profileService;
  }

  @GetMapping("/api/profile")
  public ResponseEntity<List<ThresholdProfile>> getAllProfiles()
  {
    return ResponseEntity.ok(profileService.getAllProfiles());
  }

  @GetMapping("/api/profile/current")
  public ResponseEntity<ThresholdProfile> getCurrentProfile()
  {
    return ResponseEntity.ok(profileService.getCurrentProfile());
  }

  @PostMapping("/api/profile")
  public ResponseEntity insertNewProfile(@RequestBody ThresholdProfile newProfile)
  {
      profileService.InsertNewProfile(newProfile);
      return ResponseEntity.ok(HttpStatus.CREATED);
  }

  @PutMapping("/api/profile")
  public ResponseEntity updateProfile(@RequestBody ThresholdProfile newProfile)
  {
    profileService.UpdateProfile(newProfile);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @DeleteMapping("/api/profile/{id}")
  public ResponseEntity deleteProfile(@PathVariable("id") int id)
  {
    profileService.DeleteProfile(id);
    return ResponseEntity.ok(HttpStatus.OK);
  }
}
