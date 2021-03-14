package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.PermissionEntity;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

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
        if(findOneByRoleId(role).isPresent()){
            return false;
        }
        create(new PermissionEntity(role.getId(), permission.getCode(), guildId));
        return true;
    }

    public boolean changeRole(Role role, Permission permission) {
        return findOneByRoleId(role)
                .map(entity -> {
                    entity.setPermission(permission.getCode());
                    update(entity);
                    return true;
                })
                .orElse(false);
    }

    public boolean removeRole(Role role) {
        return findOneByRoleId(role)
                .map(entity -> {
                    delete(entity);
                    return true;
                })
                .orElse(false);
    }

    private Optional<PermissionEntity> findOneByRoleId(Role role) {
        return findOneByField("roleId", role.getId(), guildId);
    }

    public List<PermissionEntity> getPermissions() {
        return listByGuildId(guildId);
    }
}