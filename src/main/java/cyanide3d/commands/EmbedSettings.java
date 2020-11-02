package cyanide3d.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class EmbedSettings {

    EmbedBuilder eb = new EmbedBuilder();

    public void embedSettingsMenu(CommandEvent e) {
        eb.clear();
        eb.setTitle("Возможные команды для использзования:");
        eb.addField("Добавление роли полномочий:", "$settings role add", true);
        eb.addField("Изменение роли полномочий:", "$settings role change", false);
        eb.addField("Удаление полномочий роли:", "$settings role delete", true);
        eb.addField("Список ролей и их полномочий:", "$settings role list", false);
        e.reply(eb.build());
    }
    public void embedAddRolePermissionSynt(CommandEvent e){
        eb.clear();
        eb.setTitle("Ошибка в синтаксисе использования команды!");
        eb.addField("", "Используйте: ***$settings role add @role permission***", false);
        e.reply(eb.build());
    }
    public void embedListUsersWithRoles(CommandEvent e, StringBuilder owner, StringBuilder stmod, StringBuilder mod){
        eb.clear();
        eb.setTitle("Список пользователей:");
        eb.addField("Создатель бота: ",owner.toString(),false);
        eb.addField("Ст. модераторы: ",stmod.toString(),false);
        eb.addField("Модераторы: ",mod.toString(),false);
        e.reply(eb.build());
    }
}
