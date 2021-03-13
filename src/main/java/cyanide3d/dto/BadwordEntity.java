package cyanide3d.dto;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "badwords")
public class BadwordEntity implements Entity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    private String word;
    @Column(name = "guild_id")
    private String guildId;

    public BadwordEntity() {
    }

    public BadwordEntity(String word, String guildId) {
        this.word = word;
        this.guildId = guildId;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }
}
