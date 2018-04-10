package com.example.ComputerGames;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.marcin.jer.data.data.ComputerGame;
import pl.marcin.jer.data.data.ComputerGameValidator;
import pl.marcin.jer.data.data.TypesOfGames;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(JUnit4.class)
public class ComputerGamesApplicationTests {

	/**
	 * Method to test if there is empty gameName field
	 */
	@Test
	public void shouldBeInvalidWhenGameNameIsEmpty() {
		ComputerGame computerGame = new ComputerGame("", TypesOfGames.Action, 18, "CdProject RED", 1);
        ComputerGameValidator computerGameValidator = new ComputerGameValidator();
        boolean result = computerGameValidator.areValuesEmpty(computerGame);
        assertThat(result).isTrue();
	}

	/**
	 * Method to test if there is empty gameType field
	 */
	@Test
	public void shouldBeInvalidWhenGameTypeIsEmpty() {
		ComputerGame computerGame = new ComputerGame("Gothic", null, 12, "Piranha Bytes", 1);
		ComputerGameValidator computerGameValidator = new ComputerGameValidator();
		boolean result = computerGameValidator.areValuesEmpty(computerGame);
		assertThat(result).isTrue();
	}

	/**
	 * Method to test if there is empty allowedAge field
	 */
	@Test
	public void shouldBeInvalidWhenAllowedAgeIsEmpty() {
		ComputerGame computerGame = new ComputerGame("Mafia", TypesOfGames.Action, null, "Illusion Softworks", 1);
		ComputerGameValidator computerGameValidator = new ComputerGameValidator();
		boolean result = computerGameValidator.areValuesEmpty(computerGame);
		assertThat(result).isTrue();
	}

	/**
	 * Method to test if there is empty manufacturer field
	 */
	@Test
	public void shouldBeInvalidWhenManufacturerIsEmpty() {
		ComputerGame computerGame = new ComputerGame("Far Cry", TypesOfGames.FPS, 12, "", 1);
		ComputerGameValidator computerGameValidator = new ComputerGameValidator();
		boolean result = computerGameValidator.areValuesEmpty(computerGame);
		assertThat(result).isTrue();
	}

	/**
	 * Method to test if allowedAge field contains number less or equals 0
	 */

	@Test
	public void shouldBeInvalidWhenAllowedAgeLessThanZero() {
		ComputerGame computerGame = new ComputerGame("Fifa 18", TypesOfGames.Arcade, -12, "EA Sports", 1);
		ComputerGameValidator computerGameValidator = new ComputerGameValidator();
		boolean result = computerGameValidator.numericValidate(computerGame);
		assertThat(result).isTrue();
	}

}
