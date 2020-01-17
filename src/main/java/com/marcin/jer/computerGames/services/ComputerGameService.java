package com.marcin.jer.computerGames.services;

import com.marcin.jer.computerGames.entities.ComputerGame;
import com.marcin.jer.computerGames.entities.Review;
import com.marcin.jer.computerGames.exceptions.BadRequest;
import com.marcin.jer.computerGames.exceptions.Conflict;
import com.marcin.jer.computerGames.exceptions.NotFoundException;
import com.marcin.jer.computerGames.repositories.ComputerGameRepository;
import com.marcin.jer.computerGames.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ComputerGame getComputerGameById(int id) {
        return computerGameRepository
                .findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Computer game with id = " + id + " does not exist!"));
    }

    public Boolean findOutIfExists(int id) {
        return computerGameRepository.existsById(id);
    }

    public ComputerGame getComputerGameByName(String gameName) {
        return computerGameRepository.findByGameName(gameName)
                .orElseThrow(() ->
                        new NotFoundException("Computer game with name: " + gameName + " does not exists!"));
    }

    public List<ComputerGame> getAllComputerGames() {
        return computerGameRepository.findAll();
    }

    public ComputerGame addComputerGame(ComputerGame computerGame) {
        List<ComputerGame> computerGames = computerGameRepository.findAll();
        List<ComputerGame> repeatedGames =
                computerGames.stream()
                        .filter(computerGame1 ->
                                (computerGame1.getGameName().equals(computerGame.getGameName()))
                                        && (computerGame1.getGameType().equals(computerGame.getGameType()))
                                        && (computerGame1.getAllowedAge().equals(computerGame.getAllowedAge())))
                        .collect(Collectors.toList());
        if (repeatedGames.isEmpty()) {
            return computerGameRepository.save(computerGame);
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

    public List<ComputerGame> getAllLoggedUsersComputerGames() {
        int id = userContext.getCurrentUser().getId();
        return computerGameRepository.findAllUsersComputerGames(id);
    }

    public Review addReviewToComputerGame(Review review, int computerGameId) {
        ComputerGame computerGame = getComputerGameById(computerGameId);

        review.setComputerGame(computerGame);
        computerGame.getReviews().add(review);
        computerGame.setId(computerGameId);
        computerGameRepository.save(computerGame);
        return review;
    }
}
