package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import net.dv8tion.jda.api.entities.Member;


public class AboutCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public AboutCommand() {
        this.name = "about";
        this.aliases = new String[]{"aboutbot"};
        this.help = localization.getMessage("about.help");
    }

    @Override
    protected void execute(CommandEvent e) {
        Member author = e.getGuild().getMemberById("534894366448156682");
        Member bot = e.getGuild().getMemberById("770280221274144799");
        e.reply(localization.getMessage("about", bot.getUser().getName()));
        e.reply("Ссылка для пожертвований на развитие проекта: <https://www.donationalerts.com/r/cyanide3d>");
        e.reply(localization.getMessage("authorAbout") + author.getAsMention());
        e.reply(localization.getMessage("dateAbout") + " **26.10.2020.**");
    }
}