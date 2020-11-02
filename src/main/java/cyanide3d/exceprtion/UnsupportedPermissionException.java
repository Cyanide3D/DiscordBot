package cyanide3d.exceprtion;

import org.apache.commons.lang3.StringUtils;

public class UnsupportedPermissionException extends Exception {
    public UnsupportedPermissionException(String perm) {
        super("Permissio [" + perm + "] does not exist");
    }
}
