package cyanide3d.handlers;

import cyanide3d.misc.MyGuild;
import net.dv8tion.jda.api.entities.Guild;

public class FromVkToDiscordMessageHandler {
    String[] data;
    String channelId;
    Guild guild;

    public FromVkToDiscordMessageHandler(String msg) {
        this.guild = MyGuild.getInstance().getGuild();
        data = msg.split("-----*");
    }

    public void send() {
        StringBuilder body = new StringBuilder().append("**[From VK] ").append(data[0]).append(data[1]). append("** : ");
        data[0] = "";
        data[1] = "";
        for (String datum : data) {
            if (!datum.isEmpty()){
                body.append(datum).append("\n");
            }
        }
        guild.getTextChannelById("791636377145180191").sendMessage(body.toString()).queue();
    }
}