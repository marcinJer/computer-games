package com.marcin.jer.computerGames.controllers;

import com.marcin.jer.computerGames.entities.ComputerGame;
import com.marcin.jer.computerGames.entities.User;
import com.marcin.jer.computerGames.facades.UserComputerGameFacade;
import com.marcin.jer.computerGames.security.UserContext;
import com.marcin.jer.computerGames.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

  private final UserService userService;
  private final UserComputerGameFacade userComputerGameFacade;

  public UserController(
      UserService userService,
      UserComputerGameFacade userComputerGameFacade) {
    this.userService = userService;
    this.userComputerGameFacade = userComputerGameFacade;
  }

  @GetMapping("/users")
  public List<User> findAllUsers() {
    return userService.findAllUsers();
  }

  @GetMapping("/users/{id}")
  public User findUserById(@PathVariable int id) {
    return userService.findUserById(id);
  }

  @DeleteMapping("users/{id}")
  public ResponseEntity deleteUserById(@PathVariable int id) {
    userComputerGameFacade.deleteUserById(id);
    return new ResponseEntity(HttpStatus.OK);
  }

  @PutMapping("/users/edit")
  public ResponseEntity updateUser(@RequestBody User user) {
    UserContext userContext = new UserContext();
    user.setId(userContext.getCurrentUser().getId());
    userService.saveUser(user);
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping("/users/computerGames")
  public List<ComputerGame> findAllLoggedUsersComputerGames() {
    return userComputerGameFacade.findAllUsersComputerGames();
  }

  @PutMapping("/users/computerGames/{id}")
  public ResponseEntity removeComputerGameFromCollection(@PathVariable int id) {
    userComputerGameFacade.removeComputerGameFromUsersCollection(id);
    return new ResponseEntity(HttpStatus.OK);
  }
}
