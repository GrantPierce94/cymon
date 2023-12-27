package SignIn.backend.Repository;

import SignIn.backend.Model.Moves;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovesRepository extends JpaRepository<Moves, Long> {

}
