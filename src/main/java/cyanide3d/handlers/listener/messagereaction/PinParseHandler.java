package cyanide3d.handlers.listener.messagereaction;

import cyanide3d.repository.service.ActionService;
import cyanide3d.repository.service.ChannelService;
import cyanide3d.repository.service.Giveaway;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;

public class PinParseHandler implements MessageReactionHandler{

    private final Giveaway pinService = Giveaway.getInstance();

    @Override
    public void execute(GenericGuildMessageReactionEvent event) {
        if (isAbort(event)) {
            return;
        }

        String pin = pinService.getPinForMember(event.getMember(), event.getGuild().getId());
        sendMessages(event.getUser(), pin, event);
    }

    private boolean isAbort(GenericGuildMessageReactionEvent event){
        final Message message = pinService.getMessage(event.getGuild().getId());
        return message == null
                || event.getUser().isBot()
                || !event.getMessageId().equals(message.getId())
                || pinService.isUserReacted(event.getGuild().getId(), event.getMember())
                || event.getReaction().retrieveUsers().complete().stream().noneMatch(User::isBot);
    }

    private void sendMessages(User user, String pin, GenericGuildMessageReactionEvent event) {

        ChannelService channelService = ChannelService.getInstance();
        ActionService actionService = ActionService.getInstance();

        user.openPrivateChannel().queue(privateChannel ->
                privateChannel.sendMessage(pin).queue());

        if (actionService.isActive(ActionType.LOG, event.getGuild().getId())) {
            channelService
                    .getEventChannelOrDefault(ActionType.LOG, event.getGuild().getId())
                    .sendMessage(user.getAsMention() + " взял пин " + pin)
                    .queue();
        }
    }
}
