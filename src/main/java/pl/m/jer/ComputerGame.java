package pl.m.jer;

import javax.persistence.*;

@Entity
public class ComputerGame {

    @Id
    @GeneratedValue
    private static Integer counter = 1;

    @Column(name= "id", unique = true)
    private Integer id;

    @Column(name= "gameName")
    private String gameName;

    @Column(name= "gameType")
    private String gameType;

    @Column(name= "allowedAge")
    private Integer allowedAge;

    @Column(name= "manufacturer")
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
