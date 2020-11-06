package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;

import java.util.Locale;

public class Maps extends Command {
    private Localization localization = new Localization(new Locale("ru", "RU"));

    public Maps(){
        this.name = "maps";
        this.help = "Показывает план карт с названием точек.";
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply(localization.getMessage("warface.maps"));
    }
}