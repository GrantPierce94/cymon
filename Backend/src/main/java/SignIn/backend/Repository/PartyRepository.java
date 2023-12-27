package SignIn.backend.Repository;

import SignIn.backend.Model.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Long> {

    Party findByPartyName(String name);


}
