package cyanide3d.dto;

import javax.persistence.*;
import java.util.Map;

@javax.persistence.Entity
@Table(name = "autorole")
public class AutoroleEntity implements Entity<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private String id;
    @ElementCollection
    @CollectionTable(name = "autorole_parts", joinColumns = {@JoinColumn(name = "part_id", referencedColumnName = "message_id")})
    @MapKeyColumn(name = "emote")
    @Column(name = "autorole")
    private Map<String, String> autoroles;
    @Column(name = "guild_id")
    private String guildId;

    public AutoroleEntity() {
    }

    public AutoroleEntity(String id, Map<String, String> autoroles, String guildId) {
        this.id = id;
        this.autoroles = autoroles;
        this.guildId = guildId;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getAutoroles() {
        return autoroles;
    }

    public void setAutoroles(Map<String, String> autoroles) {
        this.autoroles = autoroles;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }
}
