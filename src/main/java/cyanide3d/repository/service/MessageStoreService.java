package cyanide3d.repository.service;

import cyanide3d.repository.model.MessageEntity;

import java.time.LocalDate;
import java.util.Optional;

public class MessageStoreService extends AbstractHibernateService<String, MessageEntity>{

    private static MessageStoreService instance;

    public MessageStoreService() {
        super(MessageEntity.class);
    }

    public void save(String messageId, String messageBody) {
        create(new MessageEntity(messageId, messageBody));
    }

    public String getMessageBodyById(String messageId) {
        return findOneById(messageId).map(MessageEntity::getBody).orElse("Unknown");
    }

    public void delete(String messageId) {
        findOneById(messageId).ifPresent(this::delete);
    }

    public void clean() {
        deleteOutdatedMessages();
    }

    private void deleteOutdatedMessages() {
        sessionFactory.inTransaction(session -> {
            LocalDate dateBeforeDelete = LocalDate.now().minusDays(10); //FIXME var name, my english is VERY WELL
            String query = "delete from MessageEntity where date<:date";
            session.createQuery(query)
                    .setParameter("date", dateBeforeDelete)
                    .executeUpdate();
        });
    }

    private Optional<MessageEntity> findOneById(String messageId) {
        return sessionFactory.fromSession(session -> {
            String query = "from MessageEntity where id=:id";
            return session.createQuery(query, MessageEntity.class)
                    .setParameter("id", messageId)
                    .uniqueResultOptional();
        });
    }

    public static MessageStoreService getInstance() {
        if (instance == null) {
            instance = new MessageStoreService();
        }
        return instance;
    }
}
