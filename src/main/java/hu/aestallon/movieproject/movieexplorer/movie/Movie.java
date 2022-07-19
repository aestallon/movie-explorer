package hu.aestallon.movieproject.movieexplorer.movie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Year;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class Movie {
    private Integer id;
    private String posterLink;
    private String title;
    private Year releaseYear;
    private String certificate;
    private Integer runtime;
    private String genre;
    private Double imdbRating;
    private String overview;
    private Integer metaScore;
    private String director;
    private String star1;
    private String star2;
    private String star3;
    private String star4;
    private Integer votecount;
    private Integer grossRevenue;
}
