package coms309.roundTrip.demo.repository;

import coms309.roundTrip.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
