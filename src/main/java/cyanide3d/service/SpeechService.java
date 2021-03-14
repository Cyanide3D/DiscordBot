package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.BadwordEntity;

import java.util.*;


public class SpeechService extends DAO<Long, BadwordEntity> {

    private static SpeechService instance;

    public SpeechService(Class<BadwordEntity> entityClass) {
        super(entityClass);
    }

    public boolean isBad(String word, String guildId) {
        return findOneByGuild(guildId)
                .map(e -> e.getWords().contains(word))
                .orElse(false);
    }

    public Set<String> getBadWords(String guildId) {
        return findOneByGuild(guildId)
                .map(BadwordEntity::getWords)
                .orElseGet(Collections::emptySet);
    }

    public void add(String word, String guildId) {
        BadwordEntity entity = findOneByGuild(guildId).orElse(new BadwordEntity(guildId));
        entity.addWord(word);
        saveOrUpdate(entity);
    }

    public boolean remove(String word, String guildId) {
        return findOneByGuild(guildId)
                .map(e -> {
                    e.getWords().remove(word);
                    saveOrUpdate(e);
                    return true;
                }).orElse(false);
    }

    private Optional<BadwordEntity> findOneByGuild(String guildId) {
        return findOneByField("guildId", guildId, guildId);
    }

    public static SpeechService getInstance() {
        if (instance == null) {
            instance = new SpeechService(BadwordEntity.class);
        }
        return instance;
    }

}
