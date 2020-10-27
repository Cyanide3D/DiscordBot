package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class MsgFromBot extends Command {
    public MsgFromBot(){
        this.name = "msg";
        this.aliases = new String[]{"message"};
        this.arguments = "[message]";
        this.help = "Команда позволяет писать от имени бота. (Только для уполномоченых лиц)";
    }

    @Override
    protected void execute(CommandEvent event) {
        if(equalsId(event.getAuthor().getId())) {
            StringBuilder sb = new StringBuilder();
            String[] msg = event.getMessage().getContentRaw().split(" ");
            if (msg.length >= 1) {
                for (int i = 1; i < msg.length; i++) {
                    sb.append(msg[i] + " ");
                }
                event.getMessage().delete().queue();
                event.getChannel().sendMessage(sb.toString()).queue();
            }
        }else{
            event.getChannel().sendMessage("Недостаточно прав для использования команды!").queue();
        }
    }
    public boolean equalsId(String usrID){
        String[] allowIDs = {"320967415863312386", "534894366448156682"};
        for(int i = 0; i < allowIDs.length; i++){
            if(allowIDs[i].equalsIgnoreCase(usrID)){
                return true;
            }
        }
        return false;
    }
}
