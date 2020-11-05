package cyanide3d.dao;

import cyanide3d.conf.Config;

import java.util.Map;

public class GainExpDao {

    private DatabaseConnection connection;

    private final String SELECT_USERLVL_QUERY = "select * from userlvl";
    private final String SELECT_USEREXP_QUERY = "select * from userexp";
    private final String ADD_USERLVL_QUERY = "insert into userlvl (id,lvl) values (?,?)";
    private final String ADD_USEREXP_QUERY = "insert into userexp (id,exp) values (?,?)";
    private final String UPDATE_USERLVL_QUERY = "update userlvl set lvl=? where id=?";
    private final String UPDATE_USEREXP_QUERY = "update userexp set exp=? where id=?";

    public GainExpDao() {
        Config config = Config.getInstance();
        connection = new DatabaseConnection(config.getUrl(), config.getUsename(), config.getPassword());
    }

    public void updateExp(String userId, String newExp) {
        connection.update(UPDATE_USEREXP_QUERY, newExp, userId);
    }

    public void insertUserLvl(String id) {
        connection.insert(ADD_USERLVL_QUERY, id, "0");
    }

    public void insertUserExp(String id) {
        connection.insert(ADD_USEREXP_QUERY, id, "0");
    }

    public void updateLvl(String userId, String newLvl) {
        connection.update(UPDATE_USERLVL_QUERY, newLvl,userId);
    }

    public Map<String, String> getAllUserLvl(){
        return connection.getBlacklist(SELECT_USERLVL_QUERY);
    }

    public Map<String, String> getAllUserExp(){
        return connection.getBlacklist(SELECT_USEREXP_QUERY);
    }
}
