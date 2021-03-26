package cyanide3d.repository.service;

import cyanide3d.repository.model.ActionEntity;
import cyanide3d.util.ActionType;

import java.util.List;
import java.util.Optional;

public class ActionService extends AbstractHibernateService<Long, ActionEntity> {

    private static ActionService instance;

    private ActionService() {
        super(ActionEntity.class);
    }

    public synchronized void enable(ActionType type, boolean enabled, String guildId) {
        findOneByAction(type.getName(), guildId).ifPresentOrElse(
                action -> {
                    action.setEnabled(enabled);
                    update(action);
                },
                () -> create(new ActionEntity(enabled, type.getName(), guildId)));
    }

    public synchronized boolean isActive(ActionType type, String guildId) {
        return findOneByAction(type.getName(), guildId)
                .map(ActionEntity::isEnabled)
                .orElse(false);
    }

    private synchronized Optional<ActionEntity> findOneByAction(String action, String guildId) {
        return findOneByField("action", action, guildId);
    }

    public synchronized List<ActionEntity> getActions(String guildId) {
        return listByGuildId(guildId);
    }

    public static ActionService getInstance() {
        if (instance == null) {
            instance = new ActionService();
        }
        return instance;
    }

}
