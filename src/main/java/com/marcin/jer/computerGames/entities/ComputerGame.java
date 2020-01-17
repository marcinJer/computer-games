package com.marcin.jer.computerGames.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marcin.jer.computerGames.enums.TypesOfGames;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "computer_game")
public class ComputerGame {

    /**
     * Computer game's fields
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String gameName;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TypesOfGames gameType;

    @NotNull
    private Integer allowedAge;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "game_users",
            joinColumns = {@JoinColumn(name = "computer_game_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<User> users;

    @OneToMany(mappedBy = "computerGame", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public ComputerGame() {
    }

    /**
     * Computer game's constructor
     *
     * @param gameName   Computer game's name
     * @param gameType   Computer game's type
     * @param allowedAge Computer game's allowed age
     */
    public ComputerGame(String gameName, TypesOfGames gameType, Integer allowedAge) {
        this.gameName = gameName;
        this.gameType = gameType;
        this.allowedAge = allowedAge;
    }

    @JsonIgnore
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @JsonIgnore
    public Set<User> getUsers() {
        return users;
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
}
