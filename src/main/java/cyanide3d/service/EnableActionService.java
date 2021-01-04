package cyanide3d.service;

import cyanide3d.conf.Logging;
import cyanide3d.dao.EnableActionDao;
import cyanide3d.exceprtion.UnsupportedActionException;
import cyanide3d.exceprtion.UnsupportedStateException;

import java.util.*;
import java.util.logging.Logger;

public class EnableActionService {
    Logger logger = Logging.getInstance().getLogger();
    private final static Set<String> AVAILABLE_ACTIONS = Set.of("joinleave", "blacklist", "joinform", "logging", "speechfilter", "answer");
    public static EnableActionService instance;
    Map<String, Boolean> actions;
    final EnableActionDao dao;

    private EnableActionService() {
        dao = new EnableActionDao();
        actions = dao.list();
    }

    public void setState(String action, boolean enabled) throws UnsupportedStateException, UnsupportedActionException {
        if (!AVAILABLE_ACTIONS.contains(action)) {
            logger.warning("EnableActionService.setState UnsupportedActionException");
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

    public static EnableActionService getInstance() {
        if (instance == null) instance = new EnableActionService();
        return instance;
    }

    public Map<String, Boolean> getActions() {
        return Collections.unmodifiableMap(actions);
    }
}
