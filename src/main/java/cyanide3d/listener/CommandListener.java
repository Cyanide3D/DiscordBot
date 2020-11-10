package cyanide3d.listener;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.model.Commands;
import cyanide3d.commands.*;
import cyanide3d.conf.Config;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.util.ArrayList;
import java.util.List;

public class CommandListener {

    CommandEvent event;
    private static CommandListener instance;
    private CommandClient commandClient;
    Config config = Config.getInstance();
    private List<Command> customCommands = new ArrayList<>();

    private CommandListener() {
        makeListener();
    }

    private void makeListener() {
        CommandClientBuilder commandClientBuilder = new CommandClientBuilder()
                .setActivity(Activity.watching("на битву гусей."))
                .setOwnerId(config.getOwner())
                .setPrefix(config.getPrefix())
                .setHelpWord("helpsdad")
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .addCommands(new MsgFromBot(),
                        new WfStats(),
                        new About(),
                        new AddBadWord(),
                        new Maps(),
                        new Vk(),
                        new ListBadWords(),
                        new Settings(),
                        new RemoveMessages(),
                        new RemoveBadWords(),
                        new UserLvl(),
                        new Suicide(),
                        new Facts(),
                        new Help(),
                        new LeaderBoard(),
                        new SupportCommand(),
                        new Blacklist());
        customCommands.stream().forEach(commandClientBuilder::addCommand);
        commandClient = commandClientBuilder.build();
    }

    public CommandClient getCommandListener() {
        return commandClient;
    }

    public static CommandListener getInstance() {
        if (instance == null) instance = new CommandListener();
        return instance;
    }

    public void addCommand(String name, String body) {
        customCommands.add(new Commands(name, body));
        updateListener();
    }

    public void deleteCommand() {
        customCommands.remove(0); //TODO this
    }

    public String getPrefix() {
        return commandClient.getPrefix();
    }

    public void setPrefix(String prefix){
        config.setPrefix(prefix);
        updateListener();
    }

    public CommandListener setEvent(CommandEvent event){
        this.event = event;
        return this;
    }

    public void updateListener(){
        event.getJDA().removeEventListener(commandClient);
        makeListener();
        event.getJDA().addEventListener(commandClient);
    }

}
