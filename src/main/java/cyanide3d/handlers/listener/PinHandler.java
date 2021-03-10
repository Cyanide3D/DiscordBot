package cyanide3d.handlers.listener;

import cyanide3d.service.ChannelService;
import cyanide3d.service.Giveaway;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class PinHandler implements ListenerHandler {

    private final GuildMessageReactionAddEvent event;
    private final Giveaway giveaway = Giveaway.getInstance();

    public PinHandler(GuildMessageReactionAddEvent event) {
        this.event = event;
    }

    @Override
    public void handle() {
        if (event.getUser().isBot() || !event.getMessageId().equals(giveaway.getMessage().getId()) || giveaway.getReactedUsers().contains(event.getMember()) || event.getReaction().retrieveUsers().complete().stream().noneMatch(User::isBot)) {
            return;
        }

        String pin = giveaway.getPinForMember(event.getMember());
        sendPin(event.getUser(), pin);
    }

    private void sendPin(User user, String pin) {
        user.openPrivateChannel().queue(privateChannel ->
                privateChannel.sendMessage(pin).queue());

        ChannelService.getInstance()
                .loggingChannel(event.getGuild())
                .sendMessage(user.getAsMention() + " взял пин " + pin)
                .queue();
    }

}
