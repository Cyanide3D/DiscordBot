package cyanide3d.service;


import cyanide3d.dao.DAO;
import cyanide3d.dto.BlacklistEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BlacklistService extends DAO<Long, BlacklistEntity> {

    private static BlacklistService instance;

    public BlacklistService(Class<BlacklistEntity> entityClass) {
        super(entityClass);
    }


    public synchronized void add(String name, String reason, String guildId) {
        create(new BlacklistEntity(name, reason, guildId));
    }

    public synchronized boolean delete(String name, String guildId) {
        return findOneByField("name", name, guildId)
                .map(entity -> {
                    delete(entity);
                    return true;
                })
                .orElse(false);
    }

    public synchronized BlacklistEntity findOneByUsername(String username, String guildId) {
        return findOneByField("name", username, guildId).orElse(null);
    }

    public synchronized List<BlacklistEntity> giveBlacklistedUsers(String guildId){
        return listByGuildId(guildId);
    }

    public static BlacklistService getInstance() {
        if (instance == null) {
            instance = new BlacklistService(BlacklistEntity.class);
        }
        return instance;
    }

}
