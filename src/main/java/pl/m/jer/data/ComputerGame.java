package pl.m.jer.data;

import javax.persistence.*;

@Entity
public class ComputerGame {

    @Id
    @GeneratedValue
    private Integer id;

    private String gameName;

    private TypesOfGames gameType;

    private Integer allowedAge;

    private String manufacturer;

    public ComputerGame(String gameName, TypesOfGames gameType, Integer allowedAge, String manufacturer, Integer id) {
        this.gameName = gameName;
        this.gameType = gameType;
        this.allowedAge = allowedAge;
        this.manufacturer = manufacturer;
    }

    public ComputerGame() {
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

    public TypesOfGames getGameType() {
        return gameType;
    }

    public void setGameType(TypesOfGames gameType) {
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
