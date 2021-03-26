package cyanide3d.repository.service;

import cyanide3d.repository.model.PermissionEntity;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.Member;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PermissionService extends AbstractHibernateService<Long, PermissionEntity> {

    private static PermissionService instance;

    public PermissionService() {
        super(PermissionEntity.class);
    }

    public boolean isAvailable(Member user, Permission permission, String guildId) {

        if (user.isOwner())
            return true;

        final List<String> roles = user.getRoles().stream()
                .map(ISnowflake::getId)
                .collect(Collectors.toList());

        return !findListByPermission(permission, roles, guildId).isEmpty();
    }

    public void empowerRole(String role, Permission permission, String guildId) {
        findOneByRoleId(role, guildId).ifPresentOrElse(
                e -> changePermAsRole(role, permission, guildId),
                () -> create(new PermissionEntity(role, permission.getCode(), guildId))
        );
    }

    public void changePermAsRole(String role, Permission permission, String guildId) {
        final Optional<PermissionEntity> perm = findOneByRoleId(role, guildId);
        perm.ifPresent(entity -> {
            entity.setPermission(permission.getCode());
            update(entity);
        });
    }

    public void removePermAsRole(String role, String guildId) {
        findOneByRoleId(role, guildId)
                .ifPresent(this::delete);
    }

    public Map<Integer, List<String>> getRoleIdWithPerms(String guildId) {
        return listByGuildId(guildId).stream().collect(
                Collectors.groupingBy(
                        PermissionEntity::getPermission,
                        Collectors.mapping(PermissionEntity::getRoleId, Collectors.toList()))
        );
    }

    private List<PermissionEntity> findListByPermission(Permission permission, List<String> roles, String guildId) {

        if (roles.isEmpty())
            roles.add("1");

        return sessionFactory.fromSession(session -> {
            String asd = "from PermissionEntity E where E.permission<=:permission and E.roleId in (:roles) and E.guildId=:guildId";
            final Query<PermissionEntity> query = session.createQuery(asd, PermissionEntity.class);
            query.setParameter("guildId", guildId);
            query.setParameter("roles", roles);
            query.setParameter("permission", permission.getCode());
            return query.getResultList();
        });
    }

    private Optional<PermissionEntity> findOneByRoleId(String role, String guildId) {
        return findOneByField("roleId", role, guildId);
    }

    public static PermissionService getInstance() {
        if (instance == null) {
            instance = new PermissionService();
        }
        return instance;
    }

}