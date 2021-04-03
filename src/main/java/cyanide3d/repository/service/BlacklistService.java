package cyanide3d.repository.service;


import cyanide3d.exceptions.IncorrectInputDataException;
import cyanide3d.repository.model.BlacklistEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BlacklistService extends AbstractHibernateService<Long, BlacklistEntity> {

    private static BlacklistService instance;

    public BlacklistService() {
        super(BlacklistEntity.class);
    }

    public void addToBlacklist(String name, String reason, String userId, String guildId) {
        create(new BlacklistEntity(name, userId, reason, guildId));
    }

    public Set<String> getBlacklistedUserIDs(String guildId) {
        return listByGuildId(guildId).stream()
                .map(BlacklistEntity::getUserId)
                .collect(Collectors.toSet());
    }

    public void deleteFromBlacklistByName(String name, String guildId) {
        final Optional<BlacklistEntity> blacklist = findOneByUsername(name, guildId);
        blacklist.ifPresent(this::delete);
    }

    public void deleteFromBlacklistById(String userId, String guildId) {
        final Optional<BlacklistEntity> blacklist = findOneByUserId(userId, guildId);
        blacklist.ifPresentOrElse(this::delete, () -> {
            throw new IncorrectInputDataException("Can't find user in blacklist.");
        });
    }

    public Optional<BlacklistEntity> findOneByUsername(String username, String guildId) {
        return findOneByField("name", username, guildId);
    }

    public Optional<BlacklistEntity> findOneByUserId(String userId, String guildId) {
        return findOneByField("userId", userId, guildId);
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
