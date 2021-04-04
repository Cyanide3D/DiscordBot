package cyanide3d.handlers.listener.receivedmessage;

import cyanide3d.Localization;
import cyanide3d.exceptions.IncorrectInputDataException;
import cyanide3d.repository.service.ActionService;
import cyanide3d.repository.service.ChannelService;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.ActionType;
import cyanide3d.util.DefaultAlertMessages;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class StatementHandler implements ReceivedMessageHandler {
    private final Localization localization;
    private final ChannelService channels;
    private final ActionService actionService;
    private final String[] fieldNames;
    private String[] lines;

    public StatementHandler() {
        localization = Localization.getInstance();
        channels = ChannelService.getInstance();
        actionService = ActionService.getInstance();
        fieldNames = localization.getMessage("event.join.request.field.titles").split("\n");
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        final TextChannel statementChannel = channels.getEventChannel(event.getJDA(), ActionType.STATEMENT, event.getGuild().getId());
        if (event.getAuthor().isBot() || !event.getChannel().equals(statementChannel) || PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId()) || !actionService.isActive(ActionType.STATEMENT, event.getGuild().getId())) {
            return;
        }

        event.getMessage().delete().queue();
        sendMessage(event, statementChannel);
    }

    private void sendMessage(GuildMessageReceivedEvent event, TextChannel channel) {
        try {
            lines = event.getMessage().getContentStripped().split("\n");

            if (lines.length != 8) {
                throw new IncorrectInputDataException("Incorrect message length.");
            }

            channel.sendMessage(getMessage(event)).queue();
        } catch (IncorrectInputDataException e) {
            event.getChannel().sendMessage(localization.getMessage("event.join.request.malformed", event.getGuild().getOwner().getAsMention())).queue();
        }
    }

    private MessageEmbed getMessage(GuildMessageReceivedEvent event) {
        EmbedBuilder builder = getEmbedTemplate(event);
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            builder.addField(fieldNames[i], getFormattedLine(i + 1, line), false);
        }
        return builder.build();
    }

    private String getFormattedLine(int lineNumber, String line) {
        if (isNeedFormat(line, lineNumber)) {
            line = line.substring(2);
        }

        return line;
    }

    private boolean isNeedFormat(String line, int lineNumber) {
        return StringUtils.startsWith(line, lineNumber + ".")
                || StringUtils.startsWith(line, lineNumber + ")")
                || StringUtils.startsWith(line, lineNumber + " ");
    }

    private EmbedBuilder getEmbedTemplate(GuildMessageReceivedEvent event) {
        return new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setDescription(event.getAuthor().getAsMention())
                .setAuthor(event.getAuthor().getAsTag(), null, event.getAuthor().getAvatarUrl())
                .setTitle(localization.getMessage("event.join.request.title"))
                .setThumbnail(event.getAuthor().getAvatarUrl())
                .setImage(DefaultAlertMessages.getStatementEventImage())
                .setFooter(localization.getMessage("event.join.request.footer"));
    }

}