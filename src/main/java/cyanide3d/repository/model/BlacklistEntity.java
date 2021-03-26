package cyanide3d.repository.model;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "discord_blacklist")
public class BlacklistEntity implements Entity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    private String name;
    @Basic
    private String reason;
    @Column(name = "guild_id")
    private String guildId;

    public BlacklistEntity() {
    }

    public BlacklistEntity(String name, String reason, String guildId) {
        this.name = name;
        this.reason = reason;
        this.guildId = guildId;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
