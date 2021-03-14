package cyanide3d.dto;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "disord_custom_commands")
public class CustomCommandEntity implements Entity<String>{

    @Id
    @Column(name = "command")
    private String id;
    @Basic
    private String body;

    public CustomCommandEntity() {
    }

    public CustomCommandEntity(String id, String body) {
        this.id = id;
        this.body = body;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
