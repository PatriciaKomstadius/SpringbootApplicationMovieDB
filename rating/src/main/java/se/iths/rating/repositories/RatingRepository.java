package se.iths.rating.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.rating.entities.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {


}
