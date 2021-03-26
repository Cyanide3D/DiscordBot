package cyanide3d.handlers.listener.receivedmessage;

import cyanide3d.Localization;
import cyanide3d.exceptions.IncorrectInputDataException;
import cyanide3d.repository.service.ActionService;
import cyanide3d.repository.service.ChannelService;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.ActionType;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class StatementHandler implements ReceivedMessageHandler {
    private final Localization localization = Localization.getInstance();
    private final String[] fieldNames = {"Имя:", "Кол-во лет:", "Игровой ник:", "Средний онлайн:", "Ранг:", "Ссылка на ВК:", "Разница во времени от МСК:", "Пригласивший игрок:"};

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        ChannelService channels = ChannelService.getInstance();
        ActionService actionService = ActionService.getInstance();
        final TextChannel statementChannel = channels.getEventChannel(event.getJDA(), ActionType.STATEMENT, event.getGuild().getId());
        if (event.getAuthor().isBot() || !event.getChannel().equals(statementChannel) || PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId()) || !actionService.isActive(ActionType.STATEMENT, event.getGuild().getId())) {
            return;
        }

        event.getMessage().delete().queue();
        try {
            statementChannel
                    .sendMessage(getMessage(event.getMessage().getContentStripped().split("\n"), event))
                    .queue();
        } catch (IncorrectInputDataException e) {
            event.getChannel().sendMessage(localization.getMessage("event.request.join.malformed", event.getGuild().getOwner().getAsMention())).queue();
        }
    }

    private MessageEmbed getMessage(String[] lines, GuildMessageReceivedEvent event) throws IncorrectInputDataException {
        if (lines.length != 8) {
            throw new IncorrectInputDataException("Incorrect message length.");
        }
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setDescription(event.getAuthor().getAsMention())
                .setAuthor(event.getAuthor().getAsTag(), null, event.getAuthor().getAvatarUrl())
                .setTitle(localization.getMessage("event.join.request.title"))
                .setThumbnail(event.getAuthor().getAvatarUrl())
                .setImage("https://media3.giphy.com/media/WV4YdUfCxDfwA5MH0Q/giphy.gif?cid=ecf05e474fb24ae3998bcd07410214fdbc0ba947138f297a&rid=giphy.gif")
                .setFooter(localization.getMessage("event.join.request.footer"));

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (!line.startsWith(i + 1 + ".")) {
                throw new IncorrectInputDataException("Incorrect message signature.");
            }
            embedBuilder.addField(fieldNames[i], line.substring(2), false);
        }
        return embedBuilder.build();
    }

}