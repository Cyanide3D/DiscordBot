package cyanide3d;

import cyanide3d.conf.Config;
import cyanide3d.listener.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Bot {

    public static void main(String[] args) throws Exception {
        Logger logger = LoggerFactory.getLogger(Bot.class);
        Config config = Config.getInstance();
        logger.info("Start initialization\n");
        JDA jda = JDABuilder.createDefault(config.getToken())
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
        new SocketMessageListener().start();
        CommandClientManager commandClientManager = CommandClientManager.create(jda);
        jda.addEventListener(commandClientManager.getCommandClient(), new CyanoListener(), new LoggingListener());
        logger.info("Successful initialization\n");
    }
}