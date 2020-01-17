package com.marcin.jer.computerGames;

import com.marcin.jer.computerGames.entities.ComputerGame;
import com.marcin.jer.computerGames.entities.User;
import com.marcin.jer.computerGames.enums.TypesOfGames;
import com.marcin.jer.computerGames.facades.UserRoleFacade;
import com.marcin.jer.computerGames.services.ComputerGameService;
import com.marcin.jer.computerGames.services.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAdminControllerTestJwt {

  @Autowired private TestRestTemplate testRestTemplate;
  @Autowired private UserRoleFacade userRoleFacade;
  @Autowired private UserService userService;
  @Autowired private ComputerGameService computerGameService;
  @LocalServerPort private int port;
  private String baseUrl;
  private String adminToken;
  private User user;
  private ComputerGame computerGame;

  @Before
  public void setup() throws URISyntaxException, JSONException {
    baseUrl = "http://localhost:" + port + "";
    deleteTestUser();
    createUser();
    deleteTestComputerGame();
    createComputerGame();
    adminSignIn();
  }

  @Test
  public void testAdminSignIn() throws URISyntaxException, JSONException {
    URI uri = new URI(baseUrl + "/users/signin");

    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");

    User user = new User("root", "root", "root", "root@root.com", "root");

    HttpEntity<User> request = new HttpEntity<>(user, headers);

    ResponseEntity<String> result =
        testRestTemplate.exchange(uri, HttpMethod.POST, request, String.class);

    JSONObject jsonObject = new JSONObject(result.getBody());

    adminToken = jsonObject.getString("accessToken");

    assertNotNull(result.getBody());
    assertTrue(result.hasBody());
  }

  @Test
  public void testGetUserById() throws URISyntaxException {
    int id = user.getId();
    URI uri = new URI(baseUrl + "/users/" + id);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + adminToken);
    ResponseEntity<User> result =
        testRestTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), User.class);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertTrue(result.hasBody());
  }

  @Test
  public void testGetAllUsers() throws URISyntaxException {
    URI uri = new URI(baseUrl + "/users");

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + adminToken);
    ResponseEntity<List<User>> result =
        testRestTemplate.exchange(
            uri,
            HttpMethod.GET,
            new HttpEntity<>(headers),
            new ParameterizedTypeReference<List<User>>() {});

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertTrue(result.hasBody());
  }

  @Test
  public void testUpdateComputerGame() throws URISyntaxException {
    if (computerGame != null) {
      computerGame.setGameName("Tomb Raider");
      computerGame.setAllowedAge(15);

      URI uri = new URI(baseUrl + "/games/" + computerGame.getId());

      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "Bearer " + adminToken);
      ResponseEntity<ComputerGame> result =
          testRestTemplate.exchange(
              uri, HttpMethod.PUT, new HttpEntity<>(computerGame, headers), ComputerGame.class);

      assertEquals(HttpStatus.OK, result.getStatusCode());
    }
  }

  @Test
  public void testShouldNotAddComputerGameWhichExists() throws URISyntaxException {
    URI uri = new URI(baseUrl + "/games");

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + adminToken);
    ResponseEntity<ComputerGame> result =
        testRestTemplate.exchange(
            uri, HttpMethod.POST, new HttpEntity<>(computerGame, headers), ComputerGame.class);

    assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
  }

  @Test
  public void testAddComputerGame() throws URISyntaxException {
    URI uri = new URI(baseUrl + "/games");

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + adminToken);

    ComputerGame computerGame2 = new ComputerGame("Goat Simulator", TypesOfGames.Simulation, 5);

    ResponseEntity<ComputerGame> result =
        testRestTemplate.exchange(
            uri, HttpMethod.POST, new HttpEntity<>(computerGame2, headers), ComputerGame.class);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());

    ComputerGame cg = computerGameService.getComputerGameByName("Goat Simulator");
    computerGameService.deleteById(cg.getId());
  }

  @Test
  public void testDeleteUserById() throws URISyntaxException {
    int id = user.getId();
    URI uri = new URI(baseUrl + "/users/" + id);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + adminToken);
    headers.set("Content-Type", "application/json");

    ResponseEntity<User> result =
        testRestTemplate.exchange(uri, HttpMethod.DELETE, new HttpEntity<>(headers), User.class);

    Assert.assertEquals(HttpStatus.OK, HttpStatus.valueOf(result.getStatusCodeValue()));
    user = null;
  }

  @Test
  public void testDeleteComputerGame() throws URISyntaxException {
    URI uri = new URI(baseUrl + "/games/" + computerGame.getId());

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + adminToken);
    headers.set("Content-Type", "application/json");

    ResponseEntity<String> result =
        testRestTemplate.exchange(uri, HttpMethod.DELETE, new HttpEntity<>(headers), String.class);

    Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    computerGame = null;
  }

  @After
  public void deleteUserAfterTests() {
    deleteTestUser();
    deleteTestComputerGame();
  }

  private void deleteTestUser() {
    if (user != null) {
      userService.deleteUserById(user.getId());
    }
  }

  private void createUser() {
    user = new User("test", "test", "test", "user@user.com", "test");
    userRoleFacade.saveAsUser(user);
  }

  private void adminSignIn() throws URISyntaxException, JSONException {
    testAdminSignIn();
  }

  private void createComputerGame() {
    computerGame = new ComputerGame("Cod MW3", TypesOfGames.FPS, 16);
    computerGameService.save(computerGame);
  }

  private void deleteTestComputerGame() {
    if (computerGame != null) {
      computerGameService.deleteById(computerGame.getId());
    }
  }
}
