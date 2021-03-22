package cyanide3d.dto;

import javax.persistence.*;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "discord_users")
public class UserEntity implements Entity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private String userId;
    @Basic
    private int lvl;
    @Basic
    private int exp;
    @Column(name = "guild_id")
    private String guildId;

    public UserEntity() {
    }

    public UserEntity(String userId, String guildId) {
        this.userId = userId;
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(guildId, that.guildId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, guildId);
    }
}
