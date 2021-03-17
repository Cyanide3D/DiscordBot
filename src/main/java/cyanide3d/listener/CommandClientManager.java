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
import cyanide3d.commands.mod.entrymessage.AddEntryMessageCommand;
import cyanide3d.commands.mod.entrymessage.DeleteEntryMessageCommand;
import cyanide3d.commands.mod.entrymessage.ListEntryMessageCommand;
import cyanide3d.commands.mod.joinleave.JoinLeaveSettingCommand;
import cyanide3d.commands.mod.pin.PinCommand;
import cyanide3d.commands.mod.pin.PinInfoCommand;
import cyanide3d.commands.mod.settings.*;
import cyanide3d.commands.music.*;
import cyanide3d.model.CustomCommand;
import cyanide3d.conf.Config;
import cyanide3d.service.CustomCommandService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class CommandClientManager {

    private static CommandClientManager instance;
    private String guildId;
    private CommandClient commandClient;
    private final CustomCommandService service;
    private final Config config = Config.getInstance();
    private final JDA jda;
    private final EventWaiter waiter = new EventWaiter();

    private CommandClientManager(JDA jda, String guildId) {
        service = CustomCommandService.getInstance();
        this.guildId = guildId;
        this.jda = jda;
        jda.addEventListener(waiter);
        addListenerIntoJDA();
    }

    private synchronized void addListenerIntoJDA() {
        if (commandClient != null) {
            jda.removeEventListener(commandClient);
        }
        commandClient = makeClient();
        jda.addEventListener(commandClient);
    }

    public static CommandClientManager create(JDA jda, String guildId) {
        if (instance == null) {
            instance = new CommandClientManager(jda, guildId);
        }
        return instance;
    }

    public static CommandClientManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Client manager should be created first (use create method)");
        }
        return instance;
    }

    public synchronized void createCommand(String command, String body, String guildId) {
        service.add(command, body, guildId);
    }

    public synchronized void deleteCommand(String command) {
        service.delete(command);
    }

    private synchronized CommandClient makeClient() {
        CommandClientBuilder commandClientBuilder = new CommandClientBuilder()
                .setOwnerId(config.getOwner())
                .setPrefix(config.getPrefix())
                .setHelpWord("xzczxcxzcs")
                .addCommands(new MessageCommand(),
                        new WfStatCommand(),
                        new AboutCommand(),
                        new BadwordAddCommand(),
                        new MapsCommand(),
                        new BadwordListCommand(),
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
                        //new SetPrefixCommand(),
                        new ActionActivateCommand(),
                        new ActionStateCommand(),
                        new JoinLeaveSettingCommand(waiter),
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
                        new AddEntryMessageCommand(),
                        new DeleteEntryMessageCommand(),
                        new ListEntryMessageCommand(),
                        new PinCommand(),
                        new MentionCommand(),
                        new PinInfoCommand(),
                        new PermissionSettingsCommand(),
                        new ChannelSettingsCommand(),
                        new QuestionCommand(),
                        new EmojiCommand(waiter));
        commandClientBuilder.addCommands(service.getCommands(guildId).toArray(new CustomCommand[0]));
        return commandClientBuilder.build();
    }

    public static CommandClient getDefault() {
        return new CommandClientBuilder()
                .setActivity(Activity.listening("!help | Have fun!"))
                .setOwnerId(Config.getInstance().getOwner())
                .setHelpWord("sdxczxcasd")
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .build();
    }

    public String getPrefix() {
        return commandClient.getPrefix();
    }

    public void setPrefix(String prefix) {
        config.setPrefix(prefix);
    }
}
