package cyanide3d.handlers.listener.receivedmessage.dialog;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class AvatarInterceptor implements MessageInterceptor{
    @Override
    public void execute(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (!event.getAuthor().isBot() && message.contains("ай") && message.contains("аватар")) {
            String url;
            if (!event.getMessage().getMentionedMembers().isEmpty()) {
                url = event.getMessage().getMentionedMembers().get(0).getUser().getEffectiveAvatarUrl();
            } else if (message.split(" ").length < 3){
                url = event.getAuthor().getEffectiveAvatarUrl();
            } else {
                return;
            }
            event.getChannel().sendMessage(url).queue();
        }
    }
}
