package cyanide3d.service;


import cyanide3d.dao.BlacklistDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BlackListService {

    private static BlackListService instance;
    private final Logger logger = LoggerFactory.getLogger(EmoteManageService.class);
    private final BlacklistDao dao;
    final Map<String, String> blackListedUsers;

    private BlackListService() {
        dao = new BlacklistDao();
        blackListedUsers = dao.giveAll();
        logger.info("Loading " + blackListedUsers.size() + " blacklisted users");
    }

    public static BlackListService getInstance() {
        if (instance == null) {
            instance = new BlackListService();
        }
        return instance;
    }

    public void add(String nickname, String reason) {
        if (blackListedUsers.containsKey(nickname)) return;
        dao.add(nickname,reason);
        blackListedUsers.put(nickname,reason);
    }
    public void delete(String nickname) {
        if (!blackListedUsers.containsKey(nickname)) return;
        dao.delete(nickname);
        blackListedUsers.remove(nickname);
    }
    public Map<String,String> giveBlacklistedUsers(){
        return blackListedUsers;
    }
}
