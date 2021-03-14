package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.ActionEntity;
import cyanide3d.exceprtion.UnsupportedActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ActionService extends DAO<Long, ActionEntity> {
    Logger logger = LoggerFactory.getLogger(ActionService.class);
    private final static Set<String> AVAILABLE_ACTIONS = Set.of("joinleave", "blacklist", "joinform", "logging", "speechfilter", "answer", "vkdiscord");
    private final String guildId;

    public ActionService(Class<ActionEntity> entityClass, String guildId) {
        super(entityClass);
        this.guildId = guildId;
    }

    public void setState(String actionName, boolean enabled) throws UnsupportedActionException {
        if (!AVAILABLE_ACTIONS.contains(actionName)) {
            throw new UnsupportedActionException(actionName);
        }

        findOneByAction(actionName).ifPresentOrElse(
                action -> {
                    action.setEnabled(enabled);
                    update(action);
                },
                () -> create(new ActionEntity(enabled, actionName, guildId)));
    }

    public boolean getState(String action) {
        return findOneByAction(action)
                .map(ActionEntity::isEnabled)
                .orElse(false);
    }

    private Optional<ActionEntity> findOneByAction(String action) {
        return findOneByField("action", action, guildId);
    }

    public List<ActionEntity> getActions() {
        return listByGuildId(guildId);
    }
}
