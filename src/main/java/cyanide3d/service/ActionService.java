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

    public void setState(String action, boolean state) throws UnsupportedActionException {
        if (!AVAILABLE_ACTIONS.contains(action)) {
            throw new UnsupportedActionException(action);
        }

        ActionEntity entity = findOneByAction(action);

        if (entity == null) {
            create(new ActionEntity(state, action, guildId));
        } else {
            entity.setState(state);
            update(entity);
        }
    }

    public boolean getState(String action) {
        return findOneByAction(action).isState();
    }

    private ActionEntity findOneByAction(String action) {
        return findOneByField("action", action, guildId);
    }

    public List<ActionEntity> getActions() {
        return listByGuildId(guildId);
    }
}
