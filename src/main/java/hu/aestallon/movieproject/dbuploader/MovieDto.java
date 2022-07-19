package hu.aestallon.movieproject.dbuploader;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvNumber;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MovieDto {
    @CsvBindByName
    private String posterLink;
    @CsvBindByName
    private String title;
    @CsvBindByName
    @CsvNumber(value = "0000")
    private Integer releaseYear;
    @CsvBindByName
    private String certificate;
    @CsvBindByName
    private Integer runtime;
    @CsvBindByName
    private String genre;
    @CsvBindByName
    private Float imdbRating;
    @CsvBindByName
    private String overview;
    @CsvBindByName
    private Integer metaScore;
    @CsvBindByName
    private String director;
    @CsvBindByName
    private String star1;
    @CsvBindByName
    private String star2;
    @CsvBindByName
    private String star3;
    @CsvBindByName
    private String star4;
    @CsvBindByName
    private Integer votecount;
    @CsvBindByName
    @CsvNumber(value = "#,###")
    private Integer grossRevenue;


}
