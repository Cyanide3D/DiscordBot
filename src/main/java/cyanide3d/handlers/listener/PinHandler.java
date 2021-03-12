package cyanide3d.handlers.listener;

import cyanide3d.service.ChannelManagmentService;
import cyanide3d.service.PinService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class ParsePinReactionHandler implements ListenerHandler {

    private final GuildMessageReactionAddEvent event;
    private final PinService pinService = PinService.getInstance();

    public ParsePinReactionHandler(GuildMessageReactionAddEvent event) {
        this.event = event;
    }

    @Override
    public void handle() {
        if (event.getUser().isBot() || !event.getMessageId().equals(pinService.getParseMessage().getId()) || pinService.getReactedUser().contains(event.getMember()) || event.getReaction().retrieveUsers().complete().stream().noneMatch(User::isBot)) {
            return;
        }

        String pin = pinService.getPinForMember(event.getMember());
        sendMessages(event.getUser(), pin);

        pinService.isEndDistribution();
    }

    private void sendMessages(User user, String pin) {
        user.openPrivateChannel().queue(privateChannel ->
                privateChannel.sendMessage(pin).queue());

        ChannelManagmentService.getInstance()
                .loggingChannel(event.getGuild())
                .sendMessage(user.getAsMention() + " взял пин " + pin)
                .queue();
    }

}
