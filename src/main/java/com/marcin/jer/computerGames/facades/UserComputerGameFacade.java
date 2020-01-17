package com.marcin.jer.computerGames.facades;

import com.marcin.jer.computerGames.entities.ComputerGame;
import com.marcin.jer.computerGames.entities.User;
import com.marcin.jer.computerGames.enums.RoleName;
import com.marcin.jer.computerGames.exceptions.ForbiddenException;
import com.marcin.jer.computerGames.security.UserContext;
import com.marcin.jer.computerGames.services.ComputerGameService;
import com.marcin.jer.computerGames.services.UserService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class UserComputerGameFacade {

  private final UserContext userContext;
  private final ComputerGameService computerGameService;
  private final UserService userService;

  public UserComputerGameFacade(
      UserContext userContext, ComputerGameService computerGameService, UserService userService) {
    this.userContext = userContext;
    this.computerGameService = computerGameService;
    this.userService = userService;
  }

  public void deleteComputerGameByIdFromSystem(int id) {
    computerGameService.deleteById(id);
  }

  public void removeComputerGameFromUsersCollection(int id) {
    ComputerGame computerGame = computerGameService.getComputerGameById(id);
    User user = userContext.getCurrentUser();
    computerGame.getUsers().removeIf(user1 -> user1.getId().equals(user.getId()));
    computerGameService.save(computerGame);
  }

  public ComputerGame addComputerGameToUsersCollection(int id) {
      ComputerGame computerGame = computerGameService.getComputerGameById(id);
      User user = userContext.getCurrentUser();
      computerGame.getUsers().add(user);
      return computerGameService.save(computerGame);
  }

  public List<ComputerGame> getAllUsersComputerGames() {
    return computerGameService.getAllLoggedUsersComputerGames();
  }

  public void deleteUserById(int id) {
    if (userService.checkRole(userContext, RoleName.ROLE_ADMIN) || userContext.getCurrentUser().getId() == id) {
      User user = userService.findUserById(id);
      List<ComputerGame> computerGames = getAllUsersComputerGames();
      computerGames.forEach(computerGame -> {
        computerGame.getUsers().remove(user);
        computerGameService.save(computerGame);
      });
      userService.deleteUserById(id);
    } else throw new ForbiddenException("You don't have permissions to do that");
  }
}
