package cyanide3d.commands;

import cyanide3d.Localization;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Locale;

public class EmbedTemplates {

    private static Localization localization = new Localization(new Locale("ru", "RU"));

    public static MessageEmbed MENU = new EmbedBuilder()
            .setTitle("Возможные команды для использзования:")
            .addField("Добавление роли полномочий:", "$settings role add", true)
            .addField("Изменение роли полномочий:", "$settings role change", false)
            .addField("Удаление полномочий роли:", "$settings role delete", true)
            .addField("Список ролей и их полномочий:", "$settings role list", false)
            .addField("", "", false)
            .addField("Добавление действия на канал:", "$settings channel add", false)
            .addField("Изменение канала для действия:", "$settings channel change", false)
            .addField("Удаление канала для действия:", "$settings channel delete", false)
            .build();

    public static MessageEmbed HELP_ALL = new EmbedBuilder()
            .setTitle("Доступные команды бота:")
            .setColor(Color.ORANGE)
            .addField(":bulb:Основные команды:bulb:",localization.getMessage("help.all"),false)
            .addField(":game_die:Фановые команды:game_die:",localization.getMessage("help.fun"),false)
            .build();

    public static MessageEmbed HELP_MOD = new EmbedBuilder()
            .setColor(Color.ORANGE)
            .addField(":octagonal_sign:Команды для модерирования:octagonal_sign:",localization.getMessage("help.mod"),false)
            .build();

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
