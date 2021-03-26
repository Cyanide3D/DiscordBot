package cyanide3d.repository.model;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "discord_permission")
public class PermissionEntity implements Entity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role_id")
    private String roleId;
    @Basic
    private int permission;
    @Column(name = "guild_id")
    private String guildId;

    public PermissionEntity() {
    }

    public PermissionEntity(String roleId, int permission, String guildId) {
        this.roleId = roleId;
        this.permission = permission;
        this.guildId = guildId;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
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

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public Long getId() {
        return null;
    }
}
