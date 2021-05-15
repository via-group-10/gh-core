package dk.grinhouse.persistence.repositories;

import dk.grinhouse.models.ACState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IACStateRepository extends JpaRepository<ACState, Integer>
{
     @Query(value = "select acStateId, isHeaterOn, isCoolerOn, stateDateTime = max(stateDateTime), logs from GrinHouse.stage.ACState group by acStateId, isHeaterOn, isCoolerOn, logs", nativeQuery = true)
     ACState getLatestACState();
}
