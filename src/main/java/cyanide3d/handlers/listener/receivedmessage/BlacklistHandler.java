package cyanide3d.handlers.listener.receivedmessage;

import cyanide3d.Localization;
import cyanide3d.repository.service.ActionService;
import cyanide3d.repository.service.BlacklistService;
import cyanide3d.repository.service.ChannelService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.ObjectUtils;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BlacklistHandler implements ReceivedMessageHandler {
    private final Localization localization;
    private final BlacklistService blacklistService;
    private final ChannelService channels;
    private final ActionService actionService;

    public BlacklistHandler() {
        localization = Localization.getInstance();
        actionService = ActionService.getInstance();
        channels = ChannelService.getInstance();
        blacklistService = BlacklistService.getInstance();
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("&");

        if (isAbort(event, args)) {
            return;
        }
        event.getMessage().delete().queue();

        String nickname = args[0];
        String reason = args[1];

        EmbedBuilder builder = new EmbedBuilder();
        fillEmbedMessage(builder, event, nickname, reason);

        if (args.length == 3) {
            String userId = args[2];
            builder.addField(localization.getMessage("blacklist.id"), userId, false);
            blacklistService.addToBlacklist(nickname, reason, userId, event.getGuild().getId());
        }

        event.getChannel().sendMessage(builder.build()).queue();
    }

    private boolean isAbort(GuildMessageReceivedEvent event, String[] args) {
        final TextChannel blacklistChannel = channels.getEventChannel(event.getJDA(), ActionType.BLACKLIST, event.getGuild().getId());
        return event.getAuthor().isBot()
                || !event.getChannel().equals(blacklistChannel)
                || !actionService.isActive(ActionType.BLACKLIST, event.getGuild().getId())
                || args.length < 2;
    }

    private void fillEmbedMessage(EmbedBuilder builder, GuildMessageReceivedEvent event, String nickname, String reason) {
        builder
                .setTitle(localization.getMessage("blacklist.title"))
                .addField(localization.getMessage("blacklist.nick"), nickname, false)
                .setColor(Color.ORANGE)
                .addField(localization.getMessage("blacklist.reason"), reason, false)
                .addField(localization.getMessage("blacklist.date"), new SimpleDateFormat("dd.MM.yyyy").format(new Date()), false)
                .setFooter(localization.getMessage("blacklist.form"))
                .setDescription(localization.getMessage("blacklist.add", ObjectUtils.defaultIfNull(event.getMember().getNickname(), event.getAuthor().getName())))
                .setThumbnail(event.getGuild().getIconUrl()).build();
    }
}
