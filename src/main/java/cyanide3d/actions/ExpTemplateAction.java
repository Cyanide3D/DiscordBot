package cyanide3d.actions;

import net.coobird.thumbnailator.Thumbnails;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ExpTemplateAction {
    public void makeTemplate(String username, String userLvl, String userExp, String avatarUrl) {
        //getUserAvatar(avatarUrl);
        /*IMOperation avatar = new IMOperation();
        avatar.addImage("template.png");
        avatar.addImage("userAvatar.jpg");
        avatar.gravity("center");
        avatar.geometry(200, 300, -250, 70);
        avatar.composite();*/

        IMOperation template = new IMOperation();
        //template.addSubOperation(avatar);
        template.addImage("picture\\template.png");
        template.pointsize(30);
        template.font("Impact");
        template.fill("White");
        template.draw(String.format("text 110,50 '%s'",username));
        template.draw(String.format("text 240,100 '%s уровень'",userLvl));
        template.draw(String.format("text 50,100 'Опыт %2d/%d'",Integer.parseInt(userExp),15+Integer.parseInt(userLvl)*2));
        template.addImage("picture\\output.jpg");


        ConvertCmd cmd = new ConvertCmd();
        cmd.setSearchPath("C:\\\\ImageMagick-7.0.10-Q16-HDRI");
        try {
            cmd.run(template);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getUserAvatar(String avatarUrl) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(avatarUrl).openConnection().getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File("userAvatar.jpg"));

            int ch;
            while ((ch = bufferedInputStream.read()) != -1) {
                fileOutputStream.write(ch);
            }
            bufferedInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
