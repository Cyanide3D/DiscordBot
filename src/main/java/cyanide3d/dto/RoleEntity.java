package cyanide3d.dto;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "discord_role")
public class RoleEntity implements Entity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role_id")
    private String roleId;
    @Basic
    private String date;
    @Column(name = "guild_id")
    private String guildId;
    @Basic
    private int count;

    public RoleEntity() {
    }

    public RoleEntity(String roleId, String date, String guildId) {
        this.roleId = roleId;
        this.date = date;
        this.guildId = guildId;
        this.count = 1;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
