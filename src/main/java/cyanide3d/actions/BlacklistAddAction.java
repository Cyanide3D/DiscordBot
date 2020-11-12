package cyanide3d.actions;

import cyanide3d.Localization;
import cyanide3d.service.BlackListService;
import cyanide3d.service.EnableActionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BlacklistAddAction implements Action {
    private final Localization localization = Localization.getInstance();
    private final GuildMessageReceivedEvent event;

    public BlacklistAddAction(Event event) {
        this.event = (GuildMessageReceivedEvent) event;
    }

    @Override
    public void execute() {
        EnableActionService enableActionService = EnableActionService.getInstance();
        if (!enableActionService.getState("blacklist")){
            return;
        }
        event.getMessage().delete().queue();

        String message = event.getMessage().getContentRaw();
        String nickname = StringUtils.substringBefore(message, "&");
        String reason = StringUtils.substringAfter(message, "&");

        BlackListService.getInstance().add(nickname.toLowerCase(), reason);

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
