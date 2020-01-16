package com.marcin.jer.computerGames.security;

import com.marcin.jer.computerGames.entities.User;
import com.marcin.jer.computerGames.exceptions.BadRequest;
import com.marcin.jer.computerGames.security.JwtSecurity.UserPrinciple;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserContext {

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
      return principal.getUser();
    }
    throw new BadRequest("Unauthorized");
  }
}
