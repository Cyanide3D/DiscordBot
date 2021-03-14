package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.UserEntity;

import java.util.List;
import java.util.Optional;

public class UserService extends DAO<String, UserEntity> {

    private static UserService instance;

    public UserService(Class<UserEntity> entityClass) {
        super(entityClass);
    }

    public List<UserEntity> getAllUsers(String guildId) {
        return listByGuildId(guildId);
    }

    public UserEntity getUser(String userId, String guildId) {
        return findOneByUserId(userId, guildId).orElse(null);
    }

    public void deleteUser(String userId, String guildId) {
        findOneByUserId(userId, guildId).ifPresent(this::delete);
    }

    public UserEntity incrementExpOrCreate(String userId, String guildId) {
        return findOneByUserId(userId, guildId)
                .map(this::incrementExp)
                .orElseGet(() -> create(new UserEntity(userId, guildId)));

    }

    private UserEntity incrementExp(UserEntity entity) {
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

    private boolean isLevelUp(int lvl, int exp) {
        return exp >= getLevelTreshold(lvl);
    }

    private int getLevelTreshold(int lvl) {
        return lvl * 2 + 15;
    }

    private Optional<UserEntity> findOneByUserId(String userId, String guildId) {
        return findOneByField("id", userId, guildId);
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService(UserEntity.class);
        }
        return instance;
    }

}
