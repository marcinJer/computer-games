package com.marcin.jer.computerGames.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.marcin.jer.computerGames.entities.ComputerGame;

import java.util.List;
import java.util.Optional;

/**
 * This is an interface for creating computer game object
 */

@Repository
public interface ComputerGameRepository extends JpaRepository<ComputerGame, Integer> {

    @Query(
            value =
                    "select c.* from user u, computer_game c, game_users ucg where u.id = ucg.user_id and c.id = ucg.computer_game_id and u.id= ?1",
            nativeQuery = true)
    List<ComputerGame> findAllUsersComputerGames(Integer userId);

    Optional<ComputerGame> findByGameName(String gameName);
}
