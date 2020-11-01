package conf;

public enum Permission {
    OWNER(0),
    ADMIN(1),
    MODERATOR(2),
    USER(3);

    private int code;

    Permission(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
