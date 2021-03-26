package cyanide3d.repository.service;

import cyanide3d.repository.model.DefaultRoleEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DefaultRoleService extends AbstractHibernateService<Long, DefaultRoleEntity> {

    private static DefaultRoleService instance;

    public DefaultRoleService() {
        super(DefaultRoleEntity.class);
    }

    public void addDefaultRole(List<String> roleIDs, String guildId) {
        findOneByGuildId(guildId).ifPresentOrElse(e -> {
            e.getRoles().addAll(roleIDs);
            update(e);
        }, () -> create(new DefaultRoleEntity(guildId, roleIDs)));
    }

    public void deleteDefaultRole(String roleID, String guildId) {
        if (roleID.equals("all"))
            deleteAllRolesByGuild(guildId);
        else
            deleteOneRole(roleID, guildId);
    }

    public List<String> getAllRoleIDsForGuild(String guildId) {
        return findOneByGuildId(guildId)
                .map(DefaultRoleEntity::getRoles)
                .orElseGet(ArrayList::new);
    }

    private void deleteAllRolesByGuild(String guildId) {
        DefaultRoleEntity entity = findOneByGuildId(guildId).orElseThrow();
        delete(entity);
    }

    private void deleteOneRole(String roleID, String guildId) {
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
