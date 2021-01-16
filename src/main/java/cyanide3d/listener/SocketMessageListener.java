package cyanide3d.listener;

import cyanide3d.conf.Config;
import cyanide3d.handlers.VerifyMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketMessageListener extends Thread {
    ServerSocket serverSocket;
    BufferedReader buffer;
    ExecutorService executor;
    int port = Integer.parseInt(Config.getInstance().getListenerPort());
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public SocketMessageListener() {
       executor = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        logger.info("Discord listener launched on " + port + " port...");
        try{
            serverSocket = new ServerSocket(port);
        } catch (Exception e){
            System.out.println("Socket is down nahoy");
            run();
        }
        while (true) {
            try {
                listener();
            } catch (IOException e) {
                System.out.println("Thread interrupt, try again...");
            }
        }
    }

    private void listener() throws IOException {
        Socket socket = serverSocket.accept();
        buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        executor.execute(new VerifyMessageHandler(buffer));
    }
}
