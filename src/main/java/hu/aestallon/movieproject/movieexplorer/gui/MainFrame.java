package hu.aestallon.movieproject.movieexplorer.gui;

import hu.aestallon.movieproject.movieexplorer.database.MovieDao;
import hu.aestallon.movieproject.movieexplorer.movie.Movie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    private final MovieDao movieDao;
    private final MovieTableModel movieTableModel;
    private final JTable movieTable;
    private final TablePanel tablePanel;
    private final ControlPanel controlPanel;

    public MainFrame(MovieDao movieDao) {
        this.movieDao = movieDao;
        this.movieTableModel = new MovieTableModel(movieDao);
        this.movieTable = new JTable(movieTableModel);
        this.tablePanel = new TablePanel();
        this.controlPanel = new ControlPanel();

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

    private class ControlPanel extends JPanel {

        private ControlPanel() {
            this.setSize(100, 400);
            this.setBackground(new Color(234, 100, 81));
            this.setLocation(600, 0);

            JButton removeBtn = new JButton("RemoveYear");
            removeBtn.addActionListener(e ->
                    movieTableModel.setDisplayedAttributes(
                            MovieAttribute.TITLE,
                            MovieAttribute.DIRECTOR
                    )
            );
            this.add(removeBtn);

            JButton addBtn = new JButton("AddYear");
            addBtn.addActionListener(e ->
                    movieTableModel.setDisplayedAttributes(
                            MovieAttribute.TITLE,
                            MovieAttribute.RELEASE_YEAR,
                            MovieAttribute.DIRECTOR
                    )
            );
            this.add(addBtn);
        }
    }

    private class MovieInfoDialog extends JFrame {

        private MovieInfoDialog(Movie movie) {
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
            JLabel label = new JLabel(movieText);
            this.add(label);
            this.pack();
            setLocationRelativeTo(MainFrame.this);
            this.setVisible(true);
        }
    }
}
