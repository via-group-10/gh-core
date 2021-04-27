package dk.grinhouse.api.services;

import dk.grinhouse.api.exceptions.CannotFindProfileID;
import dk.grinhouse.api.exceptions.InvalidGreenhouseIDException;
import dk.grinhouse.models.ThresholdProfile;
import dk.grinhouse.persistence.repositories.IThresholdProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class ProfileService
{
  private final IThresholdProfileRepository thresholdProfileRepository;

  @Autowired
  public ProfileService(IThresholdProfileRepository thresholdProfileRepository)
  {
    this.thresholdProfileRepository = thresholdProfileRepository;
  }
  public List<ThresholdProfile> getAllProfiles()
  {
    return thresholdProfileRepository.findAll();
  }

  public ThresholdProfile getCurrentProfile()
  {
    return thresholdProfileRepository.findByActive(true);
  }

  public void InsertNewProfile(ThresholdProfile newProfile)
  {
    newProfile.setThresholdProfileId(0);
    UpdateProfile(newProfile);
  }

  public void UpdateProfile(ThresholdProfile updateProfile)
  {
    if (updateProfile.isActive())
    {
      ThresholdProfile active = getCurrentProfile();
      active.setActive(false);
      thresholdProfileRepository.save(active);
    }

    try
    {
      thresholdProfileRepository.save(updateProfile);
    }
    catch (Exception e)
    {
      throw new InvalidGreenhouseIDException();
    }
  }

  public void DeleteProfile(int id)
  {
    try
    {
      thresholdProfileRepository.deleteById(id);
    }
    catch (Exception e)
    {
      throw new CannotFindProfileID();
    }
  }
}
