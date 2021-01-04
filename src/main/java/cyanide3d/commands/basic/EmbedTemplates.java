package cyanide3d.commands.basic;

import cyanide3d.Localization;
import cyanide3d.listener.CommandClientManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class EmbedTemplates {

    public static final MessageEmbed SYNTAX_ERROR = new EmbedBuilder()
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
                .addField("", users, false)
                .build();
    }
}
