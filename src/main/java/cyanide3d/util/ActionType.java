package cyanide3d.util;

public enum ActionType {
    LEAVE("joinleave"),
    JOIN("joinleave"),
    BLACKLIST("blacklist"),
    EXP("gainexp"),
    LOG("logging"),
    STATEMENT("joinform");

    private final String action;

    ActionType(String action) {
        this.action = action;
    }

    public String action() {
        return action;
    }
}
