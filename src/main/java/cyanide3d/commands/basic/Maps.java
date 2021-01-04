package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;

public class Maps extends Command {
    private final Localization localization = Localization.getInstance();

    public Maps(){
        this.name = "maps";
        this.help = "Показывает план карт с названием точек.";
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply(localization.getMessage("warface.maps"));
    }
}