package com.marcin.jer.computerGames.controllers;

import com.marcin.jer.computerGames.facades.UserComputerGameFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.marcin.jer.computerGames.entities.ComputerGame;
import com.marcin.jer.computerGames.entities.ComputerGameBasic;
import com.marcin.jer.computerGames.entities.Review;
import com.marcin.jer.computerGames.services.ComputerGameService;
import com.marcin.jer.computerGames.validators.ComputerGameValidator;

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

    private final ComputerGameService computerGameService;
    private final UserComputerGameFacade userComputerGameFacade;

    public ComputerGameController(
            ComputerGameService computerGameService, UserComputerGameFacade userComputerGameFacade) {
        this.computerGameService = computerGameService;
        this.userComputerGameFacade = userComputerGameFacade;
    }

    /**
     * GET method returns all computer games showing only id and game name
     *
     * @param namePrefix text needed to search computer game by
     * @return games if any found by namePrefix
     */
    @GetMapping
    public ResponseEntity<List<ComputerGameBasic>> getAllComputerGamesBasicInfo(
            @RequestParam(value = "name", required = false, defaultValue = "") String namePrefix,
            @RequestParam(value = "sort", required = false, defaultValue = "false") boolean shouldSort) {
        List<ComputerGameBasic> games =
                StreamSupport.stream(computerGameService.getAllComputerGames().spliterator(), false)
                        .filter(computerGame -> computerGame.getGameName().startsWith(namePrefix))
                        .map(ComputerGameBasic::new)
                        .collect(Collectors.toList());
        if (shouldSort) {
            Collections.sort(games);
        }
        return ResponseEntity.ok(games);
    }

    /**
     * GET method returns entities for one computer game
     *
     * @param id Computer game's id
     * @return OK when selected computer game exists, BAD_REQUEST when computer game does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity getComputerGameByIdWithDetails(@PathVariable int id) {
        return ResponseEntity.ok(computerGameService.getComputerGameById(id));
    }

    /**
     * POST method which adds new computer game
     *
     * @param computerGame computer game to add
     * @return OK if computer game was added, BAD_REQUEST if provided computer game was invalid
     */
    @PostMapping
    public ResponseEntity addComputerGame(@RequestBody ComputerGame computerGame) {
        if (ComputerGameValidator.areValuesEmpty(computerGame)
                || ComputerGameValidator.numericValidate(computerGame)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity(computerGameService.addComputerGame(computerGame), HttpStatus.CREATED);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity addComputerGameToUsersCollection(@PathVariable int id) {
        return ResponseEntity.ok(userComputerGameFacade.addComputerGameToUsersCollection(id));
    }

    /**
     * PUT method to edit computer game
     *
     * @param computerGame computer game to edit
     * @param id computer game's id
     * @return OK when computer game was edited , BAD_REQUEST when computer game hasn't been edited
     */
    @PutMapping("/{id}")
    public ResponseEntity updateComputerGame(
            @RequestBody ComputerGame computerGame, @PathVariable int id) {

        if (ComputerGameValidator.areValuesEmpty(computerGame)
                || ComputerGameValidator.numericValidate(computerGame)
                || (computerGameService.getComputerGameById(id) == null)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            computerGame.setId(id);
            return ResponseEntity.ok(computerGameService.addComputerGame(computerGame));
        }
    }

    /**
     * DELETE method to delete computer game
     *
     * @param id computer game id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteComputerGame(@PathVariable int id) {
        userComputerGameFacade.deleteComputerGameByIdFromSystem(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/{computerGameId}/review")
    public ResponseEntity addReviewToComputerGame(@RequestBody Review review, @PathVariable int computerGameId) {
        if (computerGameService.findOutIfExists(computerGameId)) {
            return ResponseEntity.ok(computerGameService.addReviewToComputerGame(review, computerGameId));
        } else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
