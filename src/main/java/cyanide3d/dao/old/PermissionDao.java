package cyanide3d.dao.old;

import cyanide3d.conf.Config;
import cyanide3d.util.Permission;

import java.util.List;
import java.util.Map;

public class PermissionDao {
    private final DatabaseConnection connection;
    private final String SELECT_QUERY = "select * from userids";
    private final String ADD_QUERY = "insert into userids (userid,permission) values (?,?)";
    private final String REMOVE_QUERY = "delete from userids where userid = ?";
    private final String UPDATE_QUERY = "update userids set permission=? where userid=?";
    private final String GET_ROLES_BY_ID_QUERY = "select userid from userids where permission=?";

    public PermissionDao() {
        Config config = Config.getInstance();
        connection = new DatabaseConnection(config.getUrl(), config.getUsename(), config.getPassword());
    }

    public void insert(String idRole, int code) {
        connection.insert(ADD_QUERY, idRole, code);
    }

    public void update(String idRole, int code) {
        connection.update(UPDATE_QUERY, code, idRole);
    }

    public void remove(String idRole) {
        connection.delete(REMOVE_QUERY, idRole);
    }

    public Map<String,Permission> getAll(){
        return connection.getListPermissions(SELECT_QUERY);
    }

    public List<String> getRoleIdsByPermission(Permission permission) {
        //select userid from userids where permission=:permission
        return connection.getRoleIdsByPermission(GET_ROLES_BY_ID_QUERY, permission.getCode());
    }

}