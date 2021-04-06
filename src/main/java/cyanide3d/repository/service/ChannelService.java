package cyanide3d.repository.service;

import cyanide3d.repository.model.ChannelEntity;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Collectors;

public class ChannelService extends AbstractHibernateService<Long, ChannelEntity> {


    private static ChannelService instance;
    private JDA jda;

    public ChannelService() {
        super(ChannelEntity.class);
    }

    public TextChannel getEventChannelOrDefault(ActionType type, String guildId) {
        final Guild guild = Optional.ofNullable(jda.getGuildById(guildId))
                .orElseThrow(IllegalStateException::new);

        return findOneByAction(type, guildId)
                .map(ChannelEntity::getChannelId)
                .map(guild::getTextChannelById)
                .orElse(guild.getDefaultChannel());
    }

    public void addChannel(String channelID, ActionType type, String guildId) {
        findOneByAction(type, guildId).ifPresentOrElse(
                e -> changeChannel(channelID, type, guildId),
                () -> create(new ChannelEntity(channelID, type.getName(), guildId))
        );
    }

    public void changeChannel(String channelID, ActionType type, String guildId) {
        findOneByAction(type, guildId).ifPresent(entity -> {
            entity.setChannelId(channelID);
            update(entity);
        });
    }

    public String getChannelsAsString(Guild guild) {
        return listByGuildId(guild.getId()).stream()
                .filter(e -> isNonNullChannel(e, guild))
                .map(e -> formattedObject(guild, e))
                .collect(Collectors.joining("\n"));
    }

    private String formattedObject(Guild guild, ChannelEntity e) {
        return String.format("**%s** - `#%s`", StringUtils.substringBefore(e.getAction(), "_event").toUpperCase(), guild.getTextChannelById(e.getChannelId()).getName());
    }

    private boolean isNonNullChannel(ChannelEntity entity, Guild guild) {
        return guild.getTextChannelById(entity.getChannelId()) != null;
    }

    private Optional<ChannelEntity> findOneByAction(ActionType type, String guildId) {
        return findOneByField("action", type.getName(), guildId);
    }

    public void setJda(JDA jda) {
        this.jda = jda;
    }

    public static ChannelService getInstance() {
        if (instance == null) {
            instance = new ChannelService();
        }
        return instance;
    }

}
