package lab4.demo.repositories;

import lab4.demo.models.Attempt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptRepository extends CrudRepository<Attempt, Long> {

}
