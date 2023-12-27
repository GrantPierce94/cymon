package SignIn.backend.Repository;

import SignIn.backend.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(int id);

    @Transactional
    void deleteById(int id);
}
