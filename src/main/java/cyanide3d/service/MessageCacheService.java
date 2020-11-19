package cyanide3d.service;

import cyanide3d.dao.MessageCacheDao;
import cyanide3d.dao.RoleUseCacheDao;
import cyanide3d.model.Message;
import cyanide3d.model.RoleUse;

import java.util.List;

public class MessageCacheService {
    MessageCacheDao messageCacheDao;
    RoleUseCacheDao roleUseCacheDao;
    private static MessageCacheService instance;

    private MessageCacheService() {
        roleUseCacheDao = new RoleUseCacheDao();
        messageCacheDao = new MessageCacheDao();
    }

    public Message getMessage(String id) {
        Message message = messageCacheDao.get(id);
        if (messageCacheDao.get(id) == null) {
            return new Message("1", "Unknown");
        }
        return message;
    }

    public void add(Message message) {
        messageCacheDao.add(message);
    }

    public void add(RoleUse roleUse) {
        RoleUse oldPost = roleCacheList().stream().filter(role ->
                role.getId().equalsIgnoreCase(roleUse.getId()) && role.getDate().equalsIgnoreCase(roleUse.getDate())).findAny().orElse(null);
        if (oldPost == null)
            roleUseCacheDao.add(roleUse);
        else {
            oldPost.setCount(String.valueOf(Integer.parseInt(oldPost.getCount()) + 1));
            roleUseCacheDao.update(oldPost);
        }
    }

    public void delete(String id) {
        if (getMessage(id) != null)
            messageCacheDao.delete(id);
    }

    public List<RoleUse> roleCacheList(){
        return roleUseCacheDao.list();
    }

    public static MessageCacheService getInstance() {
        if (instance == null) instance = new MessageCacheService();
        return instance;
    }
}
