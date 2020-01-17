package com.marcin.jer.computerGames.services;

import com.marcin.jer.computerGames.entities.User;
import com.marcin.jer.computerGames.enums.RoleName;
import com.marcin.jer.computerGames.exceptions.NotFoundException;
import com.marcin.jer.computerGames.repositories.UserRepository;
import com.marcin.jer.computerGames.security.UserContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  public UserService(UserRepository userRepository, PasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.encoder = encoder;
  }

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public User findUserById(int id) {
    return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
  }

  public Boolean checkRole(UserContext user, RoleName roleName) {
    boolean isAdmin;

    isAdmin =
        user.getCurrentUser().getRoles().stream().anyMatch(role -> role.getName().equals(roleName));

    return isAdmin;
  }

  public void deleteUserById(int id) {
    userRepository.deleteById(id);
  }

  public void saveUser(User user) {
    user.setPassword(encoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  public User save(User user) {
    return userRepository.save(user);
  }
}
