package SignIn.backend.Repository;



import SignIn.backend.Model.Game;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
    Game findById(int id);

    @Transactional
    void deleteById(int id);
}
