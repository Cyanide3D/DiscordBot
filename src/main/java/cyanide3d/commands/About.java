package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import net.dv8tion.jda.api.entities.Member;

import java.util.Locale;
import java.util.ResourceBundle;


public class About extends Command {

    private Localization localization = new Localization(new Locale("ru", "RU"));

    public About(){
        this.name = "about";
        this.aliases = new String[]{"aboutbot"};
        this.help = localization.getMessage("about.help");
    }

    @Override
    protected void execute(CommandEvent e) {
        Member author = e.getGuild().getMemberById("534894366448156682");
        Member bot = e.getGuild().getMemberById("770280221274144799");
        e.reply(localization.getMessage("about",bot.getUser().getName()));
        e.reply(localization.getMessage("authorAbout") + author.getAsMention());
        e.reply(localization.getMessage("dateAbout") + " **26.10.2020.**");
    }
}