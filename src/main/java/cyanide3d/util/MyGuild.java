package cyanide3d.util;

import net.dv8tion.jda.api.entities.Guild;

public class MyGuild {
    static private MyGuild instance;

    Guild guild;

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public static MyGuild getInstance(){
        if (instance == null) instance = new MyGuild();
        return instance;
    }
}
