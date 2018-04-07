package pl.m.jer;

import java.util.List;

public interface ComputerGameRepo {

    void addGame(ComputerGame game);
    void deleteGame(int id);
    void update(ComputerGame game, int id);

    List<ComputerGame> getComputerGames();

    ComputerGame findComputerGameById(int id);
}
