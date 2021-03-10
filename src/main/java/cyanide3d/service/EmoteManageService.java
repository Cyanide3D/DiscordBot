package cyanide3d.service;

import cyanide3d.dao.EmoteDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class EmoteManageService {
    private static EmoteManageService instance;
    private final EmoteDao dao;
    private final Map<String, Map<String, String>> state;
    private final Logger logger = LoggerFactory.getLogger(EmoteManageService.class);

    public EmoteManageService() {
        dao = new EmoteDao();
        state = dao.findAll();
        logger.info("Loading " + state.size() + " autorole messages.");
    }

    public void save(String messageID, Map<String, String> roles) {
        dao.save(messageID, roles);
        state.put(messageID, roles);
    }

    public String getRoleId(String messageID, String emote) {
        return state.containsKey(messageID)
                ? state.get(messageID).getOrDefault(emote, null)
                : null;
    }

    public static EmoteManageService getInstance() {
        if (instance == null) {
            instance = new EmoteManageService();
        }
        return instance;
    }
}
