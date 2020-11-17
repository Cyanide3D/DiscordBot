package cyanide3d.service;

import cyanide3d.dao.EnableActionDao;
import cyanide3d.dao.MessageCacheDao;
import cyanide3d.model.Message;

public class MessageCacheService {
    MessageCacheDao dao;
    private static MessageCacheService instance;

    private MessageCacheService() {
        dao = new MessageCacheDao();
    }

    public Message getMessage(String id) {
        Message message = dao.get(id);
        if (dao.get(id) == null) {
            return new Message("1", "Unknown");
        }
        return message;
    }

    public void add(Message message) {
        dao.add(message);
    }

    public void delete(String id) {
        if (getMessage(id) != null)
            dao.delete(id);
    }

    public static MessageCacheService getInstance() {
        if (instance == null) instance = new MessageCacheService();
        return instance;
    }
}
