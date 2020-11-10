package cyanide3d.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.listener.CommandListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Locale;

public class EmbedTemplates {

    private static Localization localization = new Localization(new Locale("ru", "RU"));
    private static CommandListener commandListener = CommandListener.getInstance();

    public static MessageEmbed SYNTAX_ERROR = new EmbedBuilder()
            .clear()
            .setTitle("Ошибка в синтаксисе использования команды!")
            .build();


    public static MessageEmbed listUsersWithRoles(String owner, String stmod, String mod) {
        return new EmbedBuilder()
                .setTitle("Список пользователей:")
                .addField("Создатель бота: ", owner, false)
                .addField("Ст. модераторы: ", stmod, false)
                .addField("Модераторы: ", mod, false)
                .build();
    }

    public static MessageEmbed leaderBoard(String users) {
        return new EmbedBuilder()
                .setColor(Color.ORANGE)
                .addField("Таблица лидеров: ", users, false)
                .build();
    }
}
