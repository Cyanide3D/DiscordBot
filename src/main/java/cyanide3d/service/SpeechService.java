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
        return findOneByWord(word) != null;
    }

    public List<BadwordEntity> getBadWords() {
        return listByGuildId(guildId);
    }

    public boolean add(String word) {
        final BadwordEntity entity = findOneByWord(word);

        if (entity == null)
            return false;

        create(new BadwordEntity(word, guildId));
        return true;
    }

    public boolean remove(String word) {
        final BadwordEntity entity = findOneByWord(word);

        if (entity == null)
            return false;

        delete(entity);
        return true;
    }

    private BadwordEntity findOneByWord(String word) {
        return findOneByField("word", word, guildId).orElse(null);
    }
}
