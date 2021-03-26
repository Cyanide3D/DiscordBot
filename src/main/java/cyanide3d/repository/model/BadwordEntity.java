package cyanide3d.repository.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "discord_badwords")
public class BadwordEntity implements Entity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> words;
    @Column(name = "guild_id")
    private String guildId;

    public BadwordEntity() {}

    public BadwordEntity(String guildId) {
        this.guildId = guildId;
        words = new HashSet<>();
    }

    public void addWord(String word){
        words.add(word);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getWords() {
        return words;
    }

    public void setWords(Set<String> words) {
        this.words = words;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }
}
