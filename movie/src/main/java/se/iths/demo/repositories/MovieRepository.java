package se.iths.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.demo.entities.Movie;

import java.util.List;

//Sköter uppkopplingen mot en databas/repot
//Kopplar repositoryt mot en tabell

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitle(String title);

    List<Movie> findAllByGenre(String genre);

}
