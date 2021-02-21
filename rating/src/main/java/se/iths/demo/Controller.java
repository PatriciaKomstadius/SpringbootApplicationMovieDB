package se.iths.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class Controller {

    private RatingRepository ratingRepository;

    @Autowired
    public Controller(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @PostMapping("/ratings")
    public Rating create (@RequestBody Rating rating){
    return ratingRepository.save(rating);
    }

    @GetMapping("/ratings")
    public List<Rating> all(){
        return ratingRepository.findAll();
    }

    @GetMapping("/ratings/{id}")
    public Rating one(@PathVariable Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Id " + id + " not found."));
    }

    //PATCH

    //PUT

    //DELETE

}
