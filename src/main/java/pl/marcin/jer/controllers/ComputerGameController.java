package pl.marcin.jer.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marcin.jer.entities.ComputerGame;
import pl.marcin.jer.entities.ComputerGameBasic;
import pl.marcin.jer.services.ComputerGameService;
import pl.marcin.jer.validators.ComputerGameValidator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * This class is a REST controller for computer games
 */

@RestController
public class ComputerGameController {

    @Autowired
    private ComputerGameService computerGameService;


    /**
     * GET method returns all computer games showing only id and game name
     *
     * @param namePrefix
     * @return
     */
    @GetMapping("/games")
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

    @GetMapping("/games/{id}")
    public ResponseEntity getComputerGameByIdWithDetails(@PathVariable int id) {
        if (computerGameService.findIfExists(id)) {
            ComputerGame computerGame = computerGameService.findComputerGameById(id);
            return new ResponseEntity(computerGame, HttpStatus.OK);
        } else {
            return new ResponseEntity("Computer game with id = " + id + " does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * POST method which adds new computer game
     *
     * @param computerGame computer game to add
     * @return OK if computer game was added, BAD_REQUEST if provided computer game was invalid
     */
    @PostMapping("/games")
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
    @PutMapping("/games/{id}")
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
    @DeleteMapping("/games/{id}")
    public void deleteComputerGame(@PathVariable int id) {
        computerGameService.deleteComputerGameById(id);
    }

}
