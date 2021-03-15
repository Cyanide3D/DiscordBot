package cyanide3d.util;

public enum ActionType {
    LEAVE("joinleave"),
    JOIN("joinleave"),
    BLACKLIST("blacklist"),
    EXP("gainexp"),
    LOG("logging"),
    STATEMENT("joinform"),
    DIALOG("answer"),
    DEFAULT("default"),
    VACATION("vacation"),
    SPEECH("speechfilter");

    private final String name;

    ActionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
