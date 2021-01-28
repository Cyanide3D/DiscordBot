package cyanide3d.handlers.socket;

import cyanide3d.conf.Logging;
import cyanide3d.service.EnableActionService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WirelessControlHandler implements SocketHandler{

    Logger logger = Logging.getLogger(this.getClass());
    private final String[] data;

    public WirelessControlHandler(String message) {
        this.data = message.split("\\|");
    }

    //0 - key
    //1 - action
    //2 - function
    //3 - state

    @Override
    public void handle() {
        try{
            if (data[1].equals("activate")) {
                EnableActionService.getInstance().setState(data[2], Boolean.parseBoolean(data[3]));
            }
        } catch (Exception e) {
            System.out.println("Error to activate function");
            logger.log(Level.WARNING, "WirelessControlHandler activate state", e);
        }
    }
}
