package com.marcin.jer.computerGames.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.marcin.jer.computerGames.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
