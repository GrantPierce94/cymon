package com.example.LeaderBoard.backend.Repository;


import com.example.LeaderBoard.backend.Model.Player;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findById(int id);

    @Transactional
    void deleteById(int id);
}
