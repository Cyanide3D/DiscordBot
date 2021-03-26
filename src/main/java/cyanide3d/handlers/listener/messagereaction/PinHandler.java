package cyanide3d.handlers.listener.messagereaction;

import cyanide3d.service.ActionService;
import cyanide3d.service.ChannelService;
import cyanide3d.service.Giveaway;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class PinHandler {

//    private final GuildMessageReactionAddEvent event;
//    private final Giveaway pinService = Giveaway.getInstance();
//
//    public PinHandler(GuildMessageReactionAddEvent event) {
//        this.event = event;
//    }
//
//    public void handle() {
//        if (isAbort()) {
//            return;
//        }
//
//        String pin = pinService.getPinForMember(event.getMember(), event.getGuild().getId());
//        sendMessages(event.getUser(), pin);
//    }
//
//    private boolean isAbort(){
//
//        final Message message = pinService.getMessage(event.getGuild().getId());
//        if (message == null)
//            return true;
//
//        return event.getUser().isBot()
//                || !event.getMessageId().equals(message.getId())
//                || pinService.getReactedUsers(event.getGuild().getId()).contains(event.getMember())
//                || event.getReaction().retrieveUsers().complete().stream().noneMatch(User::isBot);
//    }
//
//    private void sendMessages(User user, String pin) {
//
//        ChannelService channelService = ChannelService.getInstance();
//        ActionService actionService = ActionService.getInstance();
//
//        user.openPrivateChannel().queue(privateChannel ->
//                privateChannel.sendMessage(pin).queue());
//
//        if (actionService.isActive(ActionType.LOG, event.getGuild().getId())) {
//            channelService
//                    .getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId())
//                    .sendMessage(user.getAsMention() + " взял пин " + pin)
//                    .queue();
//        }
//    }

}
