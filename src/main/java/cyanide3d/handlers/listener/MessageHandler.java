package cyanide3d.handlers.listener;

import com.jagrosh.jdautilities.command.CommandClient;
import cyanide3d.actions.*;
import cyanide3d.listener.CommandClientManager;
import cyanide3d.service.ActionService;
import cyanide3d.service.ChannelService;
import cyanide3d.service.PermissionService;
import cyanide3d.util.ActionType;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

public class MessageHandler implements ListenerHandler {

    private final GuildMessageReceivedEvent event;

    public MessageHandler(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    //        if (event.getChannel().getId().equals("791636377145180191") && !event.getAuthor().isBot()){
//            new VkHandler(event).handle();
//        }

    @Override
    public void handle() {
        ChannelService channelService = ChannelService.getInstance();
        ActionService actionService = ActionService.getInstance();
        if (!event.getAuthor().isBot()) {
            new MentionHandler(event).handle();
        }

        commandHandler(event.getMessage().getContentRaw());
        actionExecute();

        if (actionService.isActive(ActionType.VACATION, event.getGuild().getId()) && event.getChannel().equals(channelService.getEventChannel(event.getJDA(), ActionType.VACATION, event.getGuild().getId())) && !event.getAuthor().isBot()) {
            new VacationHandler(event).handle();
        }
    }

    private void commandHandler(String message) {
        if (isCommand(message))
            CommandClientManager.create(event.getJDA(), event.getGuild().getId());
    }

    private boolean isCommand(String message) {
        return StringUtils.startsWith(message, "$") && !event.getAuthor().isBot();
    }

    private void actionExecute() {
        Action action;
        ChannelService channels = ChannelService.getInstance();

        final TextChannel blacklistChannel = channels.getEventChannel(event.getJDA(), ActionType.BLACKLIST, event.getGuild().getId());
        final TextChannel statementChannel = channels.getEventChannel(event.getJDA(), ActionType.STATEMENT, event.getGuild().getId());

        if (!event.getAuthor().isBot() && event.getChannel().equals(blacklistChannel)) {
            action = new BlacklistAddAction(event);
        } else if (!event.getAuthor().isBot() && event.getChannel().equals(statementChannel) && !PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            action = new StatementAction(event);
        } else {
            action = new SpeechFilterAction(event);
            if (!event.getAuthor().isBot()) new GainExpAction(event).execute();
            if (!event.getAuthor().isBot()) new AnswerAction(event).execute();
        }
        action.execute();
    }
}
