package cyanide3d.handlers.socket;

import cyanide3d.filters.MessageMentionFilter;
import cyanide3d.misc.MyGuild;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.commons.lang3.StringUtils;

public class FromVkToDiscordMessageHandler implements SocketHandler{
    Guild guild;
    String message;

    public FromVkToDiscordMessageHandler(String message) {
        this.message = message;
        this.guild = MyGuild.getInstance().getGuild();
    }

    @Override
    public void handle() {
        guild.getTextChannelById("791636377145180191").sendMessage(createMessageHandler()).queue();
    }

    private String createMessageHandler(){
        final String SEPARATOR = "------";
        String messageToSend = new StringBuilder()
                .append("**[From VK] ")
                .append(StringUtils.substringBefore(message, SEPARATOR))
                .append("** : ")
                .append(StringUtils.substringAfter(message, SEPARATOR))
                .toString();
        return new MessageMentionFilter(messageToSend).toDiscord();
    }
}