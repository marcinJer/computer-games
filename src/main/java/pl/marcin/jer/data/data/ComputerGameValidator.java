package pl.marcin.jer.data.data;

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

    public static boolean specialCharacters(ComputerGame computerGame){

        String regex = "^[a-zA-Z]+$";
        return computerGame.getGameName().matches(regex) || computerGame.getManufacturer().matches(regex);
    }
}
