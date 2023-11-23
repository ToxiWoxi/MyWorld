package dev.toxi.world;

public class GCRunnable implements Runnable {

    @Override
    public void run() {
        new Thread(() -> System.gc()).start();
    }
}
