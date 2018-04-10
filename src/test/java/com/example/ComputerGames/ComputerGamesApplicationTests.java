package com.example.ComputerGames;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.marcin.jer.data.data.ComputerGame;
import pl.marcin.jer.data.data.ComputerGameValidator;
import pl.marcin.jer.data.data.TypesOfGames;
import pl.marcin.jer.repositories.ComputerGameRepository;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(JUnit4.class)
public class ComputerGamesApplicationTests {

	@Test
	public void shouldBeInvalidWhenGameNameIsEmpty() {
		ComputerGame computerGame = new ComputerGame("", TypesOfGames.Action, 18, "CdProject RED", 1);
        ComputerGameValidator computerGameValidator = new ComputerGameValidator();
        boolean result = computerGameValidator.areValuesEmpty(computerGame);
        assertThat(result).isTrue();
	}

	@Test
	public void shouldBeInvalidWhenGameTypeIsEmpty() {
		ComputerGame computerGame = new ComputerGame("Gothic", null, 12, "Piranha Bytes", 1);
		ComputerGameValidator computerGameValidator = new ComputerGameValidator();
		boolean result = computerGameValidator.areValuesEmpty(computerGame);
		assertThat(result).isTrue();
	}

	@Test
	public void shouldBeInvalidWhenAllowedAgeIsEmpty() {
		ComputerGame computerGame = new ComputerGame("Mafia", TypesOfGames.Action, null, "Illusion Softworks", 1);
		ComputerGameValidator computerGameValidator = new ComputerGameValidator();
		boolean result = computerGameValidator.areValuesEmpty(computerGame);
		assertThat(result).isTrue();
	}

	@Test
	public void shouldBeInvalidWhenManufacturerIsEmpty() {
		ComputerGame computerGame = new ComputerGame("Far Cry", TypesOfGames.FPS, 12, "", 1);
		ComputerGameValidator computerGameValidator = new ComputerGameValidator();
		boolean result = computerGameValidator.areValuesEmpty(computerGame);
		assertThat(result).isTrue();
	}

	@Test
	public void shouldBeInvalidWhenAllowedAgeLessThanZero() {
		ComputerGame computerGame = new ComputerGame("Fifa 18", TypesOfGames.Arcade, -12, "EA Sports", 1);
		ComputerGameValidator computerGameValidator = new ComputerGameValidator();
		boolean result = computerGameValidator.numericValidate(computerGame);
		assertThat(result).isFalse();
	}

}
