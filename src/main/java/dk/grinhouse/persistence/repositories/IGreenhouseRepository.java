package dk.grinhouse.persistence.repositories;

import dk.grinhouse.models.Greenhouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGreenhouseRepository extends JpaRepository<Greenhouse, Integer>
{
}
