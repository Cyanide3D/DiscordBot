package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Vk extends Command {
    public Vk() {
        this.name = "vk";
        this.help = "Ссылка на группу ВК нашего клана.";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        commandEvent.reply("**Группа ВК:** <https://vk.com/def1ants>\n" +
                "**Беседка ВК:** <https://vk.me/join/5haZdNdhVneQUIr43Hy249LHEuXOk8CFDwI=>\n\n" +
                "*При вступлении **обязательно** указывайте свой игровой никнейм!*");
    }
}
