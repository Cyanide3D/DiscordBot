package cyanide3d.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class EmbedTemplates {

    public static MessageEmbed MENU = new EmbedBuilder()
            .setTitle("Возможные команды для использзования:")
            .addField("Добавление роли полномочий:", "$settings role add", true)
            .addField("Изменение роли полномочий:", "$settings role change", false)
            .addField("Удаление полномочий роли:", "$settings role delete", true)
            .addField("Список ролей и их полномочий:", "$settings role list", false)
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
}
