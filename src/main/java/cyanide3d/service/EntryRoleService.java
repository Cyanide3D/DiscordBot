package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.EntryRoleEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntryRoleService extends DAO<Long, EntryRoleEntity> {

    private static EntryRoleService instance;

    public EntryRoleService() {
        super(EntryRoleEntity.class);
    }

    public synchronized void add(List<String> roleIDs, String guildId) {
        findOneByGuildId(guildId).ifPresentOrElse(e -> {
            e.getRoles().addAll(roleIDs);
            update(e);
        }, () -> create(new EntryRoleEntity(guildId, roleIDs)));
    }

    public synchronized void delete(String roleID, String guildId) {
        if (roleID.equals("all"))
            deleteAllRoles(guildId);
        else
            deleteOneRole(roleID, guildId);
    }

    public synchronized List<String> getAllRoleIDs(String guildId) {
        return findOneByGuildId(guildId)
                .map(EntryRoleEntity::getRoles)
                .orElseGet(ArrayList::new);
    }

    private synchronized void deleteAllRoles(String guildId) {
        EntryRoleEntity entity = findOneByGuildId(guildId).orElseThrow();
        delete(entity);
    }

    private synchronized void deleteOneRole(String roleID, String guildId) {
        EntryRoleEntity entity = findOneByGuildId(guildId).orElseThrow();
        entity.getRoles().removeIf(id -> id.equals(roleID));
        update(entity);
    }

    private Optional<EntryRoleEntity> findOneByGuildId(String guildId) {
        return findOneByField("guildId", guildId, guildId);
    }

    public static EntryRoleService getInstance() {
        if (instance == null) {
            instance = new EntryRoleService();
        }
        return instance;
    }

}
