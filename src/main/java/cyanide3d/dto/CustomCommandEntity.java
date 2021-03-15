package cyanide3d.dto;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "disord_custom_commands")
public class CustomCommandEntity implements Entity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    private String command;
    @Basic
    private String body;

    public CustomCommandEntity() {
    }

    public CustomCommandEntity(String command, String body) {
        this.command = command;
        this.body = body;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
