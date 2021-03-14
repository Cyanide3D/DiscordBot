package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.AutoroleEntity;

import java.util.Map;

public class EmoteService extends DAO<String, AutoroleEntity> {

    private final String guildId;

    public EmoteService(Class<AutoroleEntity> entityClass, String guildId) {
        super(entityClass);
        this.guildId = guildId;
    }

    public void save(String messageID, Map<String, String> roles) {
        create(new AutoroleEntity(messageID, roles, guildId));
    }

    public String getRoleId(String messageID, String emote) {

        final AutoroleEntity entity = findOneByMessageId(messageID);
        if (entity == null)
            return null;

        return entity.getAutoroles().getOrDefault(emote, null);
    }

    private AutoroleEntity findOneByMessageId(String messageId) {
        return findOneByField("messageId", messageId, guildId).orElse(null);
    }

}
