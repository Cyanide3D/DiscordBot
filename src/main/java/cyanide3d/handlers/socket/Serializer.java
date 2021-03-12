package cyanide3d.handlers.socket;

import cyanide3d.util.MyGuild;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class Serializer {

    private final Guild guild = MyGuild.getInstance().getGuild();

    public String getChannels() {
        List<TextChannel> textChannels = guild.getTextChannels();
        StringBuilder result = new StringBuilder();
        for (TextChannel textChannel : textChannels) {
            result
                    .append(textChannel.getName())
                    .append(":")
                    .append(textChannel.getId())
                    .append("\n");
        }
        return result.toString();
    }
}
