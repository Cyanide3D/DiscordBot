package cyanide3d.service;

import cyanide3d.conf.Permission;
import cyanide3d.dao.PermissionDao;
import cyanide3d.exceprtion.UnsupportedPermissionException;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionService {
    private static PermissionService instance;
    private final PermissionDao dao;
    private Map<String, Permission> permissionsDao;

    public static PermissionService getInstance() {
        if(instance==null) instance = new PermissionService();
        return instance;
    }

    private PermissionService() {
        dao = new PermissionDao();
        permissionsDao = dao.getAll();
    }

    public boolean checkPermission(Member user, Permission userPerm){
        List<Role> roles = user.getRoles();
        for (Role role : roles){
            if(permissionsDao.containsKey(role.getId()) && permissionsDao.get(role.getId()).getCode() <= userPerm.getCode()) return true;
        }
        return false;
    }

    public void addRole(Role role, String perm) throws UnsupportedPermissionException {
        if (permissionsDao.containsKey(role.getId())) return;
        try {
            Permission permission = Permission.valueOf(perm.toUpperCase());
            permissionsDao.put(role.getId(),permission);
            dao.insert(role.getId(),permission.getCode());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedPermissionException(perm);
        }
    }

    public void changeRole(Role role, String perm) throws UnsupportedPermissionException {
        try {
            Permission permission = Permission.valueOf(perm.toUpperCase());
            permissionsDao.remove(role.getId());
            permissionsDao.put(role.getId(),permission);
            dao.update(role.getId(),permission.getCode());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedPermissionException(perm);
        }
    }

    public void removeRole(Role role, String perm) throws UnsupportedPermissionException {
        try {
            if(permissionsDao.containsKey(role.getId())){
                permissionsDao.remove(role.getId());
                dao.remove(role.getId());
            }
        } catch (IllegalArgumentException e) {
            throw new UnsupportedPermissionException(perm);
        }
    }

    public Map<String, Permission> giveRoleList(){
        return permissionsDao;
    }
}
