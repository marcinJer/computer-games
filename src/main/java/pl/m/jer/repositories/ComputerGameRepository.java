package pl.m.jer.repositories;

import pl.m.jer.ComputerGame;

import java.util.List;

public interface ComputerGameRepository {

    void addGame(ComputerGame game);
    void deleteGame(int id);
    void update(ComputerGame game, int id);

    List<ComputerGame> getComputerGames();

    ComputerGame findComputerGameById(int id);
}
