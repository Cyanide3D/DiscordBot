package cyanide3d.service;
import cyanide3d.dao.GainExpDao;
import java.util.Map;

public class GainExpService {
    private static GainExpService instance;
    GainExpDao dao;
    private Map<String, String> usersLvl;
    private Map<String, String> usersExp;

    private GainExpService() {
        dao = new GainExpDao();
        usersLvl = dao.getAllUserLvl();
        usersExp = dao.getAllUserExp();
    }
    
    public static GainExpService getInstance() {
        if(instance==null) instance = new GainExpService();
        return instance;
    }
    
    public Map<String, String> getUsersLvl(){
        return usersLvl;
    }
    public Map<String, String> getUsersExp(){
        return usersExp;
    }

    public void addUser(String id) {
        usersExp.put(id,"0");
        usersLvl.put(id,"0");
        dao.insertUserLvl(id);
        dao.insertUserExp(id);
    }

    public void increaseExp(String userId) {
        if (!usersLvl.containsKey(userId)) addUser(userId);
        usersExp.put(userId,String.valueOf(Integer.parseInt(usersExp.get(userId))+1));
        dao.updateExp(userId, usersExp.get(userId));
    }

    public void userLvlUp(String userId) {
        String userNewLvl = String.valueOf(Integer.parseInt(usersLvl.get(userId))+1);
        usersLvl.put(userId,userNewLvl);
        usersExp.put(userId,"0");
        dao.updateLvl(userId,userNewLvl);
        dao.updateExp(userId,"0");
    }
}
