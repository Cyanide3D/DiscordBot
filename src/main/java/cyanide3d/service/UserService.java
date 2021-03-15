package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.UserEntity;

import java.util.List;
import java.util.Optional;

public class UserService extends DAO<Long, UserEntity> {

    private static UserService instance;

    public UserService(Class<UserEntity> entityClass) {
        super(entityClass);
    }

    public synchronized List<UserEntity> getAllUsers(String guildId) {
        return listByGuildId(guildId);
    }

    public synchronized UserEntity getUser(String userId, String guildId) {
        return findOneByUserId(userId, guildId).orElse(null);
    }

    public synchronized void deleteUser(String userId, String guildId) {
        findOneByUserId(userId, guildId).ifPresent(this::delete);
    }

    public synchronized UserEntity incrementExpOrCreate(String userId, String guildId) {
        return findOneByUserId(userId, guildId)
                .map(this::incrementExp)
                .orElseGet(() -> create(new UserEntity(userId, guildId)));

    }

    private synchronized UserEntity incrementExp(UserEntity entity) {
        int level = entity.getLvl();
        int exp = entity.getExp();
        if (isLevelUp(level, exp)) {
            entity.setExp(0);
            entity.setLvl(++level);
        } else {
            entity.setExp(++exp);
        }
        update(entity);
        return entity;
    }

    private synchronized boolean isLevelUp(int lvl, int exp) {
        return exp >= getLevelTreshold(lvl);
    }

    private synchronized int getLevelTreshold(int lvl) {
        return lvl * 2 + 15;
    }

    private synchronized Optional<UserEntity> findOneByUserId(String userId, String guildId) {
        return findOneByField("userId", userId, guildId);
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService(UserEntity.class);
        }
        return instance;
    }

}
