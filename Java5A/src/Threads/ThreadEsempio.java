package Threads;

public class ThreadEsempio extends Thread {
    public ThreadEsempio() {
        // chiama il costruttore della superclasse Thread
        super();
    }

    @Override
    public void run() {
        System.out.println("Thread");
    }
}

