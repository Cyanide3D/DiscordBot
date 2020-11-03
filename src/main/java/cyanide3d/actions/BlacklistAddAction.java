package cyanide3d.actions;

import cyanide3d.Localization;
import cyanide3d.service.BlackListService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.Locale;

public class BlacklistAddAction implements Action {
    private Localization localization = new Localization(new Locale("ru", "RU"));
    private final GuildMessageReceivedEvent event;

    public BlacklistAddAction(Event event) {
        this.event = (GuildMessageReceivedEvent) event;
    }

    @Override
    public void execute() {

        event.getMessage().delete().queue();

        String message = event.getMessage().getContentRaw();
        String nickname = StringUtils.substringBefore(message, "&");
        String reason = StringUtils.substringAfter(message, "&");

        if(message.startsWith("!")){BlackListService.getInstance().add(nickname.substring(1), reason);}

        MessageEmbed resultMessage = new EmbedBuilder()
                .setTitle(localization.getMessage("blacklist.title"))
                .addField(localization.getMessage("blacklist.nick"), nickname.substring(1), false)
                .setColor(Color.ORANGE)
                .addField(localization.getMessage("blacklist.reason"), reason, false)
                .setFooter(localization.getMessage("blacklist.form"))
                .setDescription(localization.getMessage("blacklist.add", nickname, event.getAuthor().getName()))
                .setThumbnail(event.getGuild().getIconUrl()).build();
        event.getChannel().sendMessage(resultMessage).queue();
    }
}
