package com.marcin.jer.computerGames.facades;

import com.marcin.jer.computerGames.entities.User;
import com.marcin.jer.computerGames.enums.RoleName;
import com.marcin.jer.computerGames.services.RoleService;
import com.marcin.jer.computerGames.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collections;

@Component
@Transactional
public class UserRoleFacade {

  private final UserService userService;
  private final RoleService roleService;
  private final PasswordEncoder encoder;

  public UserRoleFacade(UserService userService, RoleService roleService, PasswordEncoder encoder) {
    this.userService = userService;
    this.roleService = roleService;
    this.encoder = encoder;
  }

  public User saveAsUser(User user) {
    user.setRoles(Collections.singletonList(roleService.getWantedRole(RoleName.ROLE_USER)));
    user.setPassword(encoder.encode(user.getPassword()));
    return userService.save(user);
  }
}
