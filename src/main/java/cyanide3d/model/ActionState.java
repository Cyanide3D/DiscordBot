package cyanide3d.model;

public class ActionState {
    String action;
    String state;

    public ActionState(String action, String state) {
        this.action = action;
        this.state = state;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
