package lab4.demo.dao;

import lab4.demo.models.Attempt;
import lab4.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByUser(User user);

    void deleteByUser(User user);
}
