package cyanide3d.dto;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name="action")
public class ActionEntity implements Entity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    private boolean state;
    @Basic
    private String action;
    @Column(name = "guild_id")
    private String guildId;

    public ActionEntity() {
    }

    public ActionEntity(boolean state, String action, String guildId) {
        this.state = state;
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

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
