package cyanide3d.util;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class LevelTemplateCreator {
    public void makeTemplate(String username, int userLvl, int userExp, String avatarUrl, String templateName) {
        getUserAvatar(avatarUrl);

        ConvertCmd cmd = new ConvertCmd();
        cmd.setSearchPath("C:\\ImageMagick-7.0.10-Q16-HDRI");
        try {
            cmd.run(prepareTemplate(username, userLvl, userExp, templateName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @NotNull
    private IMOperation prepareTemplate(String username, int userLvl, int userExp, String templateName) {
        IMOperation template = new IMOperation();
        template.addSubOperation(impAvatarOnTemplate(templateName));
        template.pointsize(26);
        template.font("Impact");
        template.fill("White");
        template.draw(String.format("text 150,48 '%s'", username));
        template.pointsize(23);
        template.draw(String.format("text 260,90 'level %s'", userLvl));
        template.draw(String.format("text 150,90 'XP %2d/%d'", userExp, 15 + userLvl * 2));
        template.addImage("picture\\output.png");
        return template;
    }

    @NotNull
    private IMOperation impAvatarOnTemplate(String templateName) {
        IMOperation avatar = new IMOperation();
        avatar.addImage("picture\\" + templateName + ".png");
        avatar.addSubOperation(prepareUserAvatar());
        avatar.geometry(100, 100, 24, 10);
        avatar.composite();
        return avatar;
    }

    @NotNull
    private IMOperation prepareUserAvatar() {
        IMOperation cutAvatar = new IMOperation();
        cutAvatar.addImage("picture\\userAvatar.png");
        cutAvatar.resize(100);
        cutAvatar.background("Grey");
        cutAvatar.vignette(0.0, 0.0, 0.0, 0.0);
        cutAvatar.transparent("Grey");
        return cutAvatar;
    }

    public void getUserAvatar(String avatarUrl) {
        try {
            URL url = new URL(avatarUrl);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File("picture\\userAvatar.png"));

            int ch;
            while ((ch = bufferedInputStream.read()) != -1) {
                fileOutputStream.write(ch);
            }
            bufferedInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
