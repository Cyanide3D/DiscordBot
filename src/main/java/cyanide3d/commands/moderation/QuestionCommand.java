package cyanide3d.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class QuestionCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public QuestionCommand() {
        this.name = "question";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().isEmpty()) {
            event.reply("Необходимо после комманды ввести текст для опроса.");
            return;
        }
        event.getMessage().delete().queue();

        Message message = event.getTextChannel().sendMessage(formattedMessage(event)).complete();
        addReactionsToMessage(message);
    }

    private MessageEmbed formattedMessage(CommandEvent event) {
        return new EmbedBuilder()
                .setTitle("ВНИМАНИЕ! Опрос.")
                .setDescription("*" + event.getArgs() + "*")
                .setColor(Color.ORANGE)
                .addField("Варианты ответов:", ":white_check_mark: - Да. **|** :grey_question: - Не знаю. **|** :x: - Нет.", false)
                .build();
    }

    private void addReactionsToMessage(Message message) {
        message.addReaction("U+2705").queue();
        message.addReaction("U+2754").queue();
        message.addReaction("U+274C").queue();
    }
}
