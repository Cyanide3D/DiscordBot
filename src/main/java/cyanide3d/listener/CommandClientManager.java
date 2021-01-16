package cyanide3d.listener;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import cyanide3d.commands.basic.*;
import cyanide3d.commands.fun.EightBall;
import cyanide3d.commands.fun.Facts;
import cyanide3d.commands.fun.Suicide;
import cyanide3d.commands.mod.*;
import cyanide3d.commands.music.*;
import cyanide3d.dao.CustomCommandsDao;
import cyanide3d.model.CustomCommand;
import cyanide3d.conf.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.util.List;

public class CommandClientManager {

    private static CommandClientManager instance;
    private final CustomCommandsDao dao;
    private CommandClient commandClient;
    private final Config config = Config.getInstance();
    private final JDA jda;

    private CommandClientManager(JDA jda) {
        this.jda = jda;
        dao = new CustomCommandsDao();
        commandClient = makeClient();
    }

    public static CommandClientManager create(JDA jda) {
        return instance = new CommandClientManager(jda);
    }

    public static CommandClientManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Client manager should be created first (use create method)");
        }
        return instance;
    }

    public CommandClient getCommandClient() {
        return commandClient;
    }

    public void createCommand(String name, String body) {
        if (dao.get(name) != null) return;
        dao.create(new CustomCommand(name, body));
        updateListener();
    }

    public void deleteCommand(String name) {
        //customCommands.stream().filter(commands -> commands.getName().equalsIgnoreCase(name)).findFirst()
        CustomCommand command = dao.get(name);
        if (command == null) return;
        dao.delete(command);
        updateListener();
    }

    public void updateListener() {
        final CommandClient oldClient = commandClient;
        commandClient = makeClient();
        jda.removeEventListener(oldClient);
        jda.addEventListener(commandClient);
    }

    public List<CustomCommand> getCommands() {
        return dao.list();
    }

    private CommandClient makeClient() {
        CommandClientBuilder commandClientBuilder = new CommandClientBuilder()
                .setActivity(Activity.listening("!help | Happy new year!"))
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
                        new SetPrefix(),
                        new ActivateAction(),
                        new ListenerState(),
                        new Play(),
                        new Leave(),
                        new SkipMusic(),
                        new Pause(),
                        new Resume(),
                        new ClearQueue(),
                        new ListQueue(),
                        new Stop(),
                        new EightBall(),
                        new Join(),
                        new Pin(),
                        new MentionRole(),
                        new PinInfo(),
                        new Question());
        commandClientBuilder.addCommands(dao.list().toArray(new CustomCommand[0]));
        return commandClientBuilder.build();
    }

    public String getPrefix() {
        return commandClient.getPrefix();
    }

    public void setPrefix(String prefix) {
        config.setPrefix(prefix);
        updateListener();
    }
}
