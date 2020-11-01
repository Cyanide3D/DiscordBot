package cyanide3d.conf;


import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAccessToCommand {

    public static UserAccessToCommand instance;
    public Map<String, Integer> rolesIDs = new HashMap<>();

    public boolean getAccess(Member mbr, Enum permission){
        List<Role> roleList = mbr.getRoles();
        int perm = checkPermission(permission);
        for(Role role : roleList){
            if(rolesIDs.containsKey(role.getId())){
                if(rolesIDs.get(role.getId()) <= perm){
                    return true;
                }
            }
        }
        return false;
    }

    public int checkPermission(Enum permission){
        if(permission == Permission.OWNER)
            return 0;
        if(permission == Permission.ADMIN)
            return 1;
        if(permission == Permission.MODERATOR)
            return 2;
        return 2;
    }

    public void setRolesIDs() {
        DatabaseConnection db = new DatabaseConnection();
        try {
            rolesIDs = db.getLstIDs();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    public static UserAccessToCommand getInstance(){
        if(instance == null){
            instance = new UserAccessToCommand();
        }
        return instance;
    }

}
