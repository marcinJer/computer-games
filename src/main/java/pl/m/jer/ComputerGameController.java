package pl.m.jer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ComputerGameController {

    ComputerGameRepository computerGamesList = new ComputerGameRepository();

    private Boolean areValuesEmpty(ComputerGame computerGame) {
        return (computerGame.getGameName() == null || computerGame.getGameName().isEmpty()) ||
                (computerGame.getGameType() == null || computerGame.getGameType().isEmpty()) ||
                (computerGame.getAllowedAge() == null) ||
                (computerGame.getManufacturer() == null || computerGame.getManufacturer().isEmpty());
    }

    @RequestMapping("/games")
    public List<ComputerGame> getAllComputerGames(@RequestParam(value = "filter", required = false, defaultValue = "") String namePhrase) {

        return computerGamesList.getComputerGames().stream()
                .filter(computerGame -> computerGame.getGameName().startsWith(namePhrase))
                .collect(Collectors.toList());

    }

    @PostMapping("/games")
    private ResponseEntity addComputerGame(@RequestBody ComputerGame computerGame) {
        if (areValuesEmpty(computerGame)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            computerGamesList.addGame(computerGame);
            return new ResponseEntity((HttpStatus.OK));
        }
    }

    @PutMapping("/games/{id}")
    private ResponseEntity updateComputerGame(@RequestBody ComputerGame computerGame, @PathVariable int id) {

        if (areValuesEmpty(computerGame)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            computerGamesList.update(computerGame, id);
            return new ResponseEntity((HttpStatus.OK));
        }
    }

    @DeleteMapping("/games/{id}")
    private void deleteComputerGame(@PathVariable int id) {
        computerGamesList.deleteGame(id);
    }

}
