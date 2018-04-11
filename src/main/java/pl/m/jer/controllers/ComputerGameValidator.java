package pl.m.jer.controllers;

import pl.m.jer.data.ComputerGame;

public class ComputerGameValidator {

    public static Boolean areValuesEmpty(ComputerGame computerGame) {
        return (computerGame.getGameName() == null || computerGame.getGameName().isEmpty()) ||
                (computerGame.getGameType() == null) ||
                (computerGame.getAllowedAge() == null) ||
                (computerGame.getManufacturer() == null || computerGame.getManufacturer().isEmpty());
    }

    public static Boolean numericValidate(ComputerGame computerGame) {
        return computerGame.getAllowedAge().equals(0) || computerGame.getAllowedAge() < 0;
    }
}
