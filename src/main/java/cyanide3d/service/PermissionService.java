package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.PermissionEntity;
import cyanide3d.util.Permission;
import cyanide3d.exceprtion.UnsupportedPermissionException;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PermissionService extends DAO<Long, PermissionEntity> {
    private final String guildId;
    Logger logger = LoggerFactory.getLogger(PermissionService.class);

    public PermissionService(Class<PermissionEntity> entityClass, String guildId) {
        super(entityClass);
        this.guildId = guildId;
    }

    public boolean checkPermission(Member user, Permission userPerm){
        //TODO
        return true;
    }

    public boolean addRole(Role role, Permission permission) {
        final PermissionEntity entity = findOneByRoleId(role);

        if (entity != null) {
            return false;
        }

        create(new PermissionEntity(role.getId(), permission.getCode(), guildId));
        return true;
    }

    public boolean changeRole(Role role, Permission permission) {
        final PermissionEntity entity = findOneByRoleId(role);

        if (entity == null) {
            return false;
        }

        entity.setPermission(permission.getCode());
        update(entity);
        return true;
    }

    public boolean removeRole(Role role) {
        final PermissionEntity entity = findOneByRoleId(role);

        if (entity == null) {
            return false;
        }

        delete(entity);
        return true;
    }

    private PermissionEntity findOneByRoleId(Role role) {
        return findOneByField("role_id", role.getId(), guildId);
    }

    public List<PermissionEntity> getPermissions() {
        return listByGuildId(guildId);
    }
}