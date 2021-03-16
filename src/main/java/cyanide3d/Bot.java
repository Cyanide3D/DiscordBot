package cyanide3d;

import cyanide3d.conf.Config;
import cyanide3d.listener.CommandClientManager;
import cyanide3d.listener.EventListener;
import cyanide3d.listener.LogListener;
import cyanide3d.listener.SocketListener;
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
        logger.info("Start initialization");
        JDA jda = JDABuilder.createDefault(config.getToken())
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
        new SocketListener().start();
        jda.addEventListener(CommandClientManager.getDefault(), new EventListener(), new LogListener());
        Thread.sleep(2000);
        logger.info("Working on " + jda.getGuilds().size() + " servers.");
        logger.info("Successful initialization");
    }
}