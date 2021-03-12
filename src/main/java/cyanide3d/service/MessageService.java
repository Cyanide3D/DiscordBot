package cyanide3d.service;

import cyanide3d.dao.MessageDao;
import cyanide3d.dao.RoleDao;
import cyanide3d.model.Message;
import cyanide3d.model.RoleUse;

import java.util.List;

public class MessageService {
    MessageDao messageDao;
    RoleDao roleDao;
    private static MessageService instance;

    private MessageService() {
        roleDao = new RoleDao();
        messageDao = new MessageDao();
    }

    public Message getMessage(String id) {
        Message message = messageDao.get(id);
        if (messageDao.get(id) == null) {
            return new Message("1", "Unknown");
        }
        return message;
    }

    public void add(Message message) {
        messageDao.add(message);
    }

    public void add(RoleUse roleUse) {
        RoleUse oldPost = roleCacheList().stream().filter(role ->
                role.getId().equalsIgnoreCase(roleUse.getId()) && role.getDate().equalsIgnoreCase(roleUse.getDate())).findAny().orElse(null);
        if (oldPost == null)
            roleDao.add(roleUse);
        else {
            oldPost.setCount(String.valueOf(Integer.parseInt(oldPost.getCount()) + 1));
            roleDao.update(oldPost);
        }
    }

    public void delete(String id) {
        if (getMessage(id) != null)
            messageDao.delete(id);
    }

    public List<RoleUse> roleCacheList(){
        return roleDao.list();
    }

    public static MessageService getInstance() {
        if (instance == null) instance = new MessageService();
        return instance;
    }
}
