package cyanide3d.repository.service;

import cyanide3d.repository.model.AutoroleEntity;

import java.util.Map;
import java.util.Optional;

public class AutoroleService extends AbstractHibernateService<String, AutoroleEntity> {

    private static AutoroleService instance;

    public AutoroleService() {
        super(AutoroleEntity.class);
    }

    public void save(String messageID, Map<String, String> roles, String guildId) {
        create(new AutoroleEntity(messageID, roles, guildId));
    }

    public String getRoleId(String messageID, String emote, String guildId) {
        return getByMessageId(messageID, guildId)
                .map(AutoroleEntity::getAutoroles)
                .map(roles -> roles.get(emote))
                .orElse(null);
    }

    private Optional<AutoroleEntity> getByMessageId(String messageId, String guildId) {
        return findOneByField("id", messageId, guildId);
    }

    public static AutoroleService getInstance() {
        if (instance == null) {
            instance = new AutoroleService();
        }
        return instance;
    }

}
