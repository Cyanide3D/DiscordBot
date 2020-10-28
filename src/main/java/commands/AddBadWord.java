package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.TextChannel;
import conf.DatabaseConnection;
import conf.UserAcessToCommand;

import java.sql.SQLException;

public class AddBadWord extends Command {

    public AddBadWord(){
        this.name = "addword";
        this.aliases = new String[]{"addbadword"};
        this.arguments = "[word]";
        this.help = "Добавление запрещенных в предложении слов. (Только для уполномоченых лиц)";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        DatabaseConnection db = new DatabaseConnection();
        UserAcessToCommand usrAc = new UserAcessToCommand();
        if(usrAc.checkAdm(commandEvent.getAuthor().getId())||usrAc.checkMod(commandEvent.getAuthor().getId())) {
            try {
                db.addBadWords(commandEvent.getArgs());
                commandEvent.reply("Слово успешно добавлено :)");
            } catch (SQLException throwables) {
                commandEvent.reply("Нет доступа к базе данных.");
            }
        }else{
            commandEvent.reply("Недостаточно прав для использования команды!");
        }
    }
}
