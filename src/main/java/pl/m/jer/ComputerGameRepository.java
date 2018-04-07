package pl.m.jer;

import java.util.ArrayList;
import java.util.List;

public class ComputerGameRepository implements ComputerGameRepo {

    List<ComputerGame> computerGames = new ArrayList<>();

    @Override
    public void addGame(ComputerGame game) {
        game.assignNewId();
        computerGames.add(game);
    }

    @Override
    public void deleteGame(int id) {
        computerGames.remove(id);
    }

    @Override
    public void update(ComputerGame game, int id) {

        ComputerGame existingGame = findComputerGameById(id);
        existingGame.setGameName(game.getGameName());
        existingGame.setGameType(game.getGameType());
        existingGame.setAllowedAge(game.getAllowedAge());
        existingGame.setManufacturer(game.getManufacturer());
    }

    @Override
    public List<ComputerGame> getComputerGames() {
        return computerGames;
    }

    @Override
    public ComputerGame findComputerGameById(int id) {

        return computerGames.stream()
                .filter(computerGame -> computerGame.getId().equals(id))
                .findFirst().orElseThrow(() -> new RuntimeException("Didn't find any computer game with id " + id));

    }
}
