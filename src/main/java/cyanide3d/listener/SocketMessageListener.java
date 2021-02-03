package cyanide3d.listener;

import cyanide3d.conf.Config;
import cyanide3d.handlers.socket.VerifyMessageHandler;
import cyanide3d.service.EnableActionService;

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
            while (true) {
                Socket socket = serverSocket.accept();
                if (!EnableActionService.getInstance().getState("vkdiscord")) {
                    return;
                }
                executor.execute(new VerifyMessageHandler(socket));
            }
        } catch (Exception e) {
            System.out.println("Socket interrupt.... Restart.");
            run();
        }
    }

}
