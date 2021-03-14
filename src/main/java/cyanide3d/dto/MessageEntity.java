package cyanide3d.dto;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "discord_message")
public class MessageEntity implements Entity<String>{

    @Id
    private String id;
    @Basic
    private String body;
    @Column(name = "guild_id")
    private String guildId;

    public MessageEntity() {
    }

    public MessageEntity(String id) {
        this.id = id;
    }

    public MessageEntity(String id, String body, String guildId) {
        this.id = id;
        this.body = body;
        this.guildId = guildId;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
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
