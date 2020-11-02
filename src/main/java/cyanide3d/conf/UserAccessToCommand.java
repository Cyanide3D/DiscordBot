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

    public boolean getAccess(Member mbr, Permission permission){
        List<Role> roleList = mbr.getRoles();
        for(Role role : roleList){
            if(rolesIDs.containsKey(role.getId())){
                if(rolesIDs.get(role.getId()) <= permission.getCode())
                    return true;
            }
        }
        return false;
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
