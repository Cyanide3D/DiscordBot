package cyanide3d.exceprtion;

public class UnsupportedPermissionException extends Exception {
    public UnsupportedPermissionException(String perm) {
        super("Permission [" + perm + "] does not exist");
    }
}
