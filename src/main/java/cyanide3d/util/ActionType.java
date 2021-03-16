package cyanide3d.util;

public enum ActionType {
    LEAVE("leave_event"),
    JOIN("join_event"),
    BLACKLIST("blacklist_event"),
    EXP("exp_event"),
    LOG("log_event"),
    STATEMENT("statement_event"),
    JOIN_MESSAGE("join_message_event"),
    DIALOG("dialog_event"),
    VACATION("vacation_event"),
    SPEECH("speech_filter_event");

    private final String name;

    ActionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
