package cyanide3d.repository.service;

import cyanide3d.repository.model.UserEntity;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class UserService extends AbstractHibernateService<Long, UserEntity> {

    private static UserService instance;

    public UserService() {
        super(UserEntity.class);
    }

    public List<UserEntity> getAllUsers(String guildId) {
        List<UserEntity> users = listByGuildId(guildId);
        users.sort(Comparator.comparing(UserEntity::getLvl).thenComparing(UserEntity::getExp).reversed());
        return users;
    }

    public UserEntity getUserById(String userId, String guildId) {
        return findOneByUserId(userId, guildId).orElseThrow();
    }

    public void deleteUserById(String userId, String guildId) {
        findOneByUserId(userId, guildId).ifPresent(this::delete);
    }

    public UserEntity incrementExpOrCreate(String userId, String guildId) {
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

    private boolean isLevelUp(int lvl, int exp) {
        return exp >= getLevelTreshold(lvl);
    }

    private int getLevelTreshold(int lvl) {
        return lvl * 2 + 15;
    }

    private Optional<UserEntity> findOneByUserId(String userId, String guildId) {
        return findOneByField("userId", userId, guildId);
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

}
