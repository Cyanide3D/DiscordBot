package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.MessageEntity;

import java.util.Optional;

public class MessageService extends DAO<String, MessageEntity> {

    private static MessageService instance;

    public MessageService(Class<MessageEntity> entityClass) {
        super(entityClass);
    }

    public void add(String messageId, String body, String guildId) {
        create(new MessageEntity(messageId, body, guildId));
    }

    public void delete(String messageId) {
        delete(new MessageEntity(messageId));
    }

    public MessageEntity getMessageById(String messageId, String guildId) {
        return findOneById(messageId, guildId)
                .orElse(null);
    }

    private Optional<MessageEntity> findOneById(String id, String guildId) {
        return findOneByField("id", id, guildId);
    }

    public static MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService(MessageEntity.class);
        }
        return instance;
    }

}
