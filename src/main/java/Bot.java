import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import commands.About;
import commands.MsgFromBot;
import commands.WfStats;
import events.ChatEvent;
import events.EventRequest;
import events.JoinEvent;
import events.LeaveEvent;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {

    public static void main(String args[]) throws Exception{
        JDA jda = JDABuilder.createDefault("NzcwMjgwMjIxMjc0MTQ0Nzk5.X5bRWw.d5OQ1XGTFwwUPQ8J1KK9i7wbtks").enableIntents(GatewayIntent.GUILD_MEMBERS).build();
        CommandClientBuilder cb = new CommandClientBuilder();

        cb.setActivity(Activity.playing("Warface RU | $help"));
        cb.setOwnerId("770280221274144799");
        cb.setPrefix("$");
        cb.setHelpWord("help");
        cb.setStatus(OnlineStatus.DO_NOT_DISTURB);
        cb.addCommand(new MsgFromBot());
        cb.addCommand(new WfStats());
        cb.addCommand(new About());

        CommandClient client = cb.build();

        jda.addEventListener(client);
        jda.addEventListener(new LeaveEvent());
        jda.addEventListener(new JoinEvent());
        jda.addEventListener(new ChatEvent());
        //jda.addEventListener(new EventRequest());
    }
}
