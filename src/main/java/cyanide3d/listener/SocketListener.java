package cyanide3d.listener;

import cyanide3d.conf.Config;
import cyanide3d.handlers.socket.MessageDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketListener extends Thread {

    private final ExecutorService executor;
    int port = Integer.parseInt(Config.getInstance().getListenerPort());
    private final Logger logger = LoggerFactory.getLogger(SocketListener.class);



    public SocketListener() {
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        logger.info("Discord listener launched on " + port + " port...");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
//                if (!EnableActionService.getInstance().getState("vkdiscord")) {
//                    return;
//                }
                executor.execute(new MessageDispatcher(socket));
            }
        } catch (Exception e) {
            logger.error("Socket interrupt.... Restart.", e);
            run();
        }
    }

}
