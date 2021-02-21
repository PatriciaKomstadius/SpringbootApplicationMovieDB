package se.iths.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.demo.entities.Rating;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

//   List<Rating> getAll();

}
