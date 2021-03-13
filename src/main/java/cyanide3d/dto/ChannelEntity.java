package cyanide3d.dto;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "channel_managment")
public class ChannelEntity implements Entity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "channel_id")
    private String channelId;
    @Basic
    private String action;
    @Column(name = "guild_id")
    private String guildId;

    public ChannelEntity() {
    }

    public ChannelEntity(String channelId, String action, String guildId) {
        this.channelId = channelId;
        this.action = action;
        this.guildId = guildId;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
