package cyanide3d.handlers.listener;

import cyanide3d.service.ChannelService;
import cyanide3d.service.Giveaway;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class PinHandler implements ListenerHandler {

    private final GuildMessageReactionAddEvent event;
    private final Giveaway pinService = Giveaway.getInstance();

    public PinHandler(GuildMessageReactionAddEvent event) {
        this.event = event;
    }

    @Override
    public void handle() {
        if (event.getUser().isBot() || !event.getMessageId().equals(pinService.getMessage().getId()) || pinService.getReactedUsers().contains(event.getMember()) || event.getReaction().retrieveUsers().complete().stream().noneMatch(User::isBot)) {
            return;
        }

        String pin = pinService.getPinForMember(event.getMember());
        sendMessages(event.getUser(), pin);
    }

    private void sendMessages(User user, String pin) {

        ChannelService channelService = ChannelService.getInstance();

        user.openPrivateChannel().queue(privateChannel ->
                privateChannel.sendMessage(pin).queue());

        channelService
                .getEventChannel(event.getJDA(), ActionType.LOG, event.getGuild().getId())
                .sendMessage(user.getAsMention() + " взял пин " + pin)
                .queue();
    }

}
