package cyanide3d.model;

public class CommandsDaoModel {
    String name;
    String body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public CustomCommand toCustomCommand() {
        return new CustomCommand(name, body);
    }
}
