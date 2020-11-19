package cyanide3d;

import cyanide3d.conf.Config;
import cyanide3d.conf.Logging;
import cyanide3d.listener.CommandClientManager;
import cyanide3d.listener.CyanoListener;
import cyanide3d.listener.LoggingListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.util.logging.Logger;

public class Bot {

    public static void main(String[] args) throws Exception {
        Logger logger = Logging.getInstance().getLogger();
        Config config = Config.getInstance();
        logger.info("Start initialization\n");
        JDA jda = JDABuilder.createDefault(config.getToken())
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
        //jda.awaitReady();
        CommandClientManager commandClientManager = CommandClientManager.create(jda);
        jda.addEventListener(commandClientManager.getCommandClient(), new CyanoListener(), new LoggingListener());
        logger.info("Successful initialization\n");
    }
}