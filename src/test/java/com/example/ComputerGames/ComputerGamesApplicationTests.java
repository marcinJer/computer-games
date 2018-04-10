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

	@Test
	public void shouldBeInvalidWhenGameNameIsEmpty() {
		ComputerGame computerGame = new ComputerGame("", TypesOfGames.Action, 12, "Brand", 1);
        ComputerGameValidator computerGameValidator = new ComputerGameValidator();
        boolean result = computerGameValidator.areValuesEmpty(computerGame);
        assertThat(result).isTrue();
	}

}
