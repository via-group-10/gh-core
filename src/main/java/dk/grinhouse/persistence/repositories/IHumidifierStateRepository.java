package dk.grinhouse.persistence.repositories;

import dk.grinhouse.models.ACState;
import dk.grinhouse.models.HumidifierState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IHumidifierStateRepository extends JpaRepository<HumidifierState, Integer>
{
     @Query(value = "select humidifierId, isHumidifierOn, isDehumidifierOn, stateDateTime = max(stateDateTime), logs from GrinHouse.stage.HumidifierState group by humidifierId, isHumidifierOn, isDehumidifierOn, logs", nativeQuery = true)
     ACState getLatestHumidifierState();
}
