package org.example.services;

public interface ByteFetcher {
    void start();
    void stop();
    boolean inProgress();
    byte[] getBytes();
    void drop();

}
