package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.PermissionEntity;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PermissionService extends DAO<Long, PermissionEntity> {

    private static PermissionService instance;

    public PermissionService() {
        super(PermissionEntity.class);
    }

    public synchronized boolean isAvailable(Member user, Permission permission, String guildId) {

        if (user.isOwner())
            return true;

        final List<String> roles = user.getRoles().stream()
                .map(ISnowflake::getId)
                .collect(Collectors.toList());

        return !findListByPermission(permission, roles, guildId).isEmpty();
    }

    public synchronized boolean addRole(Role role, Permission permission, String guildId) {

        if (findOneByRoleId(role, guildId).isPresent())
            return false;

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

    private synchronized List<PermissionEntity> findListByPermission(Permission permission, List<String> roles, String guildId) {

        if (roles.isEmpty())
            roles.add("1");

        return sessionFactory.fromSession(session -> {
            String asd = "from PermissionEntity E where E.permission<=:permission and E.roleId in (:roles) and E.guildId=:guildId";
            final Query query = session.createQuery(asd);
            query.setParameter("guildId", guildId);
            query.setParameter("roles", roles);
            query.setParameter("permission", permission.getCode());
            final List resultList = query.getResultList();
            return resultList;
        });
    }

    private synchronized Optional<PermissionEntity> findOneByRoleId(Role role, String guildId) {
        return findOneByField("roleId", role.getId(), guildId);
    }

    public static PermissionService getInstance() {
        if (instance == null) {
            instance = new PermissionService();
        }
        return instance;
    }

}