package cyanide3d;


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
