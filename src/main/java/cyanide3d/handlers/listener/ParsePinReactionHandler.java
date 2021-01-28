package cyanide3d.handlers.listener;

import cyanide3d.service.ChannelManagmentService;
import cyanide3d.service.PinService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import java.util.List;

public class ParsePinReactionHandler implements ListenerHandler {

    private final GuildMessageReactionAddEvent event;

    public ParsePinReactionHandler(GuildMessageReactionAddEvent event) {
        this.event = event;
    }

    @Override
    public void handle() {
        PinService pinService = PinService.getInstance();
        List<Member> reactedUser = pinService.getReactedUser();
        List<String> pins = pinService.getPins();
        Member user = event.getMember();
        if (user.getUser().isBot() || reactedUser.contains(user) || pins.isEmpty()) {
            return;
        }
        if (event.retrieveMessage().complete().getAuthor().isBot() && event.getReaction().retrieveUsers().complete().stream().filter(user1 -> user1.isBot()).findAny() != null && pinService.getParseMessage().getId().equalsIgnoreCase(event.getMessageId())) {
            String message = pins.get(pins.size() - 1);
            user.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(message).queue());
            ChannelManagmentService.getInstance().loggingChannel(event.getGuild()).sendMessage(user.getUser().getAsMention() + " взял пин " + pins.get(pins.size() - 1)).queue();
            pinService.setReactedUser(user);
            pinService.removePin(pins.size() - 1);
            if (pins.isEmpty()) {
                if (pinService.getParseMessage() != null) {
                    pinService.getParseMessage().delete().queue();
                    pinService.setParseMessage(null);
                }
            }
        }
    }
}
