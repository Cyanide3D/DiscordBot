package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Member;

import java.util.Locale;
import java.util.ResourceBundle;


public class About extends Command {

    ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));

    public About(){
        this.name = "about";
        this.aliases = new String[]{"aboutbot"};
        this.help = bundle.getString("about.help");
    }

    @Override
    protected void execute(CommandEvent e) {
        Member author = e.getGuild().getMemberById("534894366448156682");
        Member bot = e.getGuild().getMemberById("770280221274144799");
        e.reply(String.format(bundle.getString("about"),bot.getUser().getName()));
        e.reply(bundle.getString("authorAbout") + " **" + author.getAsMention() + "**\n");
        e.reply(bundle.getString("dateAbout") + " **26.10.2020.**");
    }
}