package cyanide3d.listener;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.dao.CommandsDao;
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
    private CommandsDao dao;
    Config config = Config.getInstance();

    private CommandListener() {
        dao = new CommandsDao();
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
                        new Blacklist(),
                        new AddCommand(),
                        new DeleteCommand(),
                        new SetPrefix());
        dao.list().stream().forEach(commandClientBuilder::addCommand);
        commandClient = commandClientBuilder.build();
        System.out.println("CL make");
    }

    public CommandClient getCommandListener() {
        return commandClient;
    }

    public static CommandListener getInstance() {
        if (instance == null) {
            instance = new CommandListener();
            System.out.println("CL new inst");
        }
        return instance;
    }

    public void createCommand(String name, String body) {
        if (dao.get(name) != null) return;
        dao.create(new Commands(name, body));
        updateListener();
    }

    public void deleteCommand(String name) {
        //customCommands.stream().filter(commands -> commands.getName().equalsIgnoreCase(name)).findFirst()
        Commands command = dao.get(name);
        if (command == null) return;
        dao.delete(command);
        updateListener();
    }

    public String getPrefix() {
        return commandClient.getPrefix();
    }

    public void setPrefix(String prefix) {
        config.setPrefix(prefix);
        updateListener();
    }

    public CommandListener setEvent(CommandEvent event) {
        this.event = event;
        return this;
    }

    public void updateListener() {
        event.getJDA().removeEventListener(commandClient);
        makeListener();
        event.getJDA().addEventListener(commandClient);
    }

    public List<Commands> getCommands() {
        return dao.list();
    }

}
