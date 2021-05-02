package dk.grinhouse.api.services;

import dk.grinhouse.api.exceptions.CannotFindProfileID;
import dk.grinhouse.api.exceptions.InvalidGreenhouseIDException;
import dk.grinhouse.api.exceptions.InvalidProfileId;
import dk.grinhouse.models.Greenhouse;
import dk.grinhouse.models.ThresholdProfile;
import dk.grinhouse.persistence.repositories.IGreenhouseRepository;
import dk.grinhouse.persistence.repositories.IThresholdProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
public class ProfileService
{
  private final IThresholdProfileRepository thresholdProfileRepository;
  private final IGreenhouseRepository greenhouseRepository;

  @Autowired
  public ProfileService(IThresholdProfileRepository thresholdProfileRepository, IGreenhouseRepository greenhouseRepository)
  {
    this.thresholdProfileRepository = thresholdProfileRepository;
    this.greenhouseRepository = greenhouseRepository;
  }
  public List<ThresholdProfile> getAllProfiles()
  {
    return thresholdProfileRepository.findAll();
  }

  public ThresholdProfile getCurrentProfile()
  {
    // this, can output null indicating there's no active profile set
    return thresholdProfileRepository.findByActive(true);
  }

  public void InsertNewProfile(ThresholdProfile newProfile)
  {
    // check if the greenhouse id is valid
    if(!greenhouseRepository.existsById(newProfile.getGreenhouseId())){
      throw new InvalidGreenhouseIDException();
    }

    // profile id is set to null so that the database can generate a new id for the profile
    newProfile.setThresholdProfileId(0);

    // deactivate active profile, if the new profile is active
    if (newProfile.isActive())
      DeactivateCurrentProfile();

    // save the new profile to database
    thresholdProfileRepository.save(newProfile);
  }


  public void UpdateProfile(ThresholdProfile updateProfile)
  {
    // check if id was provided
    if(!thresholdProfileRepository.existsById(updateProfile.getThresholdProfileId())){
      throw new InvalidProfileId();
    }

    // deactivate active profiles, if the new profile is active
    if (updateProfile.isActive())
      DeactivateCurrentProfile();

    try { thresholdProfileRepository.save(updateProfile); }
    catch (Exception e) { throw new InvalidGreenhouseIDException(); }
  }

  private void DeactivateCurrentProfile()
  {
    // TODO: 5/2/2021 deactivate all profiles, just to be safe
    ThresholdProfile currentProfile = thresholdProfileRepository.findByActive(true);
    if(currentProfile != null){
      currentProfile.setActive(false);
      thresholdProfileRepository.save(currentProfile);
    }
  }
  public void DeleteProfile(int id)
  {
    try { thresholdProfileRepository.deleteById(id); }
    catch (Exception e) { throw new CannotFindProfileID(); }
  }
}
