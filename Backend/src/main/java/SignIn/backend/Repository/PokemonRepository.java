package SignIn.backend.Repository;

import SignIn.backend.Model.Player;
import SignIn.backend.Model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {


    Pokemon findBySpecies(String pikachu);
}
