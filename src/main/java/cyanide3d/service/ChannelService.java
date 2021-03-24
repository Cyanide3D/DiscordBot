package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.ChannelEntity;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChannelService extends DAO<Long, ChannelEntity> {


    private static ChannelService instance;

    public ChannelService() {
        super(ChannelEntity.class);
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

    public synchronized void addChannel(String channelID, ActionType type, String guildId) {
        findOneByAction(type, guildId).ifPresentOrElse(
                e -> changeChannel(channelID, type, guildId),
                () -> create(new ChannelEntity(channelID, type.getName(), guildId))
        );
    }

    public synchronized void changeChannel(String channelID, ActionType type, String guildId) {
        final Optional<ChannelEntity> action = findOneByAction(type, guildId);
        action.ifPresent(entity -> {
            entity.setChannelId(channelID);
            update(entity);
        });
    }

    public synchronized String getChannelsWithAction(Guild guild) {
        return listByGuildId(guild.getId()).stream()
                .filter(e -> isNullableChannel(e, guild))
                .map(e -> String.format("**%s** - `#%s`", e.getAction(), guild.getTextChannelById(e.getChannelId()).getName()))
                .collect(Collectors.joining("\n"));
    }

    private synchronized boolean isNullableChannel(ChannelEntity entity, Guild guild) {
        return guild.getTextChannelById(entity.getChannelId()) != null;
    }

    public synchronized void deleteChannel(ActionType type, String guildId) {
        final Optional<ChannelEntity> action = findOneByAction(type, guildId);
        action.ifPresent(this::delete);
    }

    private synchronized Optional<ChannelEntity> findOneByAction(ActionType type, String guildId) {
        return findOneByField("action", type.getName(), guildId);
    }

    public static ChannelService getInstance() {
        if (instance == null) {
            instance = new ChannelService();
        }
        return instance;
    }

}
