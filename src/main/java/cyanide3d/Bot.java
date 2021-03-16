package cyanide3d;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import cyanide3d.conf.Config;
import cyanide3d.listener.EventListener;
import cyanide3d.listener.LogListener;
import cyanide3d.listener.SocketListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
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
        CommandClient commandClient = new CommandClientBuilder()
                .setActivity(Activity.listening("!help | Have fun!"))
                .setOwnerId(config.getOwner())
                .setHelpWord("sdxczxcasd")
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .build();
        new SocketListener().start();
        jda.addEventListener(commandClient, new EventListener(), new LogListener());
        logger.info("Successful initialization");
    }
}