package cyanide3d.service;

import cyanide3d.dao.ActionDao;
import cyanide3d.exceprtion.UnsupportedActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ActionService {
    Logger logger = LoggerFactory.getLogger(ActionService.class);
    private final static Set<String> AVAILABLE_ACTIONS = Set.of("joinleave", "blacklist", "joinform", "logging", "speechfilter", "answer", "vkdiscord");
    public static ActionService instance;
    Map<String, Boolean> actions;
    final ActionDao dao;

    private ActionService() {
        dao = new ActionDao();
        actions = dao.list();
    }

    public void setState(String action, boolean enabled) throws UnsupportedActionException {
        if (!AVAILABLE_ACTIONS.contains(action)) {
            logger.error("Unsupported function to enable [" + action + "]");
            throw new UnsupportedActionException(action);
        }

        if (actions.containsKey(action)) {
            dao.update(action.toLowerCase(), enabled);
        } else {
            dao.create(action.toLowerCase(), enabled);
        }

        actions.put(action, enabled);
    }

    public boolean getState(String action) {
        return actions.getOrDefault(action, false);
    }

    public static ActionService getInstance() {
        if (instance == null) instance = new ActionService();
        return instance;
    }

    public Map<String, Boolean> getActions() {
        return Collections.unmodifiableMap(actions);
    }
}
