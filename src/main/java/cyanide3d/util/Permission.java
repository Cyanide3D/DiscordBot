package cyanide3d.util;

public enum Permission {
    OWNER(0),
    ADMIN(1),
    MODERATOR(2),
    USER(3);

    private final int code;

    Permission(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Permission permByCode(int code) {
        if (code == 0)
            return OWNER;
        else if (code == 1)
            return ADMIN;
        else if (code == 2)
            return MODERATOR;
        else
            return USER;
    }
}
