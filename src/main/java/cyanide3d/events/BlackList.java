package cyanide3d.events;

import cyanide3d.Localization;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Locale;


public class BlackList {

    private static BlackList instance;

    private BlackList() {
    }

    public static BlackList getInstance() {
        if (instance == null) {
            instance = new BlackList();
        }
        return instance;
    }

    public void add(String nickname, String reason) {
        //TODO really add user to blacklist
        throw new UnsupportedOperationException("WiP");
    }
}
