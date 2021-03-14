package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.BadwordEntity;

import java.util.*;


public class SpeechService extends DAO<Long, BadwordEntity> {

    private final String guildId;

    public SpeechService(Class<BadwordEntity> entityClass, String guildId) {
        super(entityClass);
        this.guildId = guildId;
    }

    public boolean isBad(String word) {
        return findOneByGuild()
                .map(e -> e.getWords().contains(word))
                .orElse(false);
    }

    public Set<String> getBadWords() {
        return findOneByGuild()
                .map(BadwordEntity::getWords)
                .orElseGet(Collections::emptySet);
    }

    public void add(String word) {
        BadwordEntity entity = findOneByGuild().orElse(new BadwordEntity(guildId));
        entity.addWord(word);
        saveOrUpdate(entity);
    }

    public boolean remove(String word) {
        return findOneByGuild()
                .map(e -> {
                    e.getWords().remove(word);
                    saveOrUpdate(e);
                    return true;
                }).orElse(false);
    }

    private Optional<BadwordEntity> findOneByGuild() {
        return findOneByField("guildId", guildId, guildId);
    }
}
