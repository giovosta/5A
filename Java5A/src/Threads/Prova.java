package Threads;

public class Prova {
    public static void main(String[] args) throws InterruptedException {
        // Creazione di un oggetto di tipo ThreadEsempio
        ThreadEsempio t1 = new ThreadEsempio();

        // Chiamata del metodo run() della classe ThreadEsempio
        // t1.run();

        // Qui sto creando un nuovo thread!!!
        // Filone di esecuzione indipendente da quello principale.
        t1.start();

        // Attende la terminazione del thread...
        t1.join();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread 2");
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread 3");
            }
        }).start();
    }

    public static class ThreadRunnable implements Runnable {
        @Override
        public void run() {

        }
    }
}
