package pl.marcin.jer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.jer.repositories.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void deleteReviewById(int id){
        reviewRepository.deleteById(id);
    }
}
