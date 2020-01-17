package com.marcin.jer.computerGames.entities;

public class ComputerGameBasic implements Comparable<ComputerGameBasic> {

    private Integer id;
    private String gameName;

    public ComputerGameBasic(ComputerGame computerGame) {
        this.id = computerGame.getId();
        this.gameName = computerGame.getGameName();
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

    @Override
    public int compareTo(ComputerGameBasic game) {
        return this.getGameName().compareTo(game.getGameName());
    }

}
