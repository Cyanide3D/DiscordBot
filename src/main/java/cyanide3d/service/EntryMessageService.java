package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.EntryMessageEntity;

import java.util.*;

public class EntryMessageService extends DAO<Long, EntryMessageEntity> {

    private static EntryMessageService instance;

    public EntryMessageService() {
        super(EntryMessageEntity.class);
    }

    public void add(String key, String message, String guildId) {
        findOneByGuildId(guildId).ifPresentOrElse(e -> {
            e.addMessage(key, message);
            update(e);
        }, () -> create(new EntryMessageEntity(guildId, key, message)));
    }

    public void delete(String key, String guildId) {
        findOneByGuildId(guildId).ifPresent(e -> {
            e.getMessages().remove(key);
            update(e);
        });
    }

    public Map<String, String> getAllForGuild(String guildId) {
        return findOneByGuildId(guildId)
                .map(EntryMessageEntity::getMessages)
                .orElseGet(HashMap::new);
    }

    public List<String> getAllMessagesForGuild(String guildId) {
        return findOneByGuildId(guildId)
                .map(e -> new ArrayList<>(e.getMessages().values()))
                .orElseGet(ArrayList::new);
    }

    private Optional<EntryMessageEntity> findOneByGuildId(String guildId) {
        return findOneByField("guildId", guildId, guildId);
    }

    public static EntryMessageService getInstance() {
        if (instance == null) {
            instance = new EntryMessageService();
        }
        return instance;
    }

}
