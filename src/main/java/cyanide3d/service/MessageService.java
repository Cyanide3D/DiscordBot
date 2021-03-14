package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.MessageEntity;
import cyanide3d.model.Message;

import java.util.Optional;

public class MessageService extends DAO<String, MessageEntity> {

    private final String guildId;

    public MessageService(Class<MessageEntity> entityClass, String guildId) {
        super(entityClass);
        this.guildId = guildId;
    }

    public void add(String messageId, String body) {
        create(new MessageEntity(messageId, body, guildId));
    }

    public void delete(String messageId) {
        delete(new MessageEntity(messageId));
    }

    public MessageEntity getMessageById(String messageId) {
        return findOneById(messageId)
                .orElse(null);
    }

    private Optional<MessageEntity> findOneById(String id) {
        return findOneByField("id", id, guildId);
    }

}
