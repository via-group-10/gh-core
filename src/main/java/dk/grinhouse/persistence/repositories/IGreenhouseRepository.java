package dk.grinhouse.persistence.repositories;

import dk.grinhouse.models.Greenhouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IGreenhouseRepository extends JpaRepository<Greenhouse, Integer>
{
  @Query(value = "SELECT * FROM GrinHouse.dbo.greenhouse where loginName = :username and loginPassword = :password",nativeQuery = true)
  Greenhouse getGreenhouse(@Param("username") String username, @Param("password") String password);
}
