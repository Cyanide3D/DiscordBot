package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.UserEntity;

import java.util.List;
import java.util.Optional;

public class UserService extends DAO<String, UserEntity> {

    private final String guildId;

    public UserService(Class<UserEntity> entityClass, String guildId) {
        super(entityClass);
        this.guildId = guildId;
    }

    public List<UserEntity> getAllUsers() {
        return listByGuildId(guildId);
    }

    public UserEntity getUser(String userId) {
        return findOneByUserId(userId).orElse(null);
    }

    public void deleteUser(String userId) {
        findOneByUserId(userId).ifPresent(this::delete);
    }

    public UserEntity incrementExpOrCreate(String userId) {
        return findOneByUserId(userId)
                .map(this::incrementExp)
                .orElse(create(new UserEntity(userId, guildId)));

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

    private Optional<UserEntity> findOneByUserId(String userId) {
        return findOneByField("userId", userId, guildId);
    }
}
