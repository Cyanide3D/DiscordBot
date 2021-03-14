package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.AutoroleEntity;

import java.util.Map;

public class EmoteService extends DAO<String, AutoroleEntity> {

    private static EmoteService instance;

    public EmoteService(Class<AutoroleEntity> entityClass) {
        super(entityClass);
    }

    public void save(String messageID, Map<String, String> roles, String guildId) {
        create(new AutoroleEntity(messageID, roles, guildId));
    }

    public String getRoleId(String messageID, String emote, String guildId) {

        final AutoroleEntity entity = findOneByMessageId(messageID, guildId);
        if (entity == null)
            return null;

        return entity.getAutoroles().getOrDefault(emote, null);
    }

    private AutoroleEntity findOneByMessageId(String messageId, String guildId) {
        return findOneByField("messageId", messageId, guildId).orElse(null);
    }

    public static EmoteService getInstance() {
        if (instance == null) {
            instance = new EmoteService(AutoroleEntity.class);
        }
        return instance;
    }

}
