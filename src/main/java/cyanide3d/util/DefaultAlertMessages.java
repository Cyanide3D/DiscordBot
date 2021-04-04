package cyanide3d.util;

import java.awt.*;
import java.util.Random;

public class DefaultAlertMessages {

    private static final String[] joinGifs = {
            "https://cdn.discordapp.com/attachments/573773778480398337/771325629491707924/tenor.gif",
            "https://cdn.discordapp.com/attachments/573773778480398337/771325641009135626/tenor_1.gif",
            "https://i.gifer.com/EIGB.gif",
            "https://i.gifer.com/D85T.gif",
            "https://i.gifer.com/Pvm.gif"
    };

    private static final String[] statementGifs = {
            "https://media3.giphy.com/media/WV4YdUfCxDfwA5MH0Q/giphy.gif?cid=ecf05e474fb24ae3998bcd07410214fdbc0ba947138f297a&rid=giphy.gif",
            "https://i.gifer.com/Mucj.gif", "https://i.gifer.com/E04.gif", "https://i.gifer.com/7Wx1.gif"
    };

    private static final String[] levelUpMessages = {
            "получил(a) новый уровень!", "прокачал себя!", "стал сильнее!"
    };

    private static final String[] wfStatGifs = {
            "https://i.gifer.com/np2.gif", "https://media.discordapp.net/attachments/614472783715500052/767371392110297088/good_bye_1.gif.gif",
            "https://i.gifer.com/DriV.gif", "https://i.gifer.com/Mucj.gif", "https://i.gifer.com/E04.gif"
    };

    private static final String[] leaveGifs = {
            "https://media.discordapp.net/attachments/614472783715500052/767371392466812938/tenor.gif.gif",
            "https://media.discordapp.net/attachments/614472783715500052/767371396354408458/good_bye_2.gif.gif",
            "https://i.gifer.com/53HC.gif", "https://i.gifer.com/9TEx.gif", "https://i.gifer.com/7A25.gif",
            "https://cdn.discordapp.com/attachments/614472783715500052/767371392110297088/good_bye_1.gif.gif"
    };

    private static final String[] joinTitles = {
            "Добро пожаловать на наш сервер!",
            "Поприветствуем!"
    };

    private static final String[] joinBodies = {
            "{mention} приземлился к нам на сервер!",
            "{mention} зашёл к нам в гости!",
            "{mention} заспавнился на сервере!",
            "{mention} десантировался на сервер!"
    };

    private static final String[] leaveTitles = {
            "{username} покинул наш сервер :(",
            "{username} ушёл от нас :(",
            "{username} дизентегрировался :(",
            "{username} ушёл в отставу."
    };

    private static final String[] leaveBodies = {
            "Надеюсь, еще увидимся!",
            "Прощай.",
            "Не забывай нас :)"
    };

    public static String getJoinLeaveEventTitle(ActionType type) {
        return type.equals(ActionType.JOIN)
                ? getRandArrayItem(joinTitles)
                : getRandArrayItem(leaveTitles);
    }

    public static String getJoinLeaveEventBody(ActionType type) {
        return type.equals(ActionType.JOIN)
                ? getRandArrayItem(joinBodies)
                : getRandArrayItem(leaveBodies);
    }

    public static String getStatementEventImage() {
        return getRandArrayItem(statementGifs);
    }

    public static String getWfStatImage() {
        return getRandArrayItem(wfStatGifs);
    }

    public static String getLevelUpMessage(String substring) {
        return substring + " " + getRandArrayItem(levelUpMessages);
    }

    public static Color getEmbedColorToJoinLeaveMessage(ActionType type) {
        return type.equals(ActionType.JOIN) ? Color.green : Color.red;
    }

    public static String getJoinLeaveEventImage(ActionType type) {
        return type.equals(ActionType.JOIN)
                ? getRandArrayItem(joinGifs)
                : getRandArrayItem(leaveGifs);
    }

    private static String getRandArrayItem(String[] array) {
        return array[new Random().nextInt(array.length)];
    }

}
