package hu.aestallon.movieproject.dbuploader;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DbUploadScript {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Script starting...");

        List<MovieDto> movies = new CsvToBeanBuilder<MovieDto>(new FileReader("src/main/resources/imdb_top_1000.csv"))
                .withType(MovieDto.class).withSeparator(',').withQuoteChar('"').build().parse();
        System.out.println("Movies loaded to beans...");

        movies.forEach(DbUploadScript::insertMovie);
        System.out.println("Movies uploaded to database!");
    }

    private static Connection connect() throws SQLException {
        final String dbUrl = "jdbc:derby://localhost:1527/MOVIE";
        return DriverManager.getConnection(
                dbUrl,
                "app",
                "derby"
        );
    }

    private static void insertMovie(MovieDto movie) {
        final String sql = """
                INSERT INTO FILM(
                    FILM_ID, POSTER_LINK, TITLE, RELEASE_YEAR, CERT,
                    RUNTIME, GENRES, IMDB_RATING, OVERVIEW, METASCORE,
                    DIRECTOR, STAR1, STAR2, STAR3, STAR4,
                    VOTECOUNT, GROSS_REVENUE)
                VALUES (
                    NEXT VALUE FOR FILM_ID, ?, ?, ?, ?,
                    ?, ?, ?, ?, ?,
                    ?, ?, ?, ?, ?,
                    ?, ?
                )""";
        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, movie.getPosterLink());
            stmt.setString(2, movie.getTitle());
            stmt.setInt(3, movie.getReleaseYear());
            stmt.setString(4, movie.getCertificate());
            stmt.setInt(5, movie.getRuntime());
            stmt.setString(6, movie.getGenre());
            stmt.setDouble(7, movie.getImdbRating());
            stmt.setString(8, movie.getOverview());
            stmt.setInt(9, (movie.getMetaScore() == null) ? 0 : movie.getMetaScore());
            stmt.setString(10, movie.getDirector());
            stmt.setString(11, movie.getStar1());
            stmt.setString(12, movie.getStar2());
            stmt.setString(13, movie.getStar3());
            stmt.setString(14, movie.getStar4());
            stmt.setInt(15, (movie.getVotecount() == null) ? 0 : movie.getVotecount());
            stmt.setInt(16, (movie.getGrossRevenue() == null) ? 0 : movie.getGrossRevenue());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
