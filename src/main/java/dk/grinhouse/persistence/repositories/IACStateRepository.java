package dk.grinhouse.persistence.repositories;

import dk.grinhouse.models.ACState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IACStateRepository extends JpaRepository<ACState, Integer>
{
     @Query(value = "select top(1) acStateId, isHeaterOn, isCoolerOn, stateDateTime = max(stateDateTime), logs from GrinHouse.dbo.ACState group by acStateId, isHeaterOn, isCoolerOn, logs", nativeQuery = true)
     ACState getLatestACState();
}
