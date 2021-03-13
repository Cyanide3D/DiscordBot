package cyanide3d.dao.old;

import cyanide3d.conf.Config;

import java.util.HashSet;
import java.util.Set;

public class SpeechDao {

    private final DatabaseConnection connection;
    private final String SELECT_QUERY = "select word from badwords";
    private final String ADD_QUERY = "insert into badwords (word) values (?);";
    private final String REMOVE_QUERY = "delete from badwords where word = ?";
//    private final String CHECK_QUERY;


    public SpeechDao() {
        Config config = Config.getInstance();
        connection = new DatabaseConnection(config.getUrl(), config.getUsename(), config.getPassword());
    }

    public Set<String> getAll() {
        return new HashSet<>(connection.getListSet(SELECT_QUERY));
    }

    public void add(String word) {
        connection.insert(ADD_QUERY, word);
    }

    public boolean remove(String word) {
        return connection.delete(REMOVE_QUERY, word) > 0;
    }
}
