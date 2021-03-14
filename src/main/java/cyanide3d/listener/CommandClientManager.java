package cyanide3d.listener;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import cyanide3d.commands.basic.*;
import cyanide3d.commands.fun.EightBall;
import cyanide3d.commands.fun.Facts;
import cyanide3d.commands.fun.Suicide;
import cyanide3d.commands.mod.*;
import cyanide3d.commands.mod.emoji.EmojiCommand;
import cyanide3d.commands.music.*;
import cyanide3d.dao.old.CommandDao;
import cyanide3d.model.CustomCommand;
import cyanide3d.conf.Config;
import cyanide3d.service.CustomCommandService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.util.List;

public class CommandClientManager {

    private static CommandClientManager instance;
    private CommandClient commandClient;
    private final CustomCommandService service;
    private final Config config = Config.getInstance();
    private final JDA jda;
    private final EventWaiter waiter = new EventWaiter();

    private CommandClientManager(JDA jda) {
        service = CustomCommandService.getInstance();
        this.jda = jda;
        commandClient = makeClient();
        jda.addEventListener(waiter);
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

    public void createCommand(String command, String body) {
        service.add(command, body);
        updateListener();
    }

    public void deleteCommand(String command) {
        service.delete(command);
        updateListener();
    }

    public void updateListener() {
        final CommandClient oldClient = commandClient;
        commandClient = makeClient();
        jda.removeEventListener(oldClient);
        jda.addEventListener(commandClient);
    }

    private CommandClient makeClient() {
        CommandClientBuilder commandClientBuilder = new CommandClientBuilder()
                .setActivity(Activity.listening("!help | Have fun!"))
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
                        new PinCommand(),
                        new MentionRole(),
                        new PinInfoCommand(),
                        new Question(),
                        new EmojiCommand(waiter));
        commandClientBuilder.addCommands(service.getCommands().toArray(new CustomCommand[0]));
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
