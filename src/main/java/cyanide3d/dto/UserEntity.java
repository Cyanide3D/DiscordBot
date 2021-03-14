package cyanide3d.dto;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "users")
public class UserEntity implements Entity<String>{

    @Id
    @Column(name = "user_id")
    private String id;
    @Basic
    private int lvl;
    @Basic
    private int exp;
    @Column(name = "guild_id")
    private String guildId;

    public UserEntity() {
    }

    public UserEntity(String id, String guildId) {
        this.id = id;
        this.exp = 1;
        this.guildId = guildId;
        this.lvl = 0;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
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

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
