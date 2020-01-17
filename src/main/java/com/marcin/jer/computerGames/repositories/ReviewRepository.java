package pl.marcin.jer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.marcin.jer.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
