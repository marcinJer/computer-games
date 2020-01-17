package com.marcin.jer.computerGames.services;

import com.marcin.jer.computerGames.repositories.ComputerGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.marcin.jer.computerGames.entities.ComputerGame;
import com.marcin.jer.computerGames.entities.Review;
import com.marcin.jer.computerGames.exceptions.NotFoundException;

import java.util.List;


@Service
public class ComputerGameService {

    @Autowired
    private ComputerGameRepository computerGameRepository;


    public List<ComputerGame> getAllComputerGames(){
        List<ComputerGame> allComputerGames = computerGameRepository.findAll();
        return allComputerGames;
    }

    public ComputerGame findComputerGameById(int id){
        return computerGameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Computer game with id = " + id + " does not exist"));
    }

    public ComputerGame saveComputerGame(ComputerGame computerGame){
        return computerGameRepository.save(computerGame);
    }

    public void deleteComputerGameById(int id){
        computerGameRepository.deleteById(id);
    }

    public Boolean findIfExists(int id){
        return computerGameRepository.existsById(id);
    }

    public Review addReviewToComputerGame(Review review, int computerGameId){
        ComputerGame computerGame = computerGameRepository.findById(computerGameId)
                .orElseThrow(() -> new NotFoundException("Computer game with id = " + computerGameId + " does not exist"));
        review.setComputerGame(computerGame);
        computerGame.getReviews().add(review);
        computerGame.setId(computerGameId);
        computerGameRepository.save(computerGame);
        return review;
    }
}
