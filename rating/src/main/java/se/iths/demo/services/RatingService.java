package se.iths.demo.services;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import se.iths.demo.dtos.RatingDto;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import se.iths.demo.entities.Rating;
import se.iths.demo.mappers.RatingMapper;
import se.iths.demo.repositories.RatingRepository;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Service
public class RatingService implements se.iths.demo.services.Service {


    private RatingRepository ratingRepository;
    private RatingMapper ratingMapper;

    public RatingService(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }

    //GET ONE
    @Override
    public Optional<RatingDto> getOne(Long id) {
        return ratingMapper.mapp(ratingRepository.findById(id));
    }

    //GET ALL
    @Override
    public List<RatingDto> getAll() {
        return ratingMapper.mapp(ratingRepository.findAll());
    }

    //POST
    @Override
    public RatingDto postRating(RatingDto rating) {
        if (rating.getRating() <= 0 || rating.getRating() > 10)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Enter a rating between 0 and 10");
        return ratingMapper.mapp(ratingRepository.save(ratingMapper.mapp(rating)));
    }

    //DELETE
    @Override
    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }


    //UPDATE PATCH
    @Override
    public RatingDto updateRating(Long id, RatingDto ratingdto) {
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {

            Rating updateRating = rating.get();

            if (ratingdto.getRating() != 0)
                updateRating.setRating(ratingdto.getRating());

            return ratingMapper.mapp(ratingRepository.save(updateRating));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + "not found.");
        }
    }


    //REPLACE PUT
    @Override
    public RatingDto replaceRating(Long id, RatingDto ratingDto) {
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {
            Rating newRating = rating.get();
            newRating.setRating(ratingDto.getRating());
            return ratingMapper.mapp(ratingRepository.save(newRating));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id " + id + "not found.");
        }
    }

}

