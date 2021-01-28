package cyanide3d.handlers.listener;

import cyanide3d.actions.*;
import cyanide3d.conf.Permission;
import cyanide3d.service.ChannelManagmentService;
import cyanide3d.service.EnableActionService;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class MessageReceivedHandler implements ListenerHandler {

    private final GuildMessageReceivedEvent event;

    public MessageReceivedHandler(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    @Override
    public void handle() {
        if (!event.getAuthor().isBot()) {
            new MentionCacheHandler(event).handle();
        }

        actionExecute();

        if (event.getChannel().getId().equals("791636377145180191") && !event.getAuthor().isBot()){
            if (!EnableActionService.getInstance().getState("vkdiscord")) {
                return;
            }
            new SendToVkHandler(event).handle();
        }

        if (event.getChannel().getId().equals("785133010990792764") && !event.getAuthor().isBot()){
            new VacationHandler(event).handle();
        }
    }

    private void actionExecute() {
        Action action;
        ChannelManagmentService channels = ChannelManagmentService.getInstance();
        if (!event.getAuthor().isBot() && event.getChannel().equals(channels.blackListChannel(event))) {
            action = new BlacklistAddAction(event);
        } else if (!event.getAuthor().isBot() && event.getChannel().equals(channels.joinFormChannel(event)) && !PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            action = new JoinFormAction(event);
        } else {
            action = new SpeechFilterAction(event);
            if (!event.getAuthor().isBot()) new GainExpAction(event).execute();
            if (!event.getAuthor().isBot()) new AnswerAction(event).execute();
        }
        action.execute();
    }
}
