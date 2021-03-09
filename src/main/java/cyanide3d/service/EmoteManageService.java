package cyanide3d.service;

import cyanide3d.dao.EmoteDao;
import net.dv8tion.jda.api.entities.Role;

import java.util.Map;

public class EmoteManageService {
    private static EmoteManageService instance;
    private final EmoteDao dao;
    private final Map<String, Map<String, Role>> state;

    public EmoteManageService() {
        dao = new EmoteDao();
        state = dao.findAll();

    }

    public void save(String messageID, Map<String, Role> roles) {
        //TODO DAO
        state.put(messageID, roles);
    }

    public Role getRole(String channelID, String emote) {
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
