package cyanide3d.listener;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import cyanide3d.commands.basic.*;
import cyanide3d.commands.fun.EightballCommand;
import cyanide3d.commands.fun.FactsCommand;
import cyanide3d.commands.fun.SuicideCommand;
import cyanide3d.commands.mod.*;
import cyanide3d.commands.mod.action.ActionActivateCommand;
import cyanide3d.commands.mod.action.ActionStateCommand;
import cyanide3d.commands.mod.badwords.BadwordAddCommand;
import cyanide3d.commands.mod.badwords.BadwordListCommand;
import cyanide3d.commands.mod.badwords.BadwordRemoveCommand;
import cyanide3d.commands.mod.customcommands.AddCustomCommand;
import cyanide3d.commands.mod.customcommands.DeleteCustomCommand;
import cyanide3d.commands.mod.emoji.EmojiCommand;
import cyanide3d.commands.mod.pin.PinCommand;
import cyanide3d.commands.mod.pin.PinInfoCommand;
import cyanide3d.commands.mod.settings.SetPrefixCommand;
import cyanide3d.commands.mod.settings.SettingsCommand;
import cyanide3d.commands.mod.settings.TestCommand;
import cyanide3d.commands.music.*;
import cyanide3d.model.CustomCommand;
import cyanide3d.conf.Config;
import cyanide3d.service.CustomCommandService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

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
                .addCommands(new MessageCommand(),
                        new WfStatCommand(),
                        new AboutCommand(),
                        new BadwordAddCommand(),
                        new MapsCommand(),
                        new BadwordListCommand(),
                        new SettingsCommand(),
                        new ClearCommand(),
                        new BadwordRemoveCommand(),
                        new LevelCommand(),
                        new SuicideCommand(),
                        new FactsCommand(),
                        new HelpCommand(),
                        new LeaderBoardCommand(),
                        new TestCommand(),
                        new BlacklistCommand(),
                        new AddCustomCommand(),
                        new DeleteCustomCommand(),
                        new SetPrefixCommand(),
                        new ActionActivateCommand(),
                        new ActionStateCommand(),
                        new PlayCommand(),
                        new LeaveCommand(),
                        new SkipCommand(),
                        new PauseCommand(),
                        new ResumeCommand(),
                        new ClearQueueCommand(),
                        new ListQueueCommand(),
                        new StopCommand(),
                        new EightballCommand(),
                        new JoinCommand(),
                        new PinCommand(),
                        new MentionCommand(),
                        new PinInfoCommand(),
                        new QuestionCommand(),
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
