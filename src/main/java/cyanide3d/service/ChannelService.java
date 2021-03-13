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

    private final String guildId;
    Logger logger = LoggerFactory.getLogger(ChannelService.class);

    public ChannelService(Class<ChannelEntity> entityClass, String guildId) {
        super(entityClass);
        this.guildId = guildId;
    }


    public TextChannel getEventChannel(JDA jda, ActionType type) {
        final Guild guild = jda.getGuildById(guildId);
        if (guild == null) {
            throw new IllegalStateException("Guild with provided id [" + guildId + "] does not exist!");
        }

        return findOneByAction(type)
                .map(ChannelEntity::getChannelId)
                .map(guild::getTextChannelById)
                .orElse(guild.getDefaultChannel());
    }

    public List<ChannelEntity> getChannelIDs() {
        return listByGuildId(guildId);
    }

    public boolean addChannel(String channelID, ActionType type) {
        create(new ChannelEntity(channelID, type.action(), guildId));
        return true;
    }

    public boolean changeChannel(String channelID, ActionType type) {
        return findOneByAction(type)
                .map(entity -> {
                    entity.setChannelId(channelID);
                    update(entity);
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteChannel(ActionType type) {
        return findOneByAction(type)
                .map(entity -> {
                    delete(entity);
                    return true;
                })
                .orElse(false);
    }

    private Optional<ChannelEntity> findOneByAction(ActionType type) {
        return findOneByField("action", type.action(), guildId);
    }

}
