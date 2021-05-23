package dk.grinhouse.persistence.repositories;

import dk.grinhouse.models.ACState;
import dk.grinhouse.models.CarbonDioxideGeneratorState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarbonDioxideGeneratorStateRepository extends JpaRepository<CarbonDioxideGeneratorState, Integer>
{
     @Query(value = "select top(1) carbonDioxideGeneratorId, isCarbonDioxideGeneratorOn, stateDateTime = max(stateDateTime), logs from GrinHouse.dbo.CarbonDioxideGeneratorState group by carbonDioxideGeneratorId, isCarbonDioxideGeneratorOn, logs", nativeQuery = true)
     CarbonDioxideGeneratorState getLatestCarbonDioxideGeneratorState();
}
