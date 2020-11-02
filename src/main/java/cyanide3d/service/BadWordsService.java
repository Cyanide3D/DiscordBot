package cyanide3d.service;

import cyanide3d.conf.DatabaseConnection;

import java.sql.SQLException;
import java.util.*;


public class BadWordsService {

    private static BadWordsService instance;

    private Set<String> badWords = new HashSet<>();

    private BadWordsService() {
        DatabaseConnection db = new DatabaseConnection();
        try {
            badWords = db.listBadWords();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static BadWordsService getInstance() {
        if (instance == null) {
            instance = new BadWordsService();
        }
        return instance;
    }

    public void updateBadWords(){
        try {
            badWords = new DatabaseConnection().listBadWords();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public boolean isBad(String word) {
        return badWords.contains(word);
    }

    public Set<String> getBadWords() {
        return Collections.unmodifiableSet(badWords);
    }
}
