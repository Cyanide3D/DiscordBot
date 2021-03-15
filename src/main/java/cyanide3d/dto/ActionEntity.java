package cyanide3d.dto;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name="discord_action")
public class ActionEntity implements Entity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    private boolean enabled;
    @Basic
    private String action;
    @Column(name = "guild_id")
    private String guildId;

    public ActionEntity() {
    }

    public ActionEntity(boolean enabled, String action, String guildId) {
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean state) {
        this.enabled = state;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "ActionEntity{" +
                "id=" + id +
                ", enabled=" + enabled +
                ", action='" + action + '\'' +
                ", guildId='" + guildId + '\'' +
                '}';
    }
}
