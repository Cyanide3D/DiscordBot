package cyanide3d.util;

import java.util.Arrays;

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
        return Arrays.stream(Permission.values())
                .filter(e -> e.getCode() == code)
                .findFirst().orElse(USER);
    }
}
