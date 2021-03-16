package cyanide3d.handlers.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VacationHandler {
    private final GuildMessageReceivedEvent event;

    public VacationHandler(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    public void handle() {
        event.getChannel().sendMessage(makeMessage()).queue();
        event.getMessage().delete().queue();
    }

    private MessageEmbed makeMessage() {
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
