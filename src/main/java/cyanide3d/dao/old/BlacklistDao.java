package cyanide3d.dao.old;

import cyanide3d.conf.Config;

import java.util.Map;

public class BlacklistDao {
    private final DatabaseConnection connection;
    private final String SELECT_QUERY = "select * from blacklist";
    private final String ADD_QUERY = "insert into blacklist (username,reason) values (?,?)";
    private final String REMOVE_QUERY = "delete from blacklist where username = ?";

    public BlacklistDao() {
        Config config = Config.getInstance();
        connection = new DatabaseConnection(config.getUrl(), config.getUsename(), config.getPassword());
    }

    public void add(String nickname, String reason) {
        connection.insert(ADD_QUERY,nickname,reason);
    }

    public void delete(String nickname) {
        connection.delete(REMOVE_QUERY,nickname);
    }
    public Map<String,String> giveAll(){
        return connection.getBlacklist(SELECT_QUERY);
    }
}
