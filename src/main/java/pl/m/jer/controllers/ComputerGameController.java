package pl.m.jer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.m.jer.ComputerGame;
import pl.m.jer.repositories.SpringComputerGamesRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ComputerGameController {

    @Autowired
    private SpringComputerGamesRepository computerGameRepository;

    private Boolean areValuesEmpty(ComputerGame computerGame) {
        return (computerGame.getGameName() == null || computerGame.getGameName().isEmpty()) ||
                (computerGame.getGameType() == null || computerGame.getGameType().isEmpty()) ||
                (computerGame.getAllowedAge() == null) ||
                (computerGame.getManufacturer() == null || computerGame.getManufacturer().isEmpty());
    }

    private Boolean numericValidate(ComputerGame computerGame) {
        return computerGame.getAllowedAge().equals(0) || computerGame.getAllowedAge() < 0;
    }

    @Transactional
    @GetMapping("/games")
    private List<ComputerGame> getAllComputerGames(@RequestParam(value = "filter", required = false, defaultValue = "") String namePhrase) {


        return StreamSupport.stream(computerGameRepository.findAll().spliterator(), false)
                .filter(computerGame -> computerGame.getGameName().startsWith(namePhrase))
                .collect(Collectors.toList());

    }

    @PostMapping("/games")
    private ResponseEntity addComputerGame(@RequestBody ComputerGame computerGame) {
        if (areValuesEmpty(computerGame) || numericValidate(computerGame)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            computerGameRepository.save(computerGame);
            return new ResponseEntity((HttpStatus.OK));
        }
    }

    @PutMapping("/games/{id}")
    private ResponseEntity updateComputerGame(@RequestBody ComputerGame computerGame, @PathVariable int id) {

        if (areValuesEmpty(computerGame) || numericValidate(computerGame)) {
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
