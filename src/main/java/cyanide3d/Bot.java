package cyanide3d;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import cyanide3d.commands.*;
import cyanide3d.conf.Config;
import cyanide3d.listener.CyanoListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bot {

    public static void main(String[] args) throws Exception{
        Config config = Config.getInstance();
        JDA jda = JDABuilder.createDefault(config.getToken())
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();

        CommandClient commandListener = new CommandClientBuilder()//TODO тоже бы вынести в отдельный класс (наверное)
                .setActivity(Activity.watching("битву гусей."))
                .setOwnerId(config.getOwner())
                .setPrefix(config.getPrefix())
                .setHelpWord("helpsdad")
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .addCommand(new MsgFromBot())
                .addCommand(new WfStats())
                .addCommand(new About())
                .addCommand(new AddBadWord())
                .addCommand(new Maps())
                .addCommand(new Vk())
                .addCommand(new ListBadWords())
                .addCommand(new Settings())
                .addCommand(new RemoveMessages())
                .addCommand(new RemoveBadWords())
                .addCommand(new UserLvl())
                .addCommand(new Suicide())
                .addCommand(new Facts())
                .addCommand(new Help())
                .addCommand(new LeaderBoard())
                .addCommand(new SupportCommand())
                .addCommand(new Blacklist())
                .build();

        jda.addEventListener(commandListener,new CyanoListener());
    }

}
