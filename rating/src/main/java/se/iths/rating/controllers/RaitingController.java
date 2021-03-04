package se.iths.rating.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.rating.dtos.RatingDto;
import se.iths.rating.services.Service;

import java.util.List;

@RestController
public class RaitingController {

    private Service service;

    @Autowired
    public RaitingController(Service service) {
        this.service = service;
    }


    @GetMapping("/ratings/{id}")
    public RatingDto one(@PathVariable Long id) {
        return service.getOne(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Id " + id + " not found."));
    }

    @GetMapping("/ratings")
    public List<RatingDto> all() {
        return service.getAll();
    }

    @PostMapping("/ratings")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingDto create(@RequestBody RatingDto rating) {
        return service.postRating(rating);
    }

    @PutMapping("/ratings/{id}")
    public RatingDto replace(@RequestBody RatingDto ratingDto, @PathVariable Long id) {
        return service.replaceRating(id, ratingDto);
    }

    @PatchMapping("/ratings/{id}")
    public RatingDto update(@RequestBody RatingDto ratingDto, @PathVariable Long id) {
        return service.updateRating(id, ratingDto);
    }

    @DeleteMapping("/ratings/delete/{id}")
    public void deleteRating(@PathVariable Long id) {
        service.deleteRating(id);
    }
}
