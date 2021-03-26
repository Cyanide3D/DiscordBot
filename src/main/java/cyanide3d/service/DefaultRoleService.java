package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.DefaultRoleEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DefaultRoleService extends DAO<Long, DefaultRoleEntity> {

    private static DefaultRoleService instance;

    public DefaultRoleService() {
        super(DefaultRoleEntity.class);
    }

    public synchronized void add(List<String> roleIDs, String guildId) {
        findOneByGuildId(guildId).ifPresentOrElse(e -> {
            e.getRoles().addAll(roleIDs);
            update(e);
        }, () -> create(new DefaultRoleEntity(guildId, roleIDs)));
    }

    public synchronized void delete(String roleID, String guildId) {
        if (roleID.equals("all"))
            deleteAllRoles(guildId);
        else
            deleteOneRole(roleID, guildId);
    }

    public synchronized List<String> getAllRoleIDs(String guildId) {
        return findOneByGuildId(guildId)
                .map(DefaultRoleEntity::getRoles)
                .orElseGet(ArrayList::new);
    }

    private synchronized void deleteAllRoles(String guildId) {
        DefaultRoleEntity entity = findOneByGuildId(guildId).orElseThrow();
        delete(entity);
    }

    private synchronized void deleteOneRole(String roleID, String guildId) {
        DefaultRoleEntity entity = findOneByGuildId(guildId).orElseThrow();
        entity.getRoles().removeIf(id -> id.equals(roleID));
        update(entity);
    }

    private Optional<DefaultRoleEntity> findOneByGuildId(String guildId) {
        return findOneByField("guildId", guildId, guildId);
    }

    public static DefaultRoleService getInstance() {
        if (instance == null) {
            instance = new DefaultRoleService();
        }
        return instance;
    }

}
