package pl.marcin.jer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marcin.jer.entities.ComputerGame;
import pl.marcin.jer.entities.ComputerGameBasic;
import pl.marcin.jer.entities.Review;
import pl.marcin.jer.facades.ComputerGameReviewFacade;
import pl.marcin.jer.repositories.ReviewRepository;
import pl.marcin.jer.services.ComputerGameService;
import pl.marcin.jer.validators.ComputerGameValidator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * This class is a REST controller for computer games
 */

@RestController
@RequestMapping("/games")
public class ComputerGameController {

    @Autowired
    private ComputerGameService computerGameService;
    @Autowired
    private ComputerGameReviewFacade computerGameReviewFacade;


    /**
     * GET method returns all computer games showing only id and game name
     *
     * @param namePrefix
     * @return
     */
    @GetMapping
    public List<ComputerGameBasic> getAllComputerGamesBasicInfo(@RequestParam(value = "name", required = false, defaultValue = "") String namePrefix,
                                                                @RequestParam(value = "sort", required = false, defaultValue = "false") boolean shouldSort) {
        List<ComputerGameBasic> games = StreamSupport.stream(computerGameService.getAllComputerGames().spliterator(), false)
                .filter(computerGame -> computerGame.getGameName().startsWith(namePrefix))
                .map(computerGame -> new ComputerGameBasic(computerGame))
                .collect(Collectors.toList());
        if (shouldSort) {
            Collections.sort(games);
        }
        return games;
    }

    /**
     * GET method returns entities for one computer game
     *
     * @param id Computer game's id
     * @return OK when selected computer game exists, BAD_REQUEST when computer game does not exist
     */

    @GetMapping("/{id}")
    public ComputerGame getComputerGameByIdWithDetails(@PathVariable int id) {
            ComputerGame computerGame = computerGameService.findComputerGameById(id);
            return computerGame;
    }

    /**
     * POST method which adds new computer game
     *
     * @param computerGame computer game to add
     * @return OK if computer game was added, BAD_REQUEST if provided computer game was invalid
     */
    @PostMapping
    public ResponseEntity addComputerGame(@RequestBody ComputerGame computerGame) {
        if (ComputerGameValidator.areValuesEmpty(computerGame) || ComputerGameValidator.numericValidate(computerGame)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            computerGameService.saveComputerGame(computerGame);
            return new ResponseEntity((HttpStatus.OK));
        }
    }

    /**
     * PUT method to edit computer game
     *
     * @param computerGame computer game to edit
     * @param id           computer game's id
     * @return OK when computer game was edited , BAD_REQUEST when computer game hasn't been edited
     */
    @PutMapping("/{id}")
    public ResponseEntity updateComputerGame(@RequestBody ComputerGame computerGame, @PathVariable int id) {

        if (ComputerGameValidator.areValuesEmpty(computerGame) || ComputerGameValidator.numericValidate(computerGame) || (computerGameService.findComputerGameById(id) == null)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            computerGame.setId(id);
            computerGameService.saveComputerGame(computerGame);

            return new ResponseEntity((HttpStatus.OK));
        }
    }

    /**
     * DELETE method to delete computer game
     *
     * @param id computer game id
     */
    @DeleteMapping("/{id}")
    public void deleteComputerGame(@PathVariable int id) {
            computerGameService.deleteComputerGameById(id);
    }

    @PostMapping("/{computerGameId}/reviews")
    public Review addReviewToComputerGame(@RequestBody Review review, @PathVariable int computerGameId) {
            return computerGameService.addReviewToComputerGame(review, computerGameId);
    }

    @DeleteMapping("/{computerGameId}/reviews/{reviewId}")
    public void deleteComputerGamesReview(@PathVariable int computerGameId, @PathVariable int reviewId){
        computerGameReviewFacade.deleteComputerGamesReview(computerGameId, reviewId);
    }
}
