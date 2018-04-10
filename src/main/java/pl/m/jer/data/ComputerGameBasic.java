package pl.m.jer.data;

public class ComputerGameBasic {

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

}
