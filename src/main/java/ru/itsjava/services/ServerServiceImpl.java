package ru.itsjava.services;

import lombok.SneakyThrows;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerServiceImpl implements ServerService{
    public final static int PORT = 8081;
    public final List<Observer> observers  = new ArrayList<>();
    @SneakyThrows
    @Override
    public void start() {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("=== SERVER STARTS ===");
        while(true) {
            Socket socket = serverSocket.accept();
            if (socket != null) {
                Thread thread = new Thread(new ClientRunnable(socket, this));
                thread.start();
            }
        }
    }
    @Override
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer){
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message){
        for (Observer observer: observers) {
            observer.notifyMe(message);
        }
    }

    @Override
    public void notifyObserversExpectMe(String message, Observer observerWithoutMessage) {
        for (Observer notifyMeObserver: observers) {
        if (!(observerWithoutMessage.equals(notifyMeObserver))) {
            notifyMeObserver.notifyMe(message);
        }
        }
    }


}
