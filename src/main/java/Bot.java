import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import commands.*;
import events.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bot {


    public static void main(String args[]) throws Exception{
        conf.Settings sett = new conf.Settings();
        JDA jda = JDABuilder.createDefault(sett.getProperties("TOKEN"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
        CommandClientBuilder cb = new CommandClientBuilder();
        cb.setActivity(Activity.playing("Warface RU | $help"));
        cb.setOwnerId(sett.getProperties("OWNER_ID"));
        cb.setPrefix(sett.getProperties("PREFIX"));
        cb.setHelpWord("help");
        cb.setStatus(OnlineStatus.DO_NOT_DISTURB);
        cb.addCommand(new MsgFromBot());
        cb.addCommand(new WfStats());
        cb.addCommand(new About());
        cb.addCommand(new AddBadWord());
        cb.addCommand(new ListBadWords());
        cb.addCommand(new Settings());
        cb.addCommand(new RemoveMessages());
        cb.addCommand(new RemoveBadWords());
        cb.addCommand(new SupportCommand());

        CommandClient client = cb.build();

        jda.addEventListener(client);
        jda.addEventListener(new LeaveEvent());
        jda.addEventListener(new JoinEvent());
        jda.addEventListener(new ChatEvent());
        jda.addEventListener(new FillBD());
        jda.addEventListener(new BlackListAdd());
        jda.addEventListener(BadWordsEvent.getInstance());
        jda.addEventListener(new EventRequest());

    }

}
