package pl.m.jer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.m.jer.ComputerGame;
import pl.m.jer.ComputerGameValidator;
import pl.m.jer.repositories.SpringComputerGamesRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ComputerGameController {

    @Autowired
    private SpringComputerGamesRepository computerGameRepository;


    @Transactional
    @GetMapping("/games")
    private List<ComputerGame> getAllComputerGames(@RequestParam(value = "filter", required = false, defaultValue = "") String namePhrase) {


        return StreamSupport.stream(computerGameRepository.findAll().spliterator(), false)
                .filter(computerGame -> computerGame.getGameName().startsWith(namePhrase))
                .collect(Collectors.toList());

    }

    @PostMapping("/games")
    private ResponseEntity addComputerGame(@RequestBody ComputerGame computerGame) {
        if (ComputerGameValidator.areValuesEmpty(computerGame) || ComputerGameValidator.numericValidate(computerGame)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            computerGameRepository.save(computerGame);
            return new ResponseEntity((HttpStatus.OK));
        }
    }

    @PutMapping("/games/{id}")
    private ResponseEntity updateComputerGame(@RequestBody ComputerGame computerGame, @PathVariable int id) {

        if (ComputerGameValidator.areValuesEmpty(computerGame) || ComputerGameValidator.numericValidate(computerGame)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            computerGame.setId(id);
            computerGameRepository.save(computerGame);

            return new ResponseEntity((HttpStatus.OK));
        }
    }

    @DeleteMapping("/games/{id}")
    private void deleteComputerGame(@PathVariable int id) {
        computerGameRepository.deleteById(id);
    }

}
