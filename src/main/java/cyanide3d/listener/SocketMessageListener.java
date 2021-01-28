package cyanide3d.listener;

import cyanide3d.conf.Config;
import cyanide3d.handlers.socket.VerifyMessageHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketMessageListener extends Thread {
    ServerSocket serverSocket;
    ExecutorService executor;
    int port = Integer.parseInt(Config.getInstance().getListenerPort());

    public SocketMessageListener() {
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        System.out.println("Discord listener launched on " + port + " port...");
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("Socket is down nahoy");
            run();
        }
        while (true) {
            try {
                listener();
            } catch (Exception e) {
                System.out.println("Thread interrupt, try again...");
            }
        }
    }

    private void listener() throws IOException {
        Socket socket = serverSocket.accept();
        executor.execute(new VerifyMessageHandler(socket));
    }
}
