package pl.m.jer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.m.jer.data.data.ComputerGame;
import pl.m.jer.data.ComputerGameBasic;
import pl.m.jer.repositories.ComputerGameRepository;
import pl.m.jer.data.data.ComputerGameValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ComputerGameController {

    @Autowired
    private ComputerGameRepository computerGameRepository;

    @GetMapping("/games")
    public List<ComputerGameBasic> getAllComputerGamesBasicInfo(@RequestParam(value = "filter", required = false, defaultValue = "") String namePhrase) {
        return StreamSupport.stream(computerGameRepository.findAll().spliterator(), false)
                .filter(computerGame -> computerGame.getGameName().startsWith(namePhrase))
                .map(computerGame -> new ComputerGameBasic(computerGame))
                .collect(Collectors.toList());

    }

    @GetMapping("/games/{id}")
    public ResponseEntity getComputerGameByIdWithDetails(@PathVariable int id) {
        Optional<ComputerGame> computerGameOptional = computerGameRepository.findById(id);
        if (computerGameOptional.isPresent()) {
            return new ResponseEntity(computerGameOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/games")
    public ResponseEntity addComputerGame(@RequestBody ComputerGame computerGame) {
        if (ComputerGameValidator.areValuesEmpty(computerGame) || ComputerGameValidator.numericValidate(computerGame)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            computerGameRepository.save(computerGame);
            return new ResponseEntity((HttpStatus.OK));
        }
    }

    @PutMapping("/games/{id}")
    public ResponseEntity updateComputerGame(@RequestBody ComputerGame computerGame, @PathVariable int id) {

        if (ComputerGameValidator.areValuesEmpty(computerGame) || ComputerGameValidator.numericValidate(computerGame)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            computerGame.setId(id);
            computerGameRepository.save(computerGame);

            return new ResponseEntity((HttpStatus.OK));
        }
    }

    @DeleteMapping("/games/{id}")
    public void deleteComputerGame(@PathVariable int id) {
        computerGameRepository.deleteById(id);
    }

}
