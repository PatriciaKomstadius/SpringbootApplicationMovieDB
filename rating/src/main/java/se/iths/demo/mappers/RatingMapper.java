package se.iths.demo.mappers;

import org.springframework.stereotype.Component;
import se.iths.demo.dtos.RatingDto;
import se.iths.demo.entities.Rating;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RatingMapper {

    public RatingMapper() {
    }

    public Optional<RatingDto> mapp(Optional<Rating> optionalRating) {
        if (optionalRating.isEmpty())
            return Optional.empty();
        return Optional.of(mapp(optionalRating.get()));
    }

    //från RatingDto objekt -> Ratingobjekt
    public RatingDto mapp(Rating rating) {
        return new RatingDto(rating.getId(), rating.getRating());
    }

    public Rating mapp(RatingDto ratingDto) {
        return new Rating(ratingDto.getId(), ratingDto.getRating());
    }

    public List<RatingDto> mapp(List<Rating> all) {
        return all
                .stream()
                .map(this::mapp)
                .collect(Collectors.toList());
    }

}
