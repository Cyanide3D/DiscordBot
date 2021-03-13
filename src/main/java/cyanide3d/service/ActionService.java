package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dao.old.ActionDao;
import cyanide3d.dto.ActionEntity;
import cyanide3d.exceprtion.UnsupportedActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ActionService extends DAO {
    Logger logger = LoggerFactory.getLogger(ActionService.class);
    private final static Set<String> AVAILABLE_ACTIONS = Set.of("joinleave", "blacklist", "joinform", "logging", "speechfilter", "answer", "vkdiscord");
    public static ActionService instance;
    Map<String, Boolean> actions;

    private ActionService() {
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
            create(new ActionEntity(enabled, action.toLowerCase()));
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
