package cyanide3d.conf;

public enum Permission {
    OWNER(0),
    ADMIN(1),
    MODERATOR(2);

    private int code;

    Permission(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
