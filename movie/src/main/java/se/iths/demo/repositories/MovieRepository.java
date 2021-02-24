package se.iths.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.demo.entities.Movie;

import java.util.List;

//Sköter uppkopplingen mot en databas
//Kopplar repositoryt mot en tabell

@Repository //obs! Behöver ange @repository manuellt, att det är en repositoryklass
public interface MovieRepository extends JpaRepository<Movie, Long> {

    //Hitta titel med urlparametrar
    List<Movie> findByTitle(String title);

    //Skapar en lista därifrån man kan söka på genre urlparameter
    List<Movie> findAllByGenre(String genre);

    //Söker upp film med id urlparameter OBS ANVÄNDS EJ
  //  List<Movie> findById(long id);


}
