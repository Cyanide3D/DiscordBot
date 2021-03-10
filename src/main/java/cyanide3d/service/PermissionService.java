package cyanide3d.service;

import cyanide3d.conf.Permission;
import cyanide3d.dao.PermissionDao;
import cyanide3d.exceprtion.UnsupportedPermissionException;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class PermissionService {
    Logger logger = LoggerFactory.getLogger(PermissionService.class);
    private static PermissionService instance;
    private final PermissionDao dao;
    private final Map<String, Permission> permissionMap;

    public static PermissionService getInstance() {
        if(instance==null) instance = new PermissionService();
        return instance;
    }

    private PermissionService() {
        dao = new PermissionDao();
        permissionMap = dao.getAll();
    }

    public boolean checkPermission(Member user, Permission userPerm){
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (permissionMap.containsKey(role.getId()) && permissionMap.get(role.getId()).getCode() <= userPerm.getCode())
                return true;
        }
        return false;
    }

    public void addRole(Role role, String perm) throws UnsupportedPermissionException {
        if (permissionMap.containsKey(role.getId())) return;
        try {
            Permission permission = Permission.valueOf(perm.toUpperCase());
            permissionMap.put(role.getId(), permission);
            dao.insert(role.getId(), permission.getCode());
        } catch (IllegalArgumentException e) {
            logger.error("PermissionService.addRole UnsupportedPermissionException", e);
            throw new UnsupportedPermissionException(perm);
        }
    }

    public void changeRole(Role role, String perm) throws UnsupportedPermissionException {
        try {
            Permission permission = Permission.valueOf(perm.toUpperCase());
            permissionMap.remove(role.getId());
            permissionMap.put(role.getId(), permission);
            dao.update(role.getId(), permission.getCode());
        } catch (IllegalArgumentException e) {
            logger.error("PermissionService.changeRole UnsupportedPermissionException", e);
            throw new UnsupportedPermissionException(perm);
        }
    }

    public void removeRole(Role role, String perm) throws UnsupportedPermissionException {
        try {
            if (permissionMap.containsKey(role.getId())) {
                permissionMap.remove(role.getId());
                dao.remove(role.getId());
            }
        } catch (IllegalArgumentException e) {
            logger.error("PermissionService.removeRole UnsupportedPermissionException", e);
            throw new UnsupportedPermissionException(perm);
        }
    }

    public Map<String, Permission> getPermissions() {
        return permissionMap;
    }

    public List<String> getRoleIdsByPermission(Permission permission) {
        return dao.getRoleIdsByPermission(permission);
    }
}