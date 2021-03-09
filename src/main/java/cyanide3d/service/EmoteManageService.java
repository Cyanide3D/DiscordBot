package cyanide3d.service;

import cyanide3d.dao.EmoteDao;

import java.util.Map;

public class EmoteManageService {
    private static EmoteManageService instance;
    private final EmoteDao dao;
    private final Map<String, Map<String, String>> state;

    public EmoteManageService() {
        dao = new EmoteDao();
        state = dao.findAll();

    }

    public void save(String messageID, Map<String, String> roles) {
        dao.save(messageID, roles);
        state.put(messageID, roles);
    }

    public String getRole(String channelID, String emote) {
        if (state.containsKey(channelID)) {
            if (state.get(channelID).containsKey(emote)) {
                return state.get(channelID).get(emote);
            }
        }
        return null;
    }

    public static EmoteManageService getInstance() {
        if (instance == null) {
            instance = new EmoteManageService();
        }
        return instance;
    }
}
