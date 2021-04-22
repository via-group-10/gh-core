package dk.grinhouse.core.repository;

import dk.grinhouse.core.shared.model.Greenhouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface IGreenhouseRepository extends JpaRepository<Greenhouse, Integer>
{
}
