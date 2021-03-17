package cyanide3d.handlers.listener.receivedmessage;

import cyanide3d.service.ActionService;
import cyanide3d.service.ChannelService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VacationHandler implements ReceivedMessageHandler{
    @Override
    public void execute(GuildMessageReceivedEvent event) {
        ChannelService channelService = ChannelService.getInstance();
        ActionService actionService = ActionService.getInstance();
        if (!actionService.isActive(ActionType.VACATION, event.getGuild().getId()) || !event.getChannel().equals(channelService.getEventChannel(event.getJDA(), ActionType.VACATION, event.getGuild().getId())) || event.getAuthor().isBot()) {
            return;
        }
        event.getChannel().sendMessage(makeMessage(event)).queue();
        event.getMessage().delete().queue();
    }

    private MessageEmbed makeMessage(GuildMessageReceivedEvent event) {
        return new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setTitle(":white_check_mark: ЗАЯВЛЕНИЕ НА ОТПУСК :white_check_mark:")
                .setDescription(":arrow_right:  От " + event.getAuthor().getAsMention() + "  :arrow_left:")
                .setThumbnail("https://media.giphy.com/media/l0IykRHhsuBYastNK/giphy.gif")
                .addField("Текст заявки:", event.getMessage().getContentRaw(), false)
                .addField("Дата заполнения:", new SimpleDateFormat("HH:mm | dd.MM.yyyy").format(new Date()), false)
                .build();
    }
}
