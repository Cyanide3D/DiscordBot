package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.GreetingEntity;

import java.util.*;

public class GreetingService extends DAO<Long, GreetingEntity> {

    private static GreetingService instance;

    public GreetingService() {
        super(GreetingEntity.class);
    }

    public synchronized void add(String key, String message, String guildId) {
        findOneByGuildId(guildId).ifPresentOrElse(e -> {
            e.addMessage(key, message);
            update(e);
        }, () -> create(new GreetingEntity(guildId, key, message)));
    }

    public synchronized void delete(String key, String guildId) {
        GreetingEntity entity = findOneByGuildId(guildId).orElseThrow();
        entity.getMessages().remove(key);
        update(entity);
    }

    public synchronized Map<String, String> getAllForGuild(String guildId) {
        return findOneByGuildId(guildId)
                .map(GreetingEntity::getMessages)
                .orElseGet(HashMap::new);
    }

    public synchronized List<String> getAllMessagesForGuild(String guildId) {
        return findOneByGuildId(guildId)
                .map(e -> new ArrayList<>(e.getMessages().values()))
                .orElseGet(ArrayList::new);
    }

    private synchronized Optional<GreetingEntity> findOneByGuildId(String guildId) {
        return findOneByField("guildId", guildId, guildId);
    }

    public static GreetingService getInstance() {
        if (instance == null) {
            instance = new GreetingService();
        }
        return instance;
    }

}
