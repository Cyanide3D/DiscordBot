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


    public static void main(String[] args) throws Exception{
        conf.Settings sett = new conf.Settings();
        JDA jda = JDABuilder.createDefault(sett.getProperties("TOKEN"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
        CommandClient client = new CommandClientBuilder()
                .setActivity(Activity.playing("Warface RU | $help"))
                .setOwnerId(sett.getProperties("OWNER_ID"))
                .setPrefix(sett.getProperties("PREFIX"))
                .setHelpWord("help")
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .addCommand(new MsgFromBot())
                .addCommand(new WfStats())
                .addCommand(new About())
                .addCommand(new AddBadWord())
                .addCommand(new ListBadWords())
                .addCommand(new Settings())
                .addCommand(new RemoveMessages())
                .addCommand(new RemoveBadWords())
                .addCommand(new SupportCommand())
                .build();

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
