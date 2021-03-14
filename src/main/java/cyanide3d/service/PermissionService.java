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

    private static PermissionService instance;

    public PermissionService(Class<PermissionEntity> entityClass) {
        super(entityClass);
    }

    public synchronized boolean checkPermission(Member user, Permission userPerm, String guildId){
        //TODO
        return true;
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