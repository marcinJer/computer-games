package com.marcin.jer.computerGames.security.JwtSecurity;

import com.marcin.jer.computerGames.entities.Role;
import com.marcin.jer.computerGames.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;
import java.util.*;

/**
 * UserPrinciple is not used directly by Spring Security for security purposes. It simply stores
 * user information which is later encapsulated into Authentication objects. This allows
 * non-security related user information (such as email addresses, telephone numbers etc) to be
 * stored.
 */
@Transactional
public class UserPrinciple implements UserDetails {
  private User user;

  public UserPrinciple(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return buildUserAuthority(user.getRoles());
  }

  private List<GrantedAuthority> buildUserAuthority(Collection<Role> userRoles) {
    Set<GrantedAuthority> setAuths = new HashSet<>();

    for (Role userRole : userRoles)
      setAuths.add(new SimpleGrantedAuthority(userRole.getName().name()));

    return new ArrayList<>(setAuths);
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
