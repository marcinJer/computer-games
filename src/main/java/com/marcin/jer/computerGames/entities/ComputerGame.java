package com.marcin.jer.computerGames.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marcin.jer.computerGames.enums.TypesOfGames;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ComputerGame {

    /**
     * Computer game's fields
     */
    @Id
    @GeneratedValue
    private Integer id;
    private String gameName;
    private TypesOfGames gameType;
    private Integer allowedAge;
    private String manufacturer;

    @OneToMany(mappedBy = "computerGame", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    /**
     * Computer game's constructor
     *
     * @param gameName     Computer game's name
     * @param gameType     Computer game's type
     * @param allowedAge   Computer game's allowed age
     * @param manufacturer Computer game's manufacturer
     * @param id           Computer game's id
     */
    public ComputerGame(String gameName, TypesOfGames gameType, Integer allowedAge, String manufacturer, Integer id) {
        this.gameName = gameName;
        this.gameType = gameType;
        this.allowedAge = allowedAge;
        this.manufacturer = manufacturer;
    }

    public ComputerGame() {
    }

    @JsonIgnore
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
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
