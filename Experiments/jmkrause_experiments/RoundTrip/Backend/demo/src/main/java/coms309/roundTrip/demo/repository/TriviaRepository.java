package coms309.roundTrip.demo.repository;


import coms309.roundTrip.demo.model.Trivia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TriviaRepository extends JpaRepository<Trivia, Long> {}
