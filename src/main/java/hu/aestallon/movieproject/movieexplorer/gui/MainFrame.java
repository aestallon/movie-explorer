package hu.aestallon.movieproject.movieexplorer.gui;

import hu.aestallon.movieproject.movieexplorer.database.MovieDao;
import hu.aestallon.movieproject.movieexplorer.movie.Movie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private final MovieTableModel movieTableModel;
    private final JTable movieTable;

    public MainFrame(MovieDao movieDao) {
        this.movieTableModel = new MovieTableModel(movieDao);
        this.movieTable = new JTable(movieTableModel);
        TablePanel tablePanel = new TablePanel();
        ControlPanel controlPanel = new ControlPanel();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Movie Explorer");
        setSize(700, 400);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);

        this.add(tablePanel);
        this.add(controlPanel);

        setVisible(true);
    }

    // The JPanel containing the table displaying movie data.
    private class TablePanel extends JPanel {

        private TablePanel() {
            this.setSize(600, 400);
            this.setPreferredSize(new Dimension(600, 400));
            this.setBackground(new Color(173, 199, 166));
            this.setLocation(0, 0);

            movieTable.setPreferredSize(this.getPreferredSize());
            movieTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            movieTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    int selection = movieTable.getSelectedRow();
                    Movie movie = movieTableModel.getMovieAt(selection);
                    new MovieInfoDialog(movie);
                }
            });
            this.add(movieTable);
        }
    }

    // The dialog window which appears when a movie is selected from the
    // table.
    private class MovieInfoDialog extends JFrame {

        private MovieInfoDialog(Movie movie) {
            // TODO: Format data elements.
            String movieText = String.format("""
                    <html>
                        <h1>%s</h1>
                        <ul>
                            <li>Released: %d</li>
                            <li>Directed by: %s</li>
                            <li>Starring: %s, %s, %s, %s...</li>
                            <li>Genre(s): %s</li>
                            <li>Runtime: %d minutes</li>
                            <li>Rating: %s</li>
                        </ul>
                                        
                        <p>%s</p>
                                        
                        <ul>
                            <li>Gross revenue: %d USD</li>
                            <li>IMDB rating: %f}</li>
                            <li>Metascore: %d</li>
                            <li>No. of votes: %d</li>
                        </ul>
                                        
                        <p>Poster can be found <a href="%s">here</a>.</p>
                    </html>
                    """, movie.getTitle(), movie.getReleaseYear().getValue(), movie.getDirector(),
                    movie.getStar1(), movie.getStar2(), movie.getStar3(), movie.getStar4(),
                    movie.getGenre(), movie.getRuntime(), movie.getCertificate(),
                    movie.getOverview(), movie.getGrossRevenue(), movie.getImdbRating(),
                    movie.getMetaScore(), movie.getVotecount(), movie.getPosterLink()
            );
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setTitle(movie.getTitle());
            JLabel label = new JLabel(movieText);
            this.add(label);
            this.pack();
            setLocationRelativeTo(MainFrame.this);
            this.setVisible(true);
        }
    }

    // The JPanel containing the control buttons (Sort, Filter, CRUD)
    private class ControlPanel extends JPanel {

        private ControlPanel() {
            this.setSize(100, 400);
            this.setBackground(new Color(234, 100, 81));
            this.setLocation(600, 0);

            // Show/hide attributes
            JButton showAndHideBtn = new JButton("Display");
            showAndHideBtn.addActionListener(e -> new ShowAndHideDialog());
            this.add(showAndHideBtn);

            // Filter movies
            // TODO: Add filter button, filter dialog window, with user input validation.

            // Add button
            // Delete button
            // Update button

        }
    }

    // JFrame containing checkboxes to let the user change which
    // columns should appear.
    private class ShowAndHideDialog extends JFrame {

        private ShowAndHideDialog() {
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setTitle("Displayed Attibutes");

            this.setLayout(new GridLayout(5, 2));
            JCheckBox directorCB = new JCheckBox(
                    MovieAttribute.DIRECTOR.toString(),
                    movieTableModel.isDisplayed(MovieAttribute.DIRECTOR)
            );
            this.add(directorCB);

            JCheckBox titleCB = new JCheckBox(
                    MovieAttribute.TITLE.toString(),
                    movieTableModel.isDisplayed(MovieAttribute.TITLE)
            );
            this.add(titleCB);

            JCheckBox releaseYearCB = new JCheckBox(
                    MovieAttribute.RELEASE_YEAR.toString(),
                    movieTableModel.isDisplayed(MovieAttribute.RELEASE_YEAR)
            );
            this.add(releaseYearCB);

            JCheckBox certCB = new JCheckBox(
                    MovieAttribute.CERT.toString(),
                    movieTableModel.isDisplayed(MovieAttribute.CERT)
            );
            this.add(certCB);

            JCheckBox runtimeCB = new JCheckBox(
                    MovieAttribute.RUNTIME.toString(),
                    movieTableModel.isDisplayed(MovieAttribute.RUNTIME)
            );
            this.add(runtimeCB);

            JCheckBox genresCB = new JCheckBox(
                    MovieAttribute.GENRES.toString(),
                    movieTableModel.isDisplayed(MovieAttribute.GENRES)
            );
            this.add(genresCB);

            JCheckBox imdbCB = new JCheckBox(
                    MovieAttribute.IMDB_RATING.toString(),
                    movieTableModel.isDisplayed(MovieAttribute.IMDB_RATING)
            );
            this.add(imdbCB);

            JCheckBox metascoreCB = new JCheckBox(
                    MovieAttribute.METASCORE.toString(),
                    movieTableModel.isDisplayed(MovieAttribute.METASCORE)
            );
            this.add(metascoreCB);

            JButton okButton = new JButton("OK");
            okButton.addActionListener(e -> {
                java.util.List<MovieAttribute> selectedAttr = new ArrayList<>();
                if (directorCB.isSelected()) selectedAttr.add(MovieAttribute.DIRECTOR);
                if (titleCB.isSelected()) selectedAttr.add(MovieAttribute.TITLE);
                if (releaseYearCB.isSelected()) selectedAttr.add(MovieAttribute.RELEASE_YEAR);
                if (certCB.isSelected()) selectedAttr.add(MovieAttribute.CERT);
                if (runtimeCB.isSelected()) selectedAttr.add(MovieAttribute.RUNTIME);
                if (genresCB.isSelected()) selectedAttr.add(MovieAttribute.GENRES);
                if (imdbCB.isSelected()) selectedAttr.add(MovieAttribute.IMDB_RATING);
                if (metascoreCB.isSelected()) selectedAttr.add(MovieAttribute.METASCORE);

                movieTableModel.setDisplayedAttributes(selectedAttr.toArray(MovieAttribute[]::new));
                this.dispose();
            });
            this.add(okButton);

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> this.dispose());
            this.add(cancelButton);

            this.pack();
            setLocationRelativeTo(MainFrame.this);
            this.setVisible(true);
        }
    }
}
