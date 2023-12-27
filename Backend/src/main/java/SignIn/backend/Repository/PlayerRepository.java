package SignIn.backend.Repository;

import SignIn.backend.Model.Player;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findById(int id);

    @Transactional
    void deleteById(int id);

    Player findByName(String name);

    Player findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByPassword(String password);
}