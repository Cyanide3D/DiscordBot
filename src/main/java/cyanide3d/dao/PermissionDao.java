package cyanide3d.dao;

import cyanide3d.conf.Config;
import cyanide3d.conf.Permission;

import java.util.HashMap;
import java.util.Map;

public class PermissionDao {
    private DatabaseConnection connection;
    private final String SELECT_QUERY = "select * from userids";
    private final String ADD_QUERY = "insert into userids (userid,permission) values (?,?)";
    private final String REMOVE_QUERY = "delete from userids where userid = ?";
    private final String UPDATE_QUERY = "update userids set permission=? where userid=?";

    public PermissionDao() {
        Config config = Config.getInstance();
        connection = new DatabaseConnection(config.getUrl(), config.getUsename(), config.getPassword());
    }

    public void insert(String idRole, int code) {
        connection.insert(ADD_QUERY,idRole,code);
    }
    public void update(String idRole, int code) {
        connection.update(UPDATE_QUERY,code,idRole);
    }
    public void remove(String idRole) {
        connection.delete(REMOVE_QUERY,idRole);
    }
    public Map<String,Permission> getAll(){
        return new HashMap<>(connection.getListPermissions(SELECT_QUERY));
    }
}
