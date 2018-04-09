package pl.m.jer.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import pl.m.jer.ComputerGame;

import java.util.LinkedList;
import java.util.List;

@Primary
@Component
public class DbComputerGameRepository implements ComputerGameRepository {

    @Autowired
    private SpringComputerGamesRepository springComputerGamesRepository;

    @Override
    public void addGame(ComputerGame game) {
        springComputerGamesRepository.save(game);
    }

    @Override
    public void deleteGame(int id) {
        springComputerGamesRepository.deleteById(id);
    }

    @Override
    public void update(ComputerGame game, int id) {
        game.setId(id);
        springComputerGamesRepository.save(game);
    }

    @Override
    public List<ComputerGame> getComputerGames() {

        Iterable<ComputerGame> computerGamesFromDB = springComputerGamesRepository.findAll();

        List<ComputerGame> computerGames = new LinkedList<>();

        for (ComputerGame computerGame : computerGamesFromDB) {
            computerGames.add(computerGame);
        }
        return computerGames;
    }

    @Override
    public ComputerGame findComputerGameById(int id) {

        return springComputerGamesRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }
}
