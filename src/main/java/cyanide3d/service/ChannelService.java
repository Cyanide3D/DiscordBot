package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.ChannelEntity;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ChannelService extends DAO<Long, ChannelEntity> {


    private static ChannelService instance;

    public ChannelService(Class<ChannelEntity> entityClass) {
        super(entityClass);
    }


    public synchronized TextChannel getEventChannel(JDA jda, ActionType type, String guildId) {
        final Guild guild = jda.getGuildById(guildId);
        if (guild == null) {
            throw new IllegalStateException("Guild with provided id [" + guildId + "] does not exist!");
        }

        return findOneByAction(type, guildId)
                .map(ChannelEntity::getChannelId)
                .map(guild::getTextChannelById)
                .orElse(guild.getDefaultChannel());
    }

    public synchronized List<ChannelEntity> getChannelIDs(String guildId) {
        return listByGuildId(guildId);
    }

    public synchronized boolean addChannel(String channelID, ActionType type, String guildId) {
        create(new ChannelEntity(channelID, type.action(), guildId));
        return true;
    }

    public synchronized boolean changeChannel(String channelID, ActionType type, String guildId) {
        return findOneByAction(type, guildId)
                .map(entity -> {
                    entity.setChannelId(channelID);
                    update(entity);
                    return true;
                })
                .orElse(false);
    }

    public synchronized boolean deleteChannel(ActionType type, String guildId) {
        return findOneByAction(type, guildId)
                .map(entity -> {
                    delete(entity);
                    return true;
                })
                .orElse(false);
    }

    private synchronized Optional<ChannelEntity> findOneByAction(ActionType type, String guildId) {
        return findOneByField("action", type.action(), guildId);
    }

    public static ChannelService getInstance() {
        if (instance == null) {
            instance = new ChannelService(ChannelEntity.class);
        }
        return instance;
    }

}
