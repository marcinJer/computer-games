# Computer Games store
CRUD application for storing information about computer games.

Requirements:
Java 8+
Maven

Build & test:
`mvn package`

Run:
`java -jar target/ComputerGames-0.0.1-SNAPSHOT.jar`

Basic auth:
username: `user`
password: `user`

Usage via REST API:

Add new game
`POST http://localhost:8080/games`
Body JSON:
```
{
  "gameName": "Tekken",
  "gameType": "Action",
  "allowedAge": 15,
  "manufacturer": "CDP"
}
```

Get basic list of games
`GET http://localhost:8080/games`

Get details of game by ID
`GET http://localhost:8080/games/1`

Delete game by ID
`DELETE http://localhost:8080/games/1`

Update game by ID
`PUT http://localhost:8080/games/1`
Body JSON:
```
{
  "gameName": "Tekken",
  "gameType": "Action",
  "allowedAge": 18,
  "manufacturer": "CDP"
}
```
