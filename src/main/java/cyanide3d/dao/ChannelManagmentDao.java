package cyanide3d.dao;

import cyanide3d.conf.Config;

import java.util.Map;

public class ChannelManagmentDao {
    private DatabaseConnection connection;
    private final String SELECT_QUERY = "select * from channels";
    private final String ADD_QUERY = "insert into channels (channelid,action) values (?,?)";
    private final String REMOVE_QUERY = "delete from channels where action = ?";
    private final String UPDATE_QUERY = "update channels set channelid=? where action=?";

    public ChannelManagmentDao() {
        Config config = Config.getInstance();
        connection = new DatabaseConnection(config.getUrl(), config.getUsename(), config.getPassword());
    }

    public void insert(String action, String channelID) {
        connection.insert(ADD_QUERY,channelID,action);
    }

    public void update(String action, String channelID) {
        connection.update(UPDATE_QUERY,channelID,action);
    }

    public void delete(String action) {
        connection.delete(REMOVE_QUERY,action);
    }

    public Map<String, String> getAll() {
        return connection.getListChannels(SELECT_QUERY);
    }
}
