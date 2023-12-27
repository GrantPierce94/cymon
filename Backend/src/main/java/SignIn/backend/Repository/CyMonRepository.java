package SignIn.backend.Repository;

import SignIn.backend.Model.CyMon;
import SignIn.backend.Model.Game;
import SignIn.backend.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface CyMonRepository extends JpaRepository<CyMon, Long> {

}
