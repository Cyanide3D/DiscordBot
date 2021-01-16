package cyanide3d.filters;

import cyanide3d.misc.MyGuild;
import net.dv8tion.jda.api.entities.Guild;

public class MessageMentionFilter {
    String message;
    Guild guild = MyGuild.getInstance().getGuild();

    public MessageMentionFilter(String message) {
        this.message = message;
    }

    public String toDiscord(){
        return message.replace("@all", guild.getRolesByName("Лентяи", true).get(0).getAsMention());
    }

    public String toVk(){
        throw new UnsupportedOperationException("Unsupported operation 'To VK'");
    }
}
