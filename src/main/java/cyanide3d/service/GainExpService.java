package cyanide3d.service;
import cyanide3d.dao.GainExpDao;
import cyanide3d.model.UserLevel;

import java.util.List;
import java.util.Map;

public class GainExpService {
    private static GainExpService instance;
    GainExpDao dao;
    private List<UserLevel> users;

    private GainExpService() {
        dao = new GainExpDao();
        users = dao.getAll();
    }
    
    public static GainExpService getInstance() {
        if(instance==null) instance = new GainExpService();
        return instance;
    }
    
    public List<UserLevel> getUsers(){
        return users;
    }

    public void addUser(String id) {
        users.add(new UserLevel(id,0,0));
        dao.insert(id,0,0);
    }

    public void increaseExp(String userId) {
        for(UserLevel user : users){
            if (user.getUserId().equals(userId)){
                user.setUserExp(user.getUserExp()+1);
                dao.updateExp(userId,user.getUserExp());
                return;
            }
        }
        addUser(userId);
    }

    public void userLvlUp(String userId) {
        for(UserLevel user : users){
            if (user.getUserId().equals(userId)){
                user.setUserLvl(user.getUserLvl()+1);
                user.setUserExp(0);
                dao.updateLvl(userId,user.getUserLvl());
                dao.updateExp(userId,0);
                return;
            }
        }
    }
    public int getUserExp(String userId){
        for(UserLevel user : users){
            if(user.getUserId().equals(userId)){
                return user.getUserExp();
            }
        }
        return 0;
    }
    public int getUserLvl(String userId){
        for(UserLevel user : users){
            if(user.getUserId().equals(userId)){
                return user.getUserLvl();
            }
        }
        return 0;
    }
}
