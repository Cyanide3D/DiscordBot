package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.AutoroleEntity;

import java.util.Map;
import java.util.Optional;

public class EmoteService extends DAO<String, AutoroleEntity> {

    private static EmoteService instance;

    public EmoteService() {
        super(AutoroleEntity.class);
    }

    public synchronized void save(String messageID, Map<String, String> roles, String guildId) {
        create(new AutoroleEntity(messageID, roles, guildId));
    }

    public synchronized String getRoleId(String messageID, String emote, String guildId) {
        return getByMessageId(messageID, guildId)
                .map(AutoroleEntity::getAutoroles)
                .map(roles -> roles.get(emote))
                .orElse(null);
    }

    private synchronized Optional<AutoroleEntity> getByMessageId(String messageId, String guildId) {
        return findOneByField("id", messageId, guildId);
    }

    public static EmoteService getInstance() {
        if (instance == null) {
            instance = new EmoteService();
        }
        return instance;
    }

}
