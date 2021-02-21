package se.iths.demo.services;

import se.iths.demo.dtos.RatingDto;
import se.iths.demo.entities.Rating;

import java.util.List;
import java.util.Optional;

public interface Service {

    //get one
    Optional<RatingDto> getOne(Long id);

    // get all
    List<RatingDto> getAll();

    //post
    RatingDto postRating(RatingDto rating);

    //delete
    void deleteRating(Long id);

    //patch
    RatingDto updateRating(Long id, RatingDto ratingdto);

    //put
    RatingDto replaceRating(Long id, RatingDto ratingDto);
}
