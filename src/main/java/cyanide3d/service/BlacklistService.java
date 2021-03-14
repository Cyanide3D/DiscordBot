package cyanide3d.service;


import cyanide3d.dao.DAO;
import cyanide3d.dto.BlacklistEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BlacklistService extends DAO<Long, BlacklistEntity> {

    private final Logger logger = LoggerFactory.getLogger(EmoteService.class);
    private final String guildId;

    public BlacklistService(Class<BlacklistEntity> entityClass, String guildId) {
        super(entityClass);
        this.guildId = guildId;
    }


    public void add(String name, String reason) {
        create(new BlacklistEntity(name, reason, guildId));
    }
    public boolean delete(String name) {
        return findOneByField("name", name, guildId)
                .map(entity -> {
                    delete(entity);
                    return true;
                })
                .orElse(false);
    }

    public BlacklistEntity findOneByUsername(String username) {
        return findOneByField("name", username, guildId).orElse(null);
    }

    public List<BlacklistEntity> giveBlacklistedUsers(){
        return listByGuildId(guildId);
    }
}
