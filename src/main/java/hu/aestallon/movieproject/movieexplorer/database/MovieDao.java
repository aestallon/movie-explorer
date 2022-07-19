package hu.aestallon.movieproject.movieexplorer.database;

import hu.aestallon.movieproject.movieexplorer.movie.Movie;

import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MovieDao implements Dao<Movie> {

    @Override
    public List<Movie> selectAll() {
        final String sql = """
                SELECT  *
                FROM    APP.FILM""";
        try (Connection conn = Database.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Movie> movies = new ArrayList<>();
            while (rs.next()) {
                Movie movie = getMovieFromResultSet(rs);
                movies.add(movie);
            }
            return movies;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Collections.emptyList();
        }
    }

    private Movie getMovieFromResultSet(ResultSet rs) throws SQLException {
        Movie movie = new Movie();
        movie.setId(rs.getInt("FILM_ID"));
        movie.setPosterLink(rs.getString("POSTER_LINK"));
        movie.setTitle(rs.getString("TITLE"));
        movie.setReleaseYear(Year.of(rs.getInt("RELEASE_YEAR")));
        movie.setCertificate(rs.getString("CERT"));
        movie.setRuntime(rs.getInt("RUNTIME"));
        movie.setGenre(rs.getString("GENRES"));
        movie.setImdbRating(rs.getDouble("IMDB_RATING"));
        movie.setOverview(rs.getString("OVERVIEW"));
        movie.setMetaScore(rs.getInt("METASCORE"));
        movie.setDirector(rs.getString("DIRECTOR"));
        movie.setStar1(rs.getString("STAR1"));
        movie.setStar2(rs.getString("STAR2"));
        movie.setStar3(rs.getString("STAR3"));
        movie.setStar4(rs.getString("STAR4"));
        movie.setVotecount(rs.getInt("VOTECOUNT"));
        movie.setGrossRevenue(rs.getInt("GROSS_REVENUE"));
        return movie;
    }

    @Override
    public Optional<Movie> select(Integer id) {
        final String sql = """
                SELECT  *
                FROM    APP.FILM f
                WHERE   f.film_id = ?""";
        try (Connection conn = Database.connect()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(getMovieFromResultSet(rs));
            } else {
                return Optional.empty();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Optional.empty();
        }
    }

    public List<String> selectTitles() {
        final String sql = """
                SELECT  f.title AS "TITLE"
                FROM    APP.FILM f""";
        try (Connection conn = Database.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<String> titles = new ArrayList<>();

            while (rs.next()) {
                String title = rs.getString(1);
                titles.add(title);
            }

            return titles;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public int delete(Movie movie) {
        final String sql = """
                DELETE
                FROM    APP.FILM f
                WHERE   f.film_id = ?""";

        try (Connection conn = Database.connect()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movie.getId());
            return stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Movie movie) {
        final String sql = """
                UPDATE
                    APP.FILM f
                SET
                    f.poster_link   = ?,
                    f.title         = ?,
                    f.release_year  = ?,
                    f.cert          = ?,
                    f.runtime       = ?,
                    f.genres        = ?,
                    f.imdb_rating   = ?,
                    f.overview      = ?,
                    f.metascore     = ?,
                    f.director      = ?,
                    f.star1         = ?,
                    f.star2         = ?,
                    f.star3         = ?,
                    f.star4         = ?,
                    f.votecount     = ?,
                    f.gross_revenue = ?
                WHERE
                    f.id = ?""";
        return Database.executeDataManipulation(
                sql,
                movie.getPosterLink(),
                movie.getTitle(),
                movie.getReleaseYear().getValue(),
                movie.getCertificate(),
                movie.getRuntime(),
                movie.getGenre(),
                movie.getImdbRating(),
                movie.getOverview(),
                movie.getMetaScore(),
                movie.getDirector(),
                movie.getStar1(),
                movie.getStar2(),
                movie.getStar3(),
                movie.getStar4(),
                movie.getVotecount(),
                movie.getGrossRevenue(),
                movie.getId()
        );
    }

    @Override
    public int insert(Movie movie) {
        final String sql = """
                INSERT INTO
                FILM(
                    film_id,    poster_link,
                    title,      release_year,
                    cert,       runtime,
                    genres,     imdb_rating,
                    overview,   metascore,
                    director,   star1,
                    star2,      star3,
                    star4,      votecount,
                    gross_revenue
                )
                VALUES (
                    NEXT VALUE FOR FILM_ID,
                    ?, ?, ?, ?, ?, ?, ?, ?,
                    ?, ?, ?, ?, ?, ?, ?, ?
                )""";
        return Database.executeDataManipulation(
                sql,
                movie.getPosterLink(),
                movie.getTitle(),
                movie.getReleaseYear().getValue(),
                movie.getCertificate(),
                movie.getRuntime(),
                movie.getGenre(),
                movie.getImdbRating(),
                movie.getOverview(),
                movie.getMetaScore(),
                movie.getDirector(),
                movie.getStar1(),
                movie.getStar2(),
                movie.getStar3(),
                movie.getStar4(),
                movie.getVotecount(),
                movie.getGrossRevenue()
        );
    }
}
