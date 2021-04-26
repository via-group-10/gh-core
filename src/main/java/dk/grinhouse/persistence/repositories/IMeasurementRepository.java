package dk.grinhouse.persistence.repositories;

import dk.grinhouse.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMeasurementRepository extends JpaRepository<Measurement, Integer>
{
}
