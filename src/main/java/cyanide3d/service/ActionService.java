package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.ActionEntity;
import cyanide3d.exceprtion.UnsupportedActionException;
import cyanide3d.util.ActionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ActionService extends DAO<Long, ActionEntity> {

    private static ActionService instance;

    public ActionService(Class<ActionEntity> entityClass) {
        super(entityClass);
    }

    public void enable(ActionType type, boolean enabled, String guildId) {
        findOneByAction(type.action(), guildId).ifPresentOrElse(
                action -> {
                    action.setEnabled(enabled);
                    update(action);
                },
                () -> create(new ActionEntity(enabled, type.action(), guildId)));
    }

    public boolean isActive(ActionType type, String guildId) {
        return findOneByAction(type.action(), guildId)
                .map(ActionEntity::isEnabled)
                .orElse(false);
    }

    private Optional<ActionEntity> findOneByAction(String action, String guildId) {
        return findOneByField("action", action, guildId);
    }

    public List<ActionEntity> getActions(String guildId) {
        return listByGuildId(guildId);
    }

    public static ActionService getInstance() {
        if (instance == null) {
            instance = new ActionService(ActionEntity.class);
        }
        return instance;
    }

}
