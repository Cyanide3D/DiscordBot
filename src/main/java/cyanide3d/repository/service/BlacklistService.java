package cyanide3d.repository.service;


import cyanide3d.repository.model.BlacklistEntity;

import java.util.List;
import java.util.Optional;

public class BlacklistService extends AbstractHibernateService<Long, BlacklistEntity> {

    private static BlacklistService instance;

    public BlacklistService() {
        super(BlacklistEntity.class);
    }


    public void addToBlacklist(String name, String reason, String guildId) {
        create(new BlacklistEntity(name, reason, guildId));
    }

    public boolean deleteFromBlacklist(String name, String guildId) {
        final Optional<BlacklistEntity> blacklist = findOneByField("name", name, guildId);
        blacklist.ifPresent(this::delete);
        return blacklist.isPresent();
    }

    public BlacklistEntity findOneByUsername(String username, String guildId) {
        return findOneByField("name", username, guildId).orElse(null);
    }

    public List<BlacklistEntity> giveBlacklistedUsers(String guildId){
        return listByGuildId(guildId);
    }

    public static BlacklistService getInstance() {
        if (instance == null) {
            instance = new BlacklistService();
        }
        return instance;
    }

}
