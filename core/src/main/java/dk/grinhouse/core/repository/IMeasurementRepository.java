package dk.grinhouse.core.repository;

import dk.grinhouse.core.shared.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMeasurementRepository extends JpaRepository<Measurement, Integer>
{
}
