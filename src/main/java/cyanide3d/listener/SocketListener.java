package cyanide3d.listener;

import cyanide3d.Configuration;
import cyanide3d.handlers.socket.MessageDispatcher;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketListener extends Thread {

    private final ExecutorService executor;
    private final JDA jda;
    int port = Integer.parseInt(Configuration.getInstance().getListenerPort());
    private final Logger logger = LoggerFactory.getLogger(SocketListener.class);



    public SocketListener(JDA jda) {
        this.jda = jda;
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        logger.info("Discord listener launched on " + port + " port...");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                executor.execute(new MessageDispatcher(socket, jda));
            }
        } catch (Exception e) {
            logger.error("Socket interrupt.... Restart.", e);
            run();
        }
    }

}
