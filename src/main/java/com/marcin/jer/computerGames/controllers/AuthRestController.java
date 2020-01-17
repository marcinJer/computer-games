package com.marcin.jer.computerGames.controllers;

import com.marcin.jer.computerGames.entities.User;
import com.marcin.jer.computerGames.facades.UserRoleFacade;
import com.marcin.jer.computerGames.message.requests.LoginForm;
import com.marcin.jer.computerGames.message.response.JwtResponse;
import com.marcin.jer.computerGames.security.JwtSecurity.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class AuthRestController {

  private final AuthenticationManager authenticationManager;
  private final UserRoleFacade userRoleFacade;
  private final JwtProvider jwtProvider;

  public AuthRestController(
      AuthenticationManager authenticationManager,
      UserRoleFacade userRoleFacade,
      JwtProvider jwtProvider) {
    this.authenticationManager = authenticationManager;
    this.userRoleFacade = userRoleFacade;
    this.jwtProvider = jwtProvider;
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginForm) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginForm.getUsername(), loginForm.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtProvider.generateJwtToken(authentication);
    return ResponseEntity.ok(new JwtResponse(jwt));
  }

  @PostMapping("/signup")
  public ResponseEntity addUser(@RequestBody User user) {
    userRoleFacade.saveAsUser(user);
    return new ResponseEntity(HttpStatus.CREATED);
  }
}
