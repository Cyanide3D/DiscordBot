package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.ActionEntity;
import cyanide3d.exceprtion.UnsupportedActionException;
import cyanide3d.util.ActionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ActionService extends DAO<Long, ActionEntity> {
    private final String guildId;

    public ActionService(Class<ActionEntity> entityClass, String guildId) {
        super(entityClass);
        this.guildId = guildId;
    }

    public void enable(ActionType type, boolean enabled) {
        findOneByAction(type.action()).ifPresentOrElse(
                action -> {
                    action.setEnabled(enabled);
                    update(action);
                },
                () -> create(new ActionEntity(enabled, type.action(), guildId)));
    }

    public boolean isActive(ActionType type) {
        return findOneByAction(type.action())
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
