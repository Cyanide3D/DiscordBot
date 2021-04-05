package cyanide3d.util;

import cyanide3d.repository.service.MessageStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageStoreCleaner extends SimpleTimerTask{

    private final MessageStoreService storeService;
    private final Logger logger = LoggerFactory.getLogger(MessageStoreCleaner.class);

    public MessageStoreCleaner() {
        storeService = MessageStoreService.getInstance();
        logger.info("Message cleaner started...");
    }

    @Override
    public int getDelay() {
        return 0;
    }

    @Override
    public int getPeriod() {
        final int ONE_HOURS = 1000*60*60;
        return ONE_HOURS;
    }

    @Override
    public void run() {
        logger.info("Starting message cleaning...");
        storeService.clean();
    }
}
