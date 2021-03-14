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
        return findOneByWord(word).isPresent();
    }

    public List<BadwordEntity> getBadWords() {
        return listByGuildId(guildId);
    }

    public void add(String word) {
        BadwordEntity entity = findOneByWord(word).orElse(new BadwordEntity(guildId));
        entity.addWord(word);
        saveOrUpdate(entity);
    }

    public boolean remove(String word) {
        return findOneByWord(word)
                .map(e -> {
                    delete(e);
                    return true;
                }).orElse(false);
    }

    private Optional<BadwordEntity> findOneByWord(String word) {
        return findOneByField("word", word, guildId);
    }
}
