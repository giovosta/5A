package Threads;

public class ThreadEsempio extends Thread {
    public ThreadEsempio() {
        // chiama il costruttore della superclasse Thread
        super();
    }

    // Il metodo run() contiene il codice che sarà eseguito dal thread
    @Override
    public void run() {
        System.out.println("Thread");
    }

    public static void main(String[] args) throws InterruptedException {
        // Creazione di un oggetto di tipo ThreadEsempio
        ThreadEsempio t1 = new ThreadEsempio();

        // Chiamata del metodo run() della classe ThreadEsempio
        // Codice sincrono: chiamo il metodo e attendo il termine della sua esecuzione
        // t1.run();

        /**
         * Qui sto creando un nuovo thread!!!
         * Flusso di esecuzione indipendente da quello principale.
         * Codice asincrono: chiamo il metodo -> nuovo flusso di esecuzione indipendente
         * il mio main thread può continuare a eseguire altro codice...
         */
        t1.start();

        // Attende la terminazione del thread...
        t1.join();

        /**
         * Altri metodi per creare un thread: usare le Anonymous class
         * Una anonymous class è una classe definita senza nome, creata direttamente nel punto in cui serve.
         * In questo esempio, viene creata al volo una classe anonima che implementa l’interfaccia Runnable
         * e ridefinisce il metodo run().
         */
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread 2");
            }
        });

        /**
         * Creazione e avvio del thread senza salvataggio del riferimento restituito dalla new.
         * Il thread non può più essere referenziato successivamente nel codice: si perde il controllo
         * totale di esso. Se continua all'infinito la propria esecuzione bisogna (eventualmente)
         * ucciderlo da Gestione Attività.
         * Il garbage collector non ha effetto su questo thread, non lo "uccide".
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread 3");
            }
        }).start();
    }

    /**
     * Un altro modo per creare un thread è quello di creare una classe che
     * implementi l'interfaccia Runnable.
     * Essa contiene la definizione del metodo run() che dovrà poi essere implementato dalla classe.
     */
    public static class ThreadRunnable implements Runnable {
        @Override
        public void run() {

        }
    }
}
