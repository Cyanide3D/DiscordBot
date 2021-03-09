package cyanide3d.service;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Role;

import java.util.HashMap;
import java.util.Map;

public class EmoteManageService {
    private static EmoteManageService instance;
    Map<String, Map<Emote, Role>> state = new HashMap<>();

    public EmoteManageService() {
        //TODO STATE WARM UP
    }

    public void save(String messageID, Map<Emote, Role> roles) {
        //TODO DAO
        state.put(messageID, roles);
    }

    public Role getRole(String channelID, Emote emote) {
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
