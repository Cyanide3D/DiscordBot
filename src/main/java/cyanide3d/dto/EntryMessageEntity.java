package cyanide3d.dto;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@javax.persistence.Entity
@Table(name = "discord_entry_message")
public class EntryMessageEntity implements Entity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "guild_id")
    private String guildId;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> messages;

    public EntryMessageEntity() {
    }

    public EntryMessageEntity(String guildId, String key, String message) {
        final Map<String, String> messages = new HashMap<>();
        messages.put(key, message);
        this.messages = messages;
        this.guildId = guildId;
    }

    public void addMessage(String key, String message) {
        messages.put(key, message);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }
}
