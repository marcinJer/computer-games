package pl.m.jer;

import javax.persistence.*;

@Entity
public class ComputerGame {

    @Id
    @GeneratedValue
    private static Integer counter = 1;

    private Integer id;

    private String gameName;

    private String gameType;

    private Integer allowedAge;

    private String manufacturer;

    public ComputerGame(String gameName, String gameType, Integer allowedAge, String manufacturer, Integer id) {
        this.gameName = gameName;
        this.gameType = gameType;
        this.allowedAge = allowedAge;
        this.manufacturer = manufacturer;
    }

    public ComputerGame() {
    }

    public void assignNewId() {
        this.id = counter++;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public Integer getAllowedAge() {
        return allowedAge;
    }

    public void setAllowedAge(Integer allowedAge) {
        this.allowedAge = allowedAge;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
