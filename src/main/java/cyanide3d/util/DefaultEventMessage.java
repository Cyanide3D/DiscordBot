package cyanide3d.util;

import java.util.Random;

public class DefaultEventMessage {

    private static final String[] joinGifs = {"https://cdn.discordapp.com/attachments/573773778480398337/771325629491707924/tenor.gif",
            "https://cdn.discordapp.com/attachments/573773778480398337/771325641009135626/tenor_1.gif",
            "https://i.gifer.com/EIGB.gif",
            "https://i.gifer.com/D85T.gif",
            "https://i.gifer.com/Pvm.gif"};

    private static final String[] leaveGifs = {"https://media.discordapp.net/attachments/614472783715500052/767371392466812938/tenor.gif.gif",
            "https://media.discordapp.net/attachments/614472783715500052/767371396354408458/good_bye_2.gif.gif",
            "https://i.gifer.com/53HC.gif", "https://i.gifer.com/9TEx.gif", "https://i.gifer.com/7A25.gif",
            "https://cdn.discordapp.com/attachments/614472783715500052/767371392110297088/good_bye_1.gif.gif"};

    private static final String[] joinTitles = {""};

    private static final String[] joinBodies = {""};

    private static final String[] leaveTitles = {""};

    private static final String[] leaveBodies = {""};

    public static String joinEventTitle() {
        return getRandArrayItem(joinTitles);
    }

    public static String joinEventBody() {
        return getRandArrayItem(joinBodies);
    }

    public static String joinEventImage() {
        return getRandArrayItem(joinGifs);
    }

    public static String leaveEventTitle() {
        return getRandArrayItem(leaveTitles);
    }

    public static String leaveEventBody() {
        return getRandArrayItem(leaveBodies);
    }

    public static String leaveEventImage() {
        return getRandArrayItem(leaveGifs);
    }

    private static String getRandArrayItem(String[] array) {
        return array[new Random().nextInt(array.length)];
    }

}
