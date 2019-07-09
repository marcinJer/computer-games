package pl.marcin.jer.entities;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue
    private Integer id;
    private String description;

    @ManyToOne
    private ComputerGame computerGame;

    public Review(String description) {
        this.description = description;
    }

    public ComputerGame getComputerGame() {
        return computerGame;
    }

    public void setComputerGame(ComputerGame computerGame) {
        this.computerGame = computerGame;
    }

    public Review() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
