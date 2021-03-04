package se.iths.rating.services;

import se.iths.rating.dtos.RatingDto;

import java.util.List;
import java.util.Optional;

public interface Service {


    Optional<RatingDto> getOne(Long id);

    List<RatingDto> getAll();

    RatingDto postRating(RatingDto rating);

    void deleteRating(Long id);

    RatingDto updateRating(Long id, RatingDto ratingDto);

    RatingDto replaceRating(Long id, RatingDto ratingDto);
}
