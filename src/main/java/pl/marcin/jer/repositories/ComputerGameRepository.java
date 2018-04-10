package pl.marcin.jer.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.marcin.jer.data.data.ComputerGame;

public interface ComputerGameRepository extends CrudRepository<ComputerGame, Integer>{

}
