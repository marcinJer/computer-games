package com.marcin.jer.computerGames.validators;

import com.marcin.jer.computerGames.entities.ComputerGame;

public class ComputerGameValidator {

  /**
   * Method to validate if any field is empty
   *
   * @param computerGame Computer game
   * @return true when field is empty
   */
  public static Boolean areValuesEmpty(ComputerGame computerGame) {
    return (computerGame.getGameName() == null || computerGame.getGameName().isEmpty())
        || (computerGame.getGameType() == null)
        || (computerGame.getAllowedAge() == null);
  }

  /**
   * Method to validate if provided number in allowedAge field is lower or equals to 0
   *
   * @param computerGame Computer game
   * @return
   */
  public static Boolean numericValidate(ComputerGame computerGame) {
    return computerGame.getAllowedAge().equals(0) || computerGame.getAllowedAge() < 0;
  }
}
