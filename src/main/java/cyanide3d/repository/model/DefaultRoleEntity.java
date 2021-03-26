package cyanide3d.repository.model;

import javax.persistence.*;
import java.util.List;

@javax.persistence.Entity
@Table(name = "discord_default_roles")
public class DefaultRoleEntity implements Entity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "guild_id")
    private String guildId;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    public DefaultRoleEntity() {
    }

    public DefaultRoleEntity(String guildId, List<String> roles) {
        this.guildId = guildId;
        this.roles = roles;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
