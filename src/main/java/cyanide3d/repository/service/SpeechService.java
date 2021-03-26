package cyanide3d.repository.service;

import cyanide3d.repository.model.BadwordEntity;

import java.util.*;


public class SpeechService extends AbstractHibernateService<Long, BadwordEntity> {

    private static SpeechService instance;

    public SpeechService() {
        super(BadwordEntity.class);
    }

    public synchronized boolean isBad(String word, String guildId) {
        return findOneByGuild(guildId)
                .map(e -> e.getWords().contains(word))
                .orElse(false);
    }

    public synchronized Set<String> getBadWords(String guildId) {
        return findOneByGuild(guildId)
                .map(BadwordEntity::getWords)
                .orElseGet(Collections::emptySet);
    }

    public synchronized void add(String word, String guildId) {
        BadwordEntity entity = findOneByGuild(guildId).orElse(new BadwordEntity(guildId));
        entity.addWord(word);
        saveOrUpdate(entity);
    }

    public synchronized boolean remove(String word, String guildId) {
        final Optional<BadwordEntity> words = findOneByGuild(guildId);
        words.ifPresent( e-> {
            e.getWords().remove(word);
            saveOrUpdate(e);
        });
        return words.isPresent();
    }

    private synchronized Optional<BadwordEntity> findOneByGuild(String guildId) {
        return findOneByField("guildId", guildId, guildId);
    }

    public static SpeechService getInstance() {
        if (instance == null) {
            instance = new SpeechService();
        }
        return instance;
    }

}
