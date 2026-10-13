package Threads;

public class SincronizzazioneThread {
    // Il main crea i thread, attende la terminazione e stampa il contatore
    public static void main(String[] args) throws InterruptedException {
        // Singolo monitor condiviso
        Contatore c = new Contatore();

        Thread t1 = new SyncThread(c);
        Thread t2 = new SyncThread(c);
        Thread t3 = new SyncThread(c);

        // Avvio dei thread
        t1.start();
        t2.start();
        t3.start();

        // Attesa di terminazione
        t1.join();
        t2.join();
        t3.join();

//        Thread[] t = new Thread[5];
//        for(int i = 0; i < 5; i++) {
//            t[i] = new SyncThread(c);       // Passaggio monitor ai thread
//            t[i].start();
//        }
//
//        // Attesa della terminazione
//        for(int i = 0; i < 5; i++)
//            t[i].join();

        // Stampa il valore finale del contatore
        System.out.println("Contatore " + c.leggi());
    }

    public static class SyncThread extends Thread {
        // Massimo numero di incrementi che ciascun thread può eseguire
        private static int MAX = 1000000;
        // Riferimento al monitor condiviso
        private Contatore c;
        // Incrementi effettuati
        private int cont;

        // Costruttore, memorizza il monitor nel campo c
        public SyncThread(Contatore c) {
            this.c = c;
            cont = 0;
        }

        // I thread incrementano MAX volte il contatore
        // NOTA: non possono fare c.count++ perché è privato!
        @Override
        public void run() {
            while(cont < MAX) {
                // questo metodo è atomico e rappresenta la sezione critica perché synchronized
                c.incrementa();
                System.out.println("Thread " + super.getName() +
                        "ha portato il contatore a " + c.leggi());
                cont++;
            }
        }
    }

    /** Questa classe implementa un Monitor in cui è possibile incrementare il valore
     *  di un contatore da diversi thread in mutua esclusione.
     *  Provare a togliere il 'synchronized' dal metodo incrementa per osservare
     *  le interferenze.
     */
    public static class Contatore {
        // Variabile privata: no accessi diretti!
        private int c;

        // Costruttore anche inutile, si può fare anche direttamente private int c = 0;
        public Contatore() {
            c = 0;
        }

        // Il metodo synchronized garantisce mutua esclusione sullo stesso oggetto
//        public synchronized void incrementa() {
//            c++;
//        }

        // Equivalente al metodo incrementa() sopra
        public void incrementa() {
            synchronized (this) {
                c++;
            }
        }

        // Non serve sincronizzare questo metodo visto che è usato alla fine dal main:
        // gli altri thread sono già terminati (la join garantisce che il main è l'unico
        // thread in esecuzione), inoltre la lettura non crea mai interferenze.
        public int leggi() {
            return c;
        }
    }
}