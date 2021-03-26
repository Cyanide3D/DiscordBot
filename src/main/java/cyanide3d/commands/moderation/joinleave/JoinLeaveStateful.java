package cyanide3d.commands.moderation.joinleave;

import cyanide3d.service.JoinLeaveService;
import cyanide3d.util.ActionType;

public class JoinLeaveStateful {

    State state = State.ACTION;
    String action, title, body, imageUrl, guildId;

    public JoinLeaveStateful(String guildId) {
        this.guildId = guildId;
    }

    public String parse(String message) {
        String replyMessage;
        switch (state) {
            case ACTION:
                state = State.TITLE;
                action = message;
                replyMessage = "Введите заголовок оповещения";
                break;
            case TITLE:
                state = State.BODY;
                title = message;
                replyMessage = "Введите текст оповещения.";
                break;
            case BODY:
                state = State.IMAGE;
                body = message;
                replyMessage = "Введите URL картинки.";
                break;
            case IMAGE:
                state = State.DONE;
                imageUrl = message;
                replyMessage = "Готово.";
                JoinLeaveService.getInstance().saveOrUpdate(ActionType.valueOf(action.toUpperCase()), title, body, imageUrl, guildId);
                break;
            default:
                throw new UnsupportedOperationException("?");
        }

        return replyMessage;
    }

    public boolean isDone() {
        return state.equals(State.DONE);
    }

    private enum State {
        ACTION, TITLE, BODY, IMAGE, DONE
    }
}
