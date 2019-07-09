package pl.marcin.jer.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marcin.jer.entities.ComputerGame;
import pl.marcin.jer.entities.Review;
import pl.marcin.jer.exceptions.NotFoundException;
import pl.marcin.jer.services.ComputerGameService;
import pl.marcin.jer.services.ReviewService;

@Component
public class ComputerGameReviewFacade {

    @Autowired
    private ComputerGameService computerGameService;
    @Autowired
    private ReviewService reviewService;

    public void deleteComputerGamesReview(int computerGameId, int reviewId){
       if (checkIfComputerGameContainsReview(computerGameId, reviewId)){
           reviewService.deleteReviewById(reviewId);
       }else throw new IllegalArgumentException("You are trying to delete review that does not belong to this computer game!");
    }

    private Boolean checkIfComputerGameContainsReview(int computerGameId, int reviewId){
        ComputerGame computerGame = computerGameService.findComputerGameById(computerGameId);
        Review review = computerGame.getReviews().stream()
                .filter(review1 -> review1.getId().equals(reviewId)).findFirst().orElseThrow(() -> new NotFoundException("Review not found"));

        return computerGame.getReviews().contains(review);
    }
}
