package cyanide3d.handlers.socket;

import cyanide3d.filters.SocketFilter;
import cyanide3d.util.MyGuild;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.commons.lang3.StringUtils;

public class DiscordHandler implements SocketHandler{
    Guild guild;
    String message;

    public DiscordHandler(String message) {
        this.message = message;
        this.guild = MyGuild.getInstance().getGuild();
    }

    @Override
    public String handle() {
        guild.getTextChannelById("791636377145180191").sendMessage(createMessageHandler()).queue();
        return null;
    }

    private String createMessageHandler(){
        final String SEPARATOR = "------";
        String messageToSend = new StringBuilder()
                .append("**[From VK] ")
                .append(StringUtils.substringBefore(message, SEPARATOR))
                .append("** : ")
                .append(StringUtils.substringAfter(message, SEPARATOR))
                .toString();
        return new SocketFilter(messageToSend).toDiscord();
    }
}