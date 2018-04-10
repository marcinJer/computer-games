package pl.marcin.jer.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.marcin.jer.data.data.ComputerGame;

/**
 * This is an interface for creating computer game object
 */

public interface ComputerGameRepository extends CrudRepository<ComputerGame, Integer>{

}
