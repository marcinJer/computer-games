CREATE DATABASE computer_games;

CREATE TABLE computer_games  (
  id_computer_game int(11) PRIMARY KEY AUTO_INCREMENT,
  gameName varchar(255),
  gameType varchar(255),
  allowedAge int(16) NOT NULL,
  manufacturer varchar(255)
)
