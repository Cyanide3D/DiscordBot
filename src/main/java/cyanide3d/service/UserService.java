package cyanide3d.service;

import cyanide3d.dao.UserDao;
import cyanide3d.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserService {
    private static UserService instance;
    UserDao dao;
    //private Map<String, User> users;//Не думаю, что тут нужно кешировать

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

    public void increaseExp(String userId) {
        User user = dao.get(userId);
        if (user == null) {
            dao.create(new User(userId, 0, 1));
        } else {
            user.incrementExp();
            dao.update(user);
        }
    }

    public void userLvlUp(String userId) {
        final User user = dao.get(userId);
        if (user != null) {
            user.levelUp();
            dao.update(user);
        }
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
