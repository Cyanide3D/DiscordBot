package cyanide3d.service;


public class BlackListService {

    private static BlackListService instance;

    private BlackListService() {
    }

    public static BlackListService getInstance() {
        if (instance == null) {
            instance = new BlackListService();
        }
        return instance;
    }

    public void add(String nickname, String reason) {
        //TODO really add user to blacklist
    }
}
