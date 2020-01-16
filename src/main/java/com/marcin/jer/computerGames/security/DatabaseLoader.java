package com.marcin.jer.computerGames.security;

import com.marcin.jer.computerGames.entities.ComputerGame;
import com.marcin.jer.computerGames.entities.Role;
import com.marcin.jer.computerGames.entities.User;
import com.marcin.jer.computerGames.enums.RoleName;
import com.marcin.jer.computerGames.enums.TypesOfGames;
import com.marcin.jer.computerGames.exceptions.NotFoundException;
import com.marcin.jer.computerGames.repositories.RoleRepository;
import com.marcin.jer.computerGames.services.ComputerGameService;
import com.marcin.jer.computerGames.services.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class DatabaseLoader implements ApplicationListener<ContextRefreshedEvent> {

  boolean alreadySetup = false;

  private final UserService userService;
  private final RoleRepository roleRepository;
  private final ComputerGameService computerGameService;

  public DatabaseLoader(
      UserService userService,
      RoleRepository roleRepository,
      ComputerGameService computerGameService) {
    this.userService = userService;
    this.roleRepository = roleRepository;
    this.computerGameService = computerGameService;
  }

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    if (alreadySetup) return;

    createRoleIfNotFound(RoleName.ROLE_ADMIN);
    createRoleIfNotFound(RoleName.ROLE_USER);
    createRoleIfNotFound(RoleName.ROLE_MANUFACTURER);

    createComputerGameIfNotFound("Gothic", 12, TypesOfGames.RPG);

    Role userRole =
        roleRepository
            .findByName(RoleName.ROLE_USER)
            .orElseThrow(() -> new NotFoundException("Role not found"));
    Role adminRole =
        roleRepository
            .findByName(RoleName.ROLE_ADMIN)
            .orElseThrow(() -> new NotFoundException("Role not found"));
    Role manufacturerRole =
        roleRepository
            .findByName(RoleName.ROLE_MANUFACTURER)
            .orElseThrow(() -> new NotFoundException("Role not found"));

    createUserIfNotFound(
        "root", "root", "root", "root", "root@root.com", Arrays.asList(userRole, adminRole));

    createUserIfNotFound(
        "user", "user", "user", "user", "user@user.com", Collections.singletonList(userRole));

    createUserIfNotFound(
        "Ubisoft",
        "Montreal",
        "ubi",
        "mon",
        "man@man.com",
        Collections.singletonList(manufacturerRole));

    alreadySetup = true;
  }

  @Transactional
  Role createRoleIfNotFound(RoleName name) {

    Optional<Role> role = roleRepository.findByName(name);
    if (!role.isPresent()) {
      Role role1 = new Role();
      role1.setName(name);
      return roleRepository.save(role1);
    }
    return role.get();
  }

  @Transactional
  void createUserIfNotFound(
      String firstname,
      String lastname,
      String username,
      String password,
      String email,
      List<Role> roles) {

    Optional<User> user = userService.findByUsername(username);

    if (!user.isPresent()) {
      User user1 = new User();

      user1.setFirstname(firstname);
      user1.setLastname(lastname);
      user1.setUsername(username);
      user1.setPassword(password);
      user1.setEmail(email);
      user1.setRoles(roles);

      userService.saveUser(user1);
    }
  }

  @Transactional
  void createComputerGameIfNotFound(String gameName, Integer allowedAge, TypesOfGames typeOfGame) {

    Optional<ComputerGame> computerGameToBeFound =
        computerGameService.findComputerGameByName(gameName);

    if (!computerGameToBeFound.isPresent()) {
      ComputerGame computerGame = new ComputerGame();
      computerGame.setGameName(gameName);
      computerGame.setAllowedAge(allowedAge);
      computerGame.setGameType(typeOfGame);

      computerGameService.save(computerGame);
    }
  }
}
