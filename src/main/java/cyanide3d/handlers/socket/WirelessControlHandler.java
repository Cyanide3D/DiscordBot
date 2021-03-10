package cyanide3d.handlers.socket;

import cyanide3d.service.ChannelManagmentService;
import cyanide3d.service.EnableActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WirelessControlHandler implements SocketHandler{

    Logger logger = LoggerFactory.getLogger(WirelessControlHandler.class);
    private final String[] data;

    public WirelessControlHandler(String message) {
        this.data = message.split("\\|");
    }

    //0 - key
    //1 - action
    //2 - function
    //3 - state

    @Override
    public String handle() {
        try{
            switch (data[1]) {
                case "activate":
                    EnableActionService.getInstance().setState(data[2], Boolean.parseBoolean(data[3]));
                    break;
                case "getchannels":
                    return new PrepareChannelListHandler().handle();
                case "changechannel":
                    ChannelManagmentService.getInstance().addChannel(data[2], data[3]);
                    break;
                default:
                    throw new UnsupportedOperationException("data[1] unsupported operation " + data[1]);
            }
        } catch (Exception e) {
            logger.error("WirelessControlHandler activate state", e);
        }
        return null;
    }
}
