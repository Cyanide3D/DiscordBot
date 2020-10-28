package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import conf.DatabaseConnection;
import conf.UserAcessToCommand;

import java.sql.SQLException;
import java.util.ArrayList;

public class RemoveBadWords extends Command {
    public RemoveBadWords(){
        this.name = "removeword";
        this.aliases = new String[]{"removebadword"};
        this.arguments = "[word]";
        this.help = "Удаляет запрещённое слово. (Только для уполномоченых лиц)";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        DatabaseConnection db = new DatabaseConnection();
        UserAcessToCommand usr = new UserAcessToCommand();
        if(usr.checkAdm(commandEvent.getAuthor().getId())||usr.checkMod(commandEvent.getAuthor().getId())) {
            try {
                ArrayList<String> badWords = db.listBadWords();
                for (int i = 0; i < badWords.size(); i++) {
                    if (commandEvent.getArgs().equalsIgnoreCase(badWords.get(i))) {
                        db.removeBadWord(badWords.get(i));
                        commandEvent.reply("Слово успешно удалено.");
                        return;
                    }
                }
            } catch (SQLException throwables) {
                commandEvent.reply("Нет доступа к базе данных.");
            }
        }else{
            commandEvent.reply("Недостаточно прав для использования команды!");
        }
    }
}
