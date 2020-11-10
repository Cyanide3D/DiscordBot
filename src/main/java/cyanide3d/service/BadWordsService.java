package cyanide3d.service;

import cyanide3d.dao.BadWordsDao;

import java.util.*;


public class BadWordsService {

    private static BadWordsService instance;
    private final BadWordsDao dao;
    private final Set<String> badWords;

    public static BadWordsService getInstance() {
        if (instance == null) instance = new BadWordsService();
        return instance;
    }

    private BadWordsService() {
        dao = new BadWordsDao();
        badWords = dao.getAll();
    }

    public boolean isBad(String word) {
        return badWords.contains(word);
    }

    public Set<String> getBadWords() {
        return Collections.unmodifiableSet(badWords);
    }

    public void add(String word) {
        dao.add(word);
        badWords.add(word);
    }

    public void remove(String word) {
        if (!badWords.contains(word)) return;
        dao.remove(word);
        badWords.remove(word);
    }
}
