package cyanide3d.repository.service;


import cyanide3d.repository.model.BlacklistEntity;

import java.util.List;
import java.util.Optional;

public class BlacklistService extends AbstractHibernateService<Long, BlacklistEntity> {

    private static BlacklistService instance;

    public BlacklistService() {
        super(BlacklistEntity.class);
    }


    public synchronized void add(String name, String reason, String guildId) {
        create(new BlacklistEntity(name, reason, guildId));
    }

    public synchronized boolean delete(String name, String guildId) {
        final Optional<BlacklistEntity> blacklist = findOneByField("name", name, guildId);
        blacklist.ifPresent(this::delete);
        return blacklist.isPresent();
    }

    public synchronized BlacklistEntity findOneByUsername(String username, String guildId) {
        return findOneByField("name", username, guildId).orElse(null);
    }

    public synchronized List<BlacklistEntity> giveBlacklistedUsers(String guildId){
        return listByGuildId(guildId);
    }

    public static BlacklistService getInstance() {
        if (instance == null) {
            instance = new BlacklistService();
        }
        return instance;
    }

}
