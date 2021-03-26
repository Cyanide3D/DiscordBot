package cyanide3d.listener;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import cyanide3d.Configuration;
import cyanide3d.commands.basic.*;
import cyanide3d.commands.fun.EightballCommand;
import cyanide3d.commands.fun.FactsCommand;
import cyanide3d.commands.fun.SuicideCommand;
import cyanide3d.commands.moderation.*;
import cyanide3d.commands.moderation.action.ActionActivateCommand;
import cyanide3d.commands.moderation.action.ActionStateCommand;
import cyanide3d.commands.moderation.badwords.BadwordAddCommand;
import cyanide3d.commands.moderation.badwords.BadwordListCommand;
import cyanide3d.commands.moderation.badwords.BadwordRemoveCommand;
import cyanide3d.commands.moderation.customcommands.AddCustomCommand;
import cyanide3d.commands.moderation.customcommands.DeleteCustomCommand;
import cyanide3d.commands.moderation.customcommands.ListCustomCommand;
import cyanide3d.commands.moderation.defaultroles.AddDefaultRoleCommand;
import cyanide3d.commands.moderation.defaultroles.DefaultRoleListCommand;
import cyanide3d.commands.moderation.defaultroles.DeleteDefaultRoleCommand;
import cyanide3d.commands.moderation.emoji.EmojiCommand;
import cyanide3d.commands.moderation.greeting.AddGreetingCommand;
import cyanide3d.commands.moderation.greeting.DeleteGreetingCommand;
import cyanide3d.commands.moderation.greeting.ListGreetingCommand;
import cyanide3d.commands.moderation.joinleave.JoinLeaveSettingCommand;
import cyanide3d.commands.moderation.pin.PinCommand;
import cyanide3d.commands.moderation.pin.PinInfoCommand;
import cyanide3d.commands.moderation.settings.ChannelSettingsCommand;
import cyanide3d.commands.moderation.settings.PermissionSettingsCommand;
import cyanide3d.commands.moderation.settings.TestCommand;
import cyanide3d.commands.music.*;
import cyanide3d.model.CustomCommand;
import cyanide3d.repository.service.CustomCommandService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.util.HashMap;
import java.util.Map;

public class CommandClientManager {

    private static CommandClientManager instance;
    private CommandClient currentClient;
    private final CustomCommandService service;
    private final Configuration configuration = Configuration.getInstance();
    private final JDA jda;
    private final EventWaiter waiter = new EventWaiter();
    private final Map<String, CommandClient> commandManagers = new HashMap<>();

    private CommandClientManager(JDA jda) {
        service = CustomCommandService.getInstance();
        this.jda = jda;
        jda.addEventListener(waiter);
    }

    public synchronized void registryCommandManager(String guildId) {
        updateHandler(commandManagers.computeIfAbsent(guildId, this::makeClient));
    }

    private synchronized void updateCommandManager(String guildId) {
        updateHandler(commandManagers.put(guildId, makeClient(guildId)));
    }

    private synchronized void updateHandler(CommandClient commandClient) {
        if (currentClient != null) {
            jda.removeEventListener(currentClient);
        }
        jda.addEventListener(commandClient);
        currentClient = commandClient;
    }

    public static CommandClientManager getInstance() {
        if (instance == null) {
            throw new UnsupportedOperationException("what");
        }
        return instance;
    }

    public static CommandClientManager create(JDA jda) {
        if (instance == null) {
            instance = new CommandClientManager(jda);
        }
        return instance;
    }

    public synchronized void createCommand(String command, String body, String guildId) {
        service.addCommand(command, body, guildId);
        updateCommandManager(guildId);
    }

    public synchronized void deleteCommand(String command, String guildId) {
        service.deleteCommand(command, guildId);
        updateCommandManager(guildId);
    }

    private synchronized CommandClient makeClient(String guildId) {
        CommandClientBuilder commandClientBuilder = new CommandClientBuilder()
                .setOwnerId(configuration.getOwner())
                .setPrefix(configuration.getPrefix())
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
                        new AddGreetingCommand(),
                        new DeleteGreetingCommand(),
                        new ListGreetingCommand(),
                        new PinCommand(),
                        new MentionCommand(),
                        new PinInfoCommand(),
                        new PermissionSettingsCommand(),
                        new ChannelSettingsCommand(),
                        new QuestionCommand(),
                        new AddDefaultRoleCommand(),
                        new DeleteDefaultRoleCommand(),
                        new KickCommand(),
                        new BanCommand(),
                        new DefaultRoleListCommand(),
                        new ListCustomCommand(),
                        new EmojiCommand(waiter));
        commandClientBuilder.addCommands(service.getCommandSet(guildId).toArray(new CustomCommand[0]));
        return commandClientBuilder.build();
    }

    public CommandClient getDefaultManager() {
        return new CommandClientBuilder()
                .setActivity(Activity.listening("!help | Have fun!"))
                .setOwnerId(Configuration.getInstance().getOwner())
                .setHelpWord("sdxczxcasd")
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .build();
    }

    public String getPrefix() {
        return currentClient.getPrefix();
    }
}
