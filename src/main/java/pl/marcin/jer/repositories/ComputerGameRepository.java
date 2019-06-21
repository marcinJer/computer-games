package pl.marcin.jer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.marcin.jer.entities.ComputerGame;

/**
 * This is an interface for creating computer game object
 */

@Repository
public interface ComputerGameRepository extends JpaRepository<ComputerGame, Integer> {

}
