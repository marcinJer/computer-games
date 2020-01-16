package com.marcin.jer.computerGames.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  @Size(min = 1, max = 15)
  private String username;

  @NotNull
  @Size(min = 1, max = 15)
  private String firstname;

  @NotNull
  @Size(min = 1, max = 15)
  private String lastname;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotNull
  @Size(min = 1)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users", cascade = CascadeType.ALL)
  private Set<ComputerGame> computerGames = new HashSet<>();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_role",
      joinColumns = {@JoinColumn(name = "users_id")},
      inverseJoinColumns = {@JoinColumn(name = "roles_id")})
  private Collection<Role> roles;

  public User() {}

  public User(
      @NotNull @Size(min = 1, max = 15) String username,
      @NotNull @Size(min = 1, max = 15) String firstname,
      @NotNull @Size(min = 1, max = 15) String lastname,
      @NotBlank @Size(max = 50) @Email String email,
      @NotNull @Size(min = 1, max = 15) String password,
      Set<ComputerGame> computerGames,
      Collection<Role> roles) {
    this.username = username;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
    this.computerGames = computerGames;
    this.roles = roles;
  }

  public User(
      @NotNull @Size(min = 1, max = 15) String username,
      @NotNull @Size(min = 1, max = 15) String firstname,
      @NotNull @Size(min = 1, max = 15) String lastname,
      @NotBlank @Size(max = 50) @Email String email,
      @NotNull @Size(min = 1, max = 15) String password) {
    this.username = username;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
  }


  public User(
      @NotNull @Size(min = 1, max = 15) String username,
      @NotNull @Size(min = 1, max = 15) String firstname,
      @NotNull @Size(min = 1, max = 15) String lastname,
      @NotNull @Size(min = 1, max = 15) String password) {
    this.username = username;
    this.firstname = firstname;
    this.lastname = lastname;
    this.password = password;
  }

  @JsonIgnore
  public Collection<Role> getRoles() {
    return roles;
  }

  public void setRoles(Collection<Role> roles) {
    this.roles = roles;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<ComputerGame> getComputerGames() {
    return computerGames;
  }

  public void setComputerGames(Set<ComputerGame> computerGames) {
    this.computerGames = computerGames;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
