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

import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserControllerTestJwt {
  @LocalServerPort private int port;

  @Autowired private TestRestTemplate testRestTemplate;
  @Autowired private UserRoleFacade userRoleFacade;
  @Autowired private UserService userService;
  @Autowired private ComputerGameService computerGameService;

  private String baseUrl;
  private String userToken;
  private User user;
  private ComputerGame computerGame;

  @Before
  public void setup() throws URISyntaxException, JSONException {
    baseUrl = "http://localhost:" + port + "";
    deleteTestUser();
    createUser();
    deleteTestComputerGame();
    createComputerGame();
    userSignIn();
  }

  @Test
  public void testUserSignIn() throws URISyntaxException, JSONException {
    URI uri = new URI(baseUrl + "/users/signin");

    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");

    user.setPassword("test");

    HttpEntity<User> request = new HttpEntity<>(user, headers);

    ResponseEntity<String> result =
        testRestTemplate.exchange(uri, HttpMethod.POST, request, String.class);

    JSONObject jsonObject = new JSONObject(result.getBody());

    userToken = jsonObject.getString("accessToken");

    assertNotNull(result.getBody());
    assertTrue(result.hasBody());
  }

  @Test
  public void testSignUp() throws URISyntaxException {
    URI uri = new URI(baseUrl + "/users/signup");

    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");

    HttpEntity<User> request = new HttpEntity<>(user, headers);

    ResponseEntity<User> result =
        testRestTemplate.exchange(uri, HttpMethod.POST, request, User.class);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
  }

  @Test
  public void testUserTryToUpdateComputerGame_ShouldBeForbidden() throws URISyntaxException {
    if (computerGame != null) {
      computerGame.setGameName("Tomb Raider");
      computerGame.setAllowedAge(15);

      URI uri = new URI(baseUrl + "/games/" + computerGame.getId());

      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "Bearer " + userToken);
      ResponseEntity<ComputerGame> result =
              testRestTemplate.exchange(
                      uri, HttpMethod.PUT, new HttpEntity<>(computerGame, headers), ComputerGame.class);

      assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }
  }

  @Test
  public void testIfUserCanDeleteUserById() throws URISyntaxException {
    int id = user.getId();
    URI uri = new URI(baseUrl + "/users/" + id);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + userToken);
    headers.set("Content-Type", "application/json");

    ResponseEntity<User> result =
        testRestTemplate.exchange(uri, HttpMethod.DELETE, new HttpEntity<>(headers), User.class);

    Assert.assertEquals(HttpStatus.FORBIDDEN, HttpStatus.valueOf(result.getStatusCodeValue()));
  }

  @Test
  public void testShouldNotGetUserWhenAdminIsNotLogged() throws URISyntaxException {
    int id = user.getId();
    URI uri = new URI(baseUrl + "/users/" + id);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + userToken);
    ResponseEntity<User> result =
        testRestTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), User.class);

    assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    assertNotNull(result.getBody());
    assertTrue(result.hasBody());
  }

  @Test
  public void testShouldReturnUsersComputerGamesCollection()
      throws URISyntaxException {
    URI uri = new URI(baseUrl + "/users/computerGames");

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + userToken);
    ResponseEntity<List<ComputerGame>> result =
        testRestTemplate.exchange(
            uri,
            HttpMethod.GET,
            new HttpEntity<>(headers),
            new ParameterizedTypeReference<List<ComputerGame>>() {});

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertTrue(result.hasBody());
  }

  @Test
  public void testUpdateUser() throws URISyntaxException {
    if (user != null) {
      user.setFirstname("John");
      user.setLastname("Kowalsky");

      URI uri = new URI(baseUrl + "/users/edit");

      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "Bearer " + userToken);
      ResponseEntity<User> result =
          testRestTemplate.exchange(
              uri, HttpMethod.PUT, new HttpEntity<>(user, headers), User.class);

      assertEquals(HttpStatus.OK, result.getStatusCode());
    }
  }

  @Test
  public void testRemoveComputerGameFromCollection() throws URISyntaxException {
    int computerGameId = computerGame.getId();
    URI uri = new URI(baseUrl + "/users/computerGames/" + computerGameId);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + userToken);
    ResponseEntity<String> result =
        testRestTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), String.class);

    assertEquals(HttpStatus.OK, result.getStatusCode());
  }

  @Test
  public void testShouldReturnAllComputerGames() throws URISyntaxException {
    URI uri = new URI(baseUrl + "/games");

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + userToken);
    ResponseEntity<List<ComputerGame>> result =
        testRestTemplate.exchange(
            uri,
            HttpMethod.GET,
            new HttpEntity<>(headers),
            new ParameterizedTypeReference<List<ComputerGame>>() {});

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertTrue(result.hasBody());
  }

  @Test
  public void testShouldReturnComputerGamesById() throws URISyntaxException {
    int computerGameId = computerGame.getId();
    URI uri = new URI(baseUrl + "/games/" + computerGameId);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + userToken);
    ResponseEntity<ComputerGame> result =
        testRestTemplate.exchange(
            uri, HttpMethod.GET, new HttpEntity<>(headers), ComputerGame.class);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertTrue(result.hasBody());
  }

  @Test
  public void testShouldAddComputerGameToUsersCollection() throws URISyntaxException {
    int computerGameId = computerGame.getId();
    URI uri = new URI(baseUrl + "/games/" + computerGameId);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + userToken);

    ResponseEntity<String> result =
        testRestTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(headers), String.class);

    assertEquals(HttpStatus.OK, result.getStatusCode());
  }

  @Test
  public void testUserTryToDeleteComputerGame_ShouldBeForbidden() throws URISyntaxException {
    URI uri = new URI(baseUrl + "/games/" + 1);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + userToken);
    headers.set("Content-Type", "application/json");

    ResponseEntity<User> result =
            testRestTemplate.exchange(uri, HttpMethod.DELETE, new HttpEntity<>(headers), User.class);

    Assert.assertEquals(HttpStatus.FORBIDDEN, HttpStatus.valueOf(result.getStatusCodeValue()));
  }

  @Test
  public void testUserTryToAddComputerGame_ShouldBeForbidden() throws URISyntaxException {
    URI uri = new URI(baseUrl + "/games");

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + userToken);

    ComputerGame computerGame2 = new ComputerGame("Goat Simulator", TypesOfGames.Simulation, 5);

    ResponseEntity<ComputerGame> result =
            testRestTemplate.exchange(
                    uri, HttpMethod.POST, new HttpEntity<>(computerGame2, headers), ComputerGame.class);

    assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
  }

  private void userSignIn() throws URISyntaxException, JSONException {
    testUserSignIn();
  }

  private User createUser() {
    user = new User("test", "test", "test", "user@user.com", "test");
    return userRoleFacade.saveAsUser(user);
  }

  private void deleteTestUser() {
    if (user != null) {
      userService.deleteUserById(user.getId());
    }
  }

  private ComputerGame createComputerGame() {
    computerGame = new ComputerGame("Gothic", TypesOfGames.RPG, 12);
    return computerGameService.save(computerGame);
  }

  private void deleteTestComputerGame() {
    if (computerGame != null) {
      computerGameService.deleteById(computerGame.getId());
    }
  }

  @After
  public void deleteUserAfterTests() {
    deleteTestUser();
  }
}
