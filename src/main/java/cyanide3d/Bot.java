package cyanide3d;

import cyanide3d.conf.Config;
import cyanide3d.listener.CommandListener;
import cyanide3d.listener.CyanoListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bot {

    public static void main(String[] args) throws Exception {
        Config config = Config.getInstance();
        CommandListener commandListener = CommandListener.getInstance();
        JDA jda = JDABuilder.createDefault(config.getToken())
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
        jda.addEventListener(commandListener.getCommandListener(), new CyanoListener());
    }
}