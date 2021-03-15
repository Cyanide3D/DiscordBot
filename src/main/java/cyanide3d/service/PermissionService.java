package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.PermissionEntity;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PermissionService extends DAO<Long, PermissionEntity> {

    private static PermissionService instance;

    public PermissionService(Class<PermissionEntity> entityClass) {
        super(entityClass);
    }

    public synchronized boolean isAvailable(Member user, Permission permission, String guildId){

        if (user.isOwner())
            return true;

        final List<String> roles = user.getRoles().stream()
                .map(ISnowflake::getId)
                .collect(Collectors.toList());

        return findListByPermission(permission, guildId).stream()
                .anyMatch(entity -> roles.contains(entity.getRoleId()));
    }

    public synchronized boolean addRole(Role role, Permission permission, String guildId) {
        if(findOneByRoleId(role, guildId).isPresent()){
            return false;
        }
        create(new PermissionEntity(role.getId(), permission.getCode(), guildId));
        return true;
    }

    public synchronized boolean changeRole(Role role, Permission permission, String guildId) {
        return findOneByRoleId(role, guildId)
                .map(entity -> {
                    entity.setPermission(permission.getCode());
                    update(entity);
                    return true;
                })
                .orElse(false);
    }

    public synchronized boolean removeRole(Role role, String guildId) {
        return findOneByRoleId(role, guildId)
                .map(entity -> {
                    delete(entity);
                    return true;
                })
                .orElse(false);
    }

    private synchronized List<PermissionEntity> findListByPermission(Permission permission, String guildId) {
        return sessionFactory.fromSession(session -> {
            String asd = "from PermissionEntity E where E.permission<=:permission and E.guildId=:guildId";
            final Query query = session.createQuery(asd);
            query.setParameter("guildId", guildId);
            query.setParameter("permission", permission.getCode());
            return query.getResultList();
        });
    }

    private synchronized Optional<PermissionEntity> findOneByRoleId(Role role, String guildId) {
        return findOneByField("roleId", role.getId(), guildId);
    }

    public static PermissionService getInstance() {
        if (instance == null) {
            instance = new PermissionService(PermissionEntity.class);
        }
        return instance;
    }

}