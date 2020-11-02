package cyanide3d.service;

import cyanide3d.conf.Permission;
import cyanide3d.exceprtion.UnsupportedPermissionException;

public class PermissionService {
    private static final PermissionService instance = new PermissionService();

    public static PermissionService getInstance() {
        return instance;
    }

    public void addRole(String role, String perm) throws UnsupportedPermissionException {
        try {
            Permission permission = Permission.valueOf(perm.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedPermissionException(perm);
        }
    }
}
