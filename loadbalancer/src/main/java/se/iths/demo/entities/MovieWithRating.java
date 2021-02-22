package se.iths.demo.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.xml.MarshallingView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovieWithRating {



    //Kan man ha två resttample & autowired fr samma bean?
    @Autowired
    RestTemplate restTemplate;

    //OK - RETURNERAR EN FILM MED RATING
    public static String getTitleAndRating(Long id, Movie[] movies, Rating[] ratings) {

        for (Movie m : movies) {
            for (Rating r : ratings) {
                if (id.equals(m.getId()) && id.equals(r.getId())) {
                    String movie = "{title:" + m.getTitle() + " rating:" + r.getRating() + "}";
                    return movie;
                }
            }
        }
        return "No rating on this title yet";
    }

    //OK - RETURNERAR LISTA AV FILMER MED RATINGS
    public static List<String> getMoviesAndRatings(Movie[] movies, Rating[] ratings) {
List<String> list = new ArrayList<>();
        String object = "";
        for (Movie m : movies) {
            for (Rating r : ratings) {
                if (m.getId() == r.getId()) {
                    object = m.getTitle() + r.getRating();
                    list.add(object);
                }
            }
        }
        for(String o: list) {
            return list;
        }
        return null;
    }



                   // allofthem.add(m.getTitle() + r.getRating());


                  //  String movie = "{title:" + m.getTitle() + " rating:" + r.getRating() + "}";
                 //   movie.setId(r.getId());
                 //   movie.setTitle(m.getTitle());
                //    rating.setRating(r.getRating());



    /*
    public String getMovies(Movie movie, Rating rating) {
       String t = "";
       double r = 0;
        for (int i = 0; i < 10; i++) {
       if(movie.getId() == rating.getId()) {
         String film =  movie.getTitle() + rating.getRating();
         return film;
       }
    }

    public List<Movie> mapp(Movie[]movies, Rating[]ratings) {
       for(Movie m:movies){
           for (Rating r: ratings) {
               if (m.getId() == r.getId()){
                  String movie = m.getTitle();
                  double rating = r.getRating();
                  String map = m.getTitle() + r.getRating();
               }
           }
       }
       return Arrays.stream(movies).allMatch(ratings).
    }
 */





/*
       public static String getTitlesAndRatings(Long id, Movie[] movies, Rating[] ratings) {
        for (Movie m : movies) {
            for (Rating r : ratings) {
                if (id.equals(m.getId()) && id.equals(r.getId())) {
                    String movie = "{title:" + m.getTitle() + " rating:" + r.getRating() + "}";
                    return movie;
                }
            }
        }
        return "No rating on this title yet";
    }
*/


    /*
    //  STREAM FÖR LISTA AV FILMER M RATINGS?
          //ström av data. varje movie mappas och läggs i en ny lista
    private List<MovieDto> mapp(List<movies> all) {
       return  all
                .stream()//returnerar objekt av typen stream m massa metoder
                .map(this::mapp) //anger namnet på metoden (mapp) som ska appliceras på varje movie-objekt
               // .map(movie -> mapp(movie))//får in ett movie-objekt, mappar det till vår movieDto
                .collect(Collectors.toList()); //lägger allt i en ny lista
    }

    private List<Movie> allTitles(List<Movie>all){
    return all.stream().map(this::allTitles).collect(Collectors.toList());
*/

/*
    static double getTitlesAndRatings(Movie[] movies, Rating[] ratings) {
        Map<String, Double> list = new HashMap<>();
        String titl = "";
        double rate = 0;
        for (Movie m : movies) {
            for (Rating r : ratings) {
                if (m.getId() == r.getId()) {
                    list.put(m.getTitle(), r.getRating());
                }
            }
        }
        for (String i: list.keySet()) {
            return list.get(i);
        }
        return 0;
    }
 */
        /*
    public static double getMovieWithRating(Movie[] movies, Rating[] ratings) {
        for (Movie m : movies) {
            for (Rating r : ratings) {
                if (m.getId()  == r.getId()) {
                    return r.getRating();
                }
            }
        }
        return 0;
    }
    */


}
