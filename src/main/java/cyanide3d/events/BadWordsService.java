package cyanide3d.events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
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

    public boolean isBad(String word) {
        return badWords.contains(word);
    }

    public Set<String> getBadWords() {
        return Collections.unmodifiableSet(badWords);
    }
}
