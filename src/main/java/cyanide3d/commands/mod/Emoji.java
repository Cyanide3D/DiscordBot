package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class Emoji extends Command {

    private final EventWaiter waiter;

    public Emoji(EventWaiter waiter) {
        this.waiter = waiter;
        super.name = "emoji";
    }

    @Override
    protected void execute(CommandEvent event) {
        Map<String, Role> roles = new HashMap<>();
        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setFooter("From De  fiant'S with love ;)");
        event.reply("Введите название.");
        waiter.waitForEvent(GuildMessageReceivedEvent.class, e -> e.getAuthor().equals(event.getAuthor()), title -> {
            builder.setTitle(title.getMessage().getContentRaw());
            event.reply("Введите текст.");
            waiter.waitForEvent(GuildMessageReceivedEvent.class, e -> e.getAuthor().equals(event.getAuthor()), body -> {
                builder.setDescription(body.getMessage().getContentRaw());
                event.reply("Сколько авторолей будет в сообщении? (Цифра)");
                waiter.waitForEvent(GuildMessageReceivedEvent.class, e -> e.getAuthor().equals(event.getAuthor()), iterCount -> {
                    int count = Integer.parseInt(iterCount.getMessage().getContentRaw());
                    for (int i = 0; i < count; i++) {
                        event.reply("Введите ИД эмодзи.");
                        waiter.waitForEvent(GuildMessageReceivedEvent.class, e -> e.getAuthor().equals(event.getAuthor()), roleId -> {
                            event.reply("Укажите роль.");
                            waiter.waitForEvent(GuildMessageReceivedEvent.class, e -> e.getAuthor().equals(event.getAuthor()), role -> {
                                roles.put(roleId.getMessage().getContentRaw(), role.getMessage().getMentionedRoles().get(0));
                            });
                        });
                    }
                });
            });
        });
    }
}
