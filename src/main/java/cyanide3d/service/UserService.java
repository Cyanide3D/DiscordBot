package cyanide3d.service;

import cyanide3d.dao.UserDao;
import cyanide3d.model.User;
import java.util.List;

public class UserService {
    private static UserService instance;
    final UserDao dao;

    private UserService() {
        dao = new UserDao();
    }

    public static UserService getInstance() {
        if (instance == null) instance = new UserService();
        return instance;
    }

    public List<User> getAllUsers() {
        return dao.list();
    }

    public User getUser(String userId) {
        return dao.get(userId);
    }

    public User addUser(String id) {
        User user = new User(id, 0, 0);
        dao.create(user);
        return user;
    }

    public void deleteUser(String userId){
        User user = getUser(userId);
        if(user!=null){
            dao.delete(user);
        }
    }

    public User incrementExp(String userId) {
        User user = dao.get(userId);
        if (user == null) {
            user = new User(userId, 0, 1);
            dao.create(user);
        } else {
            user.incrementExp();
            dao.update(user);
        }
        return user;
    }

    public User incrementExp(String userId, int exp) {
        User user = dao.get(userId);
        if (user == null) {
            user = new User(userId);
            user.incrementExp(exp);
            dao.create(user);
        } else {
            user.incrementExp();
            dao.update(user);
        }
        return user;
    }

    public int getUserExp(String userId) {//не уверен, что оно вообще нужно
        final User user = dao.get(userId);
        return user != null ? user.getExperience() : 0;
    }

    public int getUserLvl(String userId) {//не уверен, что оно вообще нужно
        final User user = dao.get(userId);
        return user != null ? user.getLevel() : 0;
    }
}
