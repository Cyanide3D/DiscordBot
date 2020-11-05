package cyanide3d.commands;

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
                "**Беседка ВК:** <https://vk.me/join/2JbzWKbPzqLVSVATr6ixdfsrsUD7e/bS4LI=>\n\n" +
                "*При вступлении **обязательно** указывайте свой игровой никнейм!*");
    }
}
