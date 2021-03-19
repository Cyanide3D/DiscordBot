package cyanide3d.handlers.listener.receivedmessage;

import cyanide3d.Localization;
import cyanide3d.service.ActionService;
import cyanide3d.service.BlacklistService;
import cyanide3d.service.ChannelService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BlacklistHandler implements ReceivedMessageHandler {

    private final Localization localization = Localization.getInstance();


    @Override
    public void execute(GuildMessageReceivedEvent event) {
        ChannelService channels = ChannelService.getInstance();
        ActionService actionService = ActionService.getInstance();
        final TextChannel blacklistChannel = channels.getEventChannel(event.getJDA(), ActionType.BLACKLIST, event.getGuild().getId());
        if (event.getAuthor().isBot() || !event.getChannel().equals(blacklistChannel) || !actionService.isActive(ActionType.BLACKLIST, event.getGuild().getId())) {
            return;
        }
        event.getMessage().delete().queue();

        String message = event.getMessage().getContentRaw();
        String nickname = StringUtils.substringBefore(message, "&");
        String reason = StringUtils.substringAfter(message, "&");

        BlacklistService.getInstance().add(nickname.toLowerCase(), reason, event.getGuild().getId());

        MessageEmbed resultMessage = new EmbedBuilder()
                .setTitle(localization.getMessage("blacklist.title"))
                .addField(localization.getMessage("blacklist.nick"), nickname, false)
                .setColor(Color.ORANGE)
                .addField(localization.getMessage("blacklist.reason"), reason, false)
                .addField("Дата добавления:", new SimpleDateFormat("dd-MM-yyyy").format(new Date()),false)
                .setFooter(localization.getMessage("blacklist.form"))
                .setDescription(localization.getMessage("blacklist.add", event.getMember().getNickname(), event.getAuthor().getName()))
                .setThumbnail(event.getGuild().getIconUrl()).build();
        event.getChannel().sendMessage(resultMessage).queue();
    }
}
