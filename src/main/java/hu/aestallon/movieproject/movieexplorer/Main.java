package hu.aestallon.movieproject.movieexplorer;

import hu.aestallon.movieproject.movieexplorer.database.MovieDao;
import hu.aestallon.movieproject.movieexplorer.gui.MainFrame;

public class Main {

    public static void main(String[] args) {
        new MainFrame(new MovieDao());
    }
}
