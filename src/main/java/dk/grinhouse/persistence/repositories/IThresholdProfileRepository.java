package dk.grinhouse.persistence.repositories;

import dk.grinhouse.models.ThresholdProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface IThresholdProfileRepository extends JpaRepository<ThresholdProfile,Integer>
{
  /*@Query(value = "delete * from GrinHouse.stage.thresholdProfile where thresholdProfileId = :id",nativeQuery = true)
  void deleteProfile(@Param("id") int id);*/

  ThresholdProfile findByActive(boolean active);
}
