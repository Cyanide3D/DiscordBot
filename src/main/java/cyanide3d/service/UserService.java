package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.UserEntity;
import cyanide3d.model.User;

import java.util.List;

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
        return findOneByUserId(userId);
    }

    public void deleteUser(String userId) {
        final UserEntity entity = findOneByUserId(userId);

        if (entity == null) {
            return;
        }

        delete(entity);
    }

    public UserEntity incrementExp(String userId) {

        UserEntity entity = findOneByUserId(userId);

        if (entity == null) {
            create(new UserEntity(userId, guildId));
            entity = findOneByUserId(userId);
        } else {
            int level = entity.getLvl();
            int exp = entity.getExp();
            if (isLevelUp(level, exp)) {
                entity.setExp(0);
                entity.setLvl(++level);
            } else {
                entity.setExp(++exp);
            }
            update(entity);
        }

        return entity;
    }

    private boolean isLevelUp(int lvl, int exp) {
        return exp >= getLevelTreshold(lvl);
    }

    private int getLevelTreshold(int lvl) {
        return lvl * 2 + 15;
    }

    private UserEntity findOneByUserId(String userId) {
        return findOneByField("user_id", userId, guildId);
    }
}
