package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.Configuration;


public class AboutCommand extends Command {

    public AboutCommand() {
        this.name = "about";
        this.aliases = new String[]{"aboutbot"};
        Localization localization = Localization.getInstance();
        this.help = localization.getMessage("about.help");
    }

    @Override
    protected void execute(CommandEvent e) {
        Configuration configuration = Configuration.getInstance();
        e.reply(
                "При наличии **багов** и **предложений** обращайтесь к **Romeo-y-Cohiba#2151**.\n" +
                        "Ссылка на инвайт бота: " + "<https://discord.com/oauth2/authorize?client_id=" + configuration.getOwner() + "&permissions=8&scope=bot>\n" +
                        "Ссылка для пожертвований на развитие бота: <https://www.donationalerts.com/r/cyanide3d>"
        );
    }
}