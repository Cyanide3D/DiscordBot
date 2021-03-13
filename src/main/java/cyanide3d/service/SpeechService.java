package cyanide3d.service;

import cyanide3d.dao.old.SpeechDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class SpeechService {

    private static SpeechService instance;
    private final SpeechDao dao;
    private final Logger logger = LoggerFactory.getLogger(EmoteService.class);
    private final Set<String> badWords;

    public static SpeechService getInstance() {
        if (instance == null) instance = new SpeechService();
        return instance;
    }

    private SpeechService() {
        dao = new SpeechDao();
        badWords = dao.getAll();
        logger.info("Loading " + badWords.size() + " bad words.");
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
