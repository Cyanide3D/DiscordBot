package cyanide3d.actions;

import cyanide3d.Localization;
import cyanide3d.actions.Action;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.Locale;

public class JoinFormAction implements Action {
    private final Localization localization = new Localization(new Locale("ru", "RU"));
    private final GuildMessageReceivedEvent event;
    private final String[] fieldNames = {"Имя:", "Кол-во лет:", "Игровой ник:", "Средний онлайн:", "Ранг:", "Ссылка на ВК:", "Разница во времени от МСК:", "Пригласивший игрок:"};
    private final String messageText;

    public JoinFormAction(GuildMessageReceivedEvent event) {
        this.event = event;
        messageText = event.getMessage().getContentStripped();
    }

    @Override
    public void execute() {
        TextChannel channel = event.getChannel();
        TextChannel postChannel = event.getJDA().getTextChannelById("664814068036665354");
        String[] lines = messageText.split("\n");

        if (lines.length == 8) {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setColor(Color.ORANGE)
                    .setAuthor(event.getAuthor().getAsTag(), null, event.getAuthor().getAvatarUrl())
                    .setTitle(localization.getMessage("event.join.request.title"))
                    .setThumbnail(event.getAuthor().getAvatarUrl())
                    .setImage("https://media3.giphy.com/media/WV4YdUfCxDfwA5MH0Q/giphy.gif?cid=ecf05e474fb24ae3998bcd07410214fdbc0ba947138f297a&rid=giphy.gif");//FIXME вынести куда-нибудь
            // .setFooter(localization.getMessage("event.join.request.footer"));

            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                if (!line.startsWith(i + 1 + ".")) {
                    event.getMessage().delete().queue();
                    channel.sendMessage(localization.getMessage("event.request.join.malformed", event.getGuild().getOwner().getAsMention())).queue();
                    return;
                }
                embedBuilder.addField(fieldNames[i], line.substring(2), false);
            }
            event.getMessage().delete().queue();
            postChannel.sendMessage(embedBuilder.build()).queue();
        } else {
            event.getMessage().delete().queue();
            channel.sendMessage(localization.getMessage("event.request.join.malformed", event.getGuild().getOwner().getAsMention())).queue();
        }
    }
}
