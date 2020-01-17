package com.marcin.jer.computerGames.services;

import com.marcin.jer.computerGames.entities.ComputerGame;
import com.marcin.jer.computerGames.exceptions.BadRequest;
import com.marcin.jer.computerGames.exceptions.Conflict;
import com.marcin.jer.computerGames.exceptions.NotFoundException;
import com.marcin.jer.computerGames.repositories.ComputerGameRepository;
import com.marcin.jer.computerGames.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComputerGameService {

    private final ComputerGameRepository computerGameRepository;
    private final UserContext userContext;

    public ComputerGameService(
            ComputerGameRepository computerGameRepository, UserContext userContext) {
        this.computerGameRepository = computerGameRepository;
        this.userContext = userContext;
    }

    public ComputerGame findComputerGameById(int id) {
        return computerGameRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Computer game with id = " + id + " does not exist"));
    }

    public Optional<ComputerGame> findComputerGameByName(String gameName) {
        return Optional.ofNullable(computerGameRepository.findByGameName(gameName));
    }

    public List<ComputerGame> getAllComputerGames() {
        return computerGameRepository.findAll();
    }

    public void addComputerGame(ComputerGame computerGame) {
        List<ComputerGame> computerGames = computerGameRepository.findAll();
        List<ComputerGame> repeatedGames =
                computerGames.stream()
                        .filter(
                                computerGame1 ->
                                        (computerGame1.getGameName().equals(computerGame.getGameName()))
                                                && (computerGame1.getGameType().equals(computerGame.getGameType()))
                                                && (computerGame1.getAllowedAge().equals(computerGame.getAllowedAge())))
                        .collect(Collectors.toList());
        if (repeatedGames.isEmpty()) {
            computerGameRepository.save(computerGame);
        } else throw new Conflict("Computer game already exists");
    }

    public ComputerGame save(ComputerGame computerGame) {
        return computerGameRepository.save(computerGame);
    }

    public void deleteById(int id) {
        if (computerGameRepository.existsById(id)) {
            computerGameRepository.deleteById(id);
        } else throw new BadRequest("Computer game doesn't exists");
    }

    public List<ComputerGame> findAllLoggedUsersComputerGames() {
        int id = userContext.getCurrentUser().getId();
        return computerGameRepository.findAllUsersComputerGames(id);
    }
}
