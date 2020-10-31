package conf;

import events.BadWordsEvent;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserAcessToCommand {

    //Permission list:
    //0 - author bot
    //1 - server creator
    //2 - moderator
    //3 - user

    public static UserAcessToCommand instance;

    public Map<String, Integer> userIDs = new HashMap<>();

    public boolean getAccess(String ID, Enum permission){
        int perm = checkPermission(permission);
        if(userIDs.containsKey(ID)) {
            if (userIDs.get(ID) <= perm){
                return true;
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
        if(permission == Permission.USER)
            return 3;
        return 2;
    }

    public boolean checkUserInMap(String userID){
        if(userIDs.containsKey(userID.toLowerCase())){
            return true;
        }
        return false;
    }

    public boolean isEmpty(){
        if(userIDs.size()==0){
            return true;
        }
        return false;
    }

    public void setUserIDs() {
        DatabaseConnection db = new DatabaseConnection();
        try {
            userIDs = db.getLstIDs();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    public static UserAcessToCommand getInstance(){
        if(instance == null){
            instance = new UserAcessToCommand();
        }
        return instance;
    }

}
