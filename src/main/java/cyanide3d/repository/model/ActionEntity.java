package cyanide3d.repository.model;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name="discord_action")
public class ActionEntity implements Entity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    private boolean enabled;
    @Basic
    private String name;
    @Column(name = "guild_id")
    private String guildId;

    public ActionEntity() {
    }

    public ActionEntity(boolean enabled, String name, String guildId) {
        this.enabled = enabled;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String action) {
        this.name = action;
    }

    @Override
    public String toString() {
        return "ActionEntity{" +
                "id=" + id +
                ", enabled=" + enabled +
                ", action='" + name + '\'' +
                ", guildId='" + guildId + '\'' +
                '}';
    }
}
