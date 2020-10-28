import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import commands.*;
import events.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {

    public static void main(String args[]) throws Exception{
        JDA jda = JDABuilder.createDefault("NzcwMjgwMjIxMjc0MTQ0Nzk5.X5bRWw.UaErBo5IpSvc3zkGF18ThQfV_xM").enableIntents(GatewayIntent.GUILD_MEMBERS).build();
        CommandClientBuilder cb = new CommandClientBuilder();

        cb.setActivity(Activity.playing("Warface RU | $help"));
        cb.setOwnerId("770280221274144799");
        cb.setPrefix("$");
        cb.setHelpWord("help");
        cb.setStatus(OnlineStatus.DO_NOT_DISTURB);
        cb.addCommand(new MsgFromBot());
        cb.addCommand(new WfStats());
        cb.addCommand(new About());
        cb.addCommand(new AddBadWord());
        cb.addCommand(new ListBadWords());
        cb.addCommand(new RemoveBadWords());

        CommandClient client = cb.build();

        jda.addEventListener(client);
        jda.addEventListener(new LeaveEvent());
        jda.addEventListener(new JoinEvent());
        jda.addEventListener(new ChatEvent());
        jda.addEventListener(new BadWordsEvent());
        //jda.addEventListener(new EventRequest());
    }
}
