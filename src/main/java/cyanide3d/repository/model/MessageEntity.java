package cyanide3d.repository.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "discord_message_store")
public class MessageEntity implements Entity<String>{

    @Id
    private String id;
    @Column(name = "body", columnDefinition = "TEXT")
    private String body;
    @Column(name = "publish_date")
    private LocalDate date;

    public MessageEntity() {
    }

    public MessageEntity(String id, String body) {
        this.id = id;
        this.body = body;
        this.date = LocalDate.now();
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
