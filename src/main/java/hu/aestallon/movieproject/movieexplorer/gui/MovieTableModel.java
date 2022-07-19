package hu.aestallon.movieproject.movieexplorer.gui;

import hu.aestallon.movieproject.movieexplorer.database.MovieDao;
import hu.aestallon.movieproject.movieexplorer.movie.Movie;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MovieTableModel extends AbstractTableModel {

    private MovieDao movieDao;
    private List<Movie> storedMovies;
    private List<MovieAttribute> displayedAttributes;

    public MovieTableModel(MovieDao movieDao) {
        this.movieDao = movieDao;

        // When we first start up, we will display
        //  1) title
        //  2) release year
        //  3) director
        displayedAttributes = new ArrayList<>();
        displayedAttributes.add(MovieAttribute.TITLE);
        displayedAttributes.add(MovieAttribute.RELEASE_YEAR);
        displayedAttributes.add(MovieAttribute.DIRECTOR);

        // and we'll load every movie:
        storedMovies = movieDao.selectAll();

    }
    @Override
    public int getRowCount() {
        return storedMovies.size();
    }

    @Override
    public int getColumnCount() {
        return displayedAttributes.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Movie movie = storedMovies.get(rowIndex);
        MovieAttribute attribute = displayedAttributes.get(columnIndex);
        return switch (attribute) {
            case CERT -> movie.getCertificate();
            case TITLE -> movie.getTitle();
            case GENRES -> movie.getGenre();
            case RUNTIME -> movie.getRuntime();
            case DIRECTOR -> movie.getDirector();
            case STAR_1 -> movie.getStar1();
            case STAR_2 -> movie.getStar2();
            case STAR_3 -> movie.getStar3();
            case STAR_4 -> movie.getStar4();
            case OVERVIEW -> movie.getOverview();
            case METASCORE -> movie.getMetaScore();
            case VOTECOUNT -> movie.getVotecount();
            case IMDB_RATING -> movie.getImdbRating();
            case RELEASE_YEAR -> movie.getReleaseYear();
            case GROSS_REVENUE -> movie.getGrossRevenue();
        };
    }

    public void setDisplayedAttributes(MovieAttribute... attributes) {
        if (attributes == null || attributes.length == 0) {
            throw new IllegalArgumentException("No attributes provided for table model!");
        }
        displayedAttributes = Arrays.asList(attributes);
        fireTableStructureChanged();
    }

    public void setDisplayedMovies(List<Movie> movies) {
        storedMovies = new ArrayList<>(movies);
        fireTableDataChanged();
    }

    public void sortMoviesBy(Comparator<Movie> movieComparator) {
        storedMovies.sort(movieComparator);
        fireTableDataChanged();
    }

    public void filterMoviesBy(Predicate<Movie> moviePredicate) {
        storedMovies = storedMovies.stream()
                .filter(moviePredicate)
                .collect(Collectors.toCollection(ArrayList::new));
        fireTableStructureChanged();
    }

    public void clearAllFilters() {
        storedMovies = movieDao.selectAll();
        fireTableStructureChanged();
    }

    public Movie getMovieAt(int index) {
        return storedMovies.get(index);
    }
}
