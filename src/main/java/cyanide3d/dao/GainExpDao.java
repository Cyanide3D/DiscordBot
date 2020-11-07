package cyanide3d.dao;

import cyanide3d.conf.Config;
import cyanide3d.model.UserLevel;

import java.util.List;
import java.util.Map;

public class GainExpDao {

    private DatabaseConnection connection;

    private final String SELECT_QUERY = "select * from userlevel";
    private final String ADD_QUERY = "insert into userlevel (id,exp,lvl) values (?,?,?)";
    private final String UPDATE_USERLVL_QUERY = "update userlevel set lvl=? where id=?";
    private final String UPDATE_USEREXP_QUERY = "update userlevel set exp=? where id=?";

    public GainExpDao() {
        Config config = Config.getInstance();
        connection = new DatabaseConnection(config.getUrl(), config.getUsename(), config.getPassword());
    }

    public void insert(String userId, int userExp, int userLvl){
        connection.insert(ADD_QUERY,userId,userExp,userLvl);
    }
    public void updateExp(String userId, int userExp){
        connection.update(UPDATE_USEREXP_QUERY, userExp,userId);
    }
    public void updateLvl(String userId, int userLvl){
        connection.update(UPDATE_USERLVL_QUERY, userLvl,userId);
    }
    public List<UserLevel> getAll(){
        return connection.getUsersLevel(SELECT_QUERY);
    }
}
