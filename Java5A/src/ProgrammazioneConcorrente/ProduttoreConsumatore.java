package ProgrammazioneConcorrente;

/**
 * Soluzione in Java del problema classico del Produttore-Consumatore
 * con buffer circolare.
 */

public class ProduttoreConsumatore {
    public static void main(String[] args) throws InterruptedException {
        Buffer b = new Buffer();
//        Produttore p = new Produttore(b);
//        Consumatore c = new Consumatore(b);
//
//        p.start();
//        c.start();

        // Test con più thread
        Thread prod[] = new Thread[3];
        Thread cons[] = new Thread[3];

        for(int i = 0; i < 3; i++) {
            prod[i] = new Produttore(b);
            cons[i] = new Consumatore(b);
            prod[i].start();
            cons[i].start();
        }

        for(int i = 0; i < 3; i++) {
            prod[i].join();
            cons[i].join();
        }
    }

    public static class Produttore extends Thread {
        // Riferimento al buffer condiviso
        private Buffer b;

        public Produttore(Buffer b) {
            this.b = b;
        }

        @Override
        public void run() {
            while(true) {
                // Produce dato casuale
                Double d = Math.random() * 1000;
                try {
                    // Inserisce il dato nel buffer
                    b.inserisci(d, super.getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // Dorme un po'...la Thread.sleep() potrebbe essere
                // messa anche all'interno del try-catch precedente
                // perché "raccolgono" la stessa eccezione.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static class Consumatore extends Thread {
        // Riferimento al buffer condiviso
        private Buffer b;

        public Consumatore(Buffer b) {
            this.b = b;
        }

        @Override
        public void run() {
            while(true) {
                double d;
                try {
                    // Consuma il dato
                    d = b.consuma(super.getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    // Monitor
    public static class Buffer {
        // Dimensione del buffer
        private final int MAX = 8;
        // Buffer per la memorizzazione
        private double b[] = new double[MAX];
        // Contatori di elementi nel buffer, posizione in cui inserire/prelevare
        private int contatore = 0, inserisci = 0, preleva = 0;

        public synchronized void inserisci(double d, String s) throws InterruptedException {
            // Attendo finché il buffer è pieno
            while(contatore == MAX)
                wait();

            // Inseriamo il nuovo valore prodotto nel buffer
            b[inserisci] = d;
            // Aggiornamento circolare del buffer
            inserisci = (inserisci + 1) % MAX;

            System.out.println("Thread " + s +
                    " ha prodotto " + d);

            // Aumento il contatore perché c'è un nuovo dato nel buffer
            contatore++;
            notifyAll();
        }

        public synchronized double consuma(String s) throws InterruptedException {
            // Attendo finché il buffer è vuoto: non c'è niente da consumare
            while(contatore == 0)
                wait();

            // Prelevo e faccio l'aggiornamento (circolare) dell'indice
            double d = b[preleva];
            preleva = (preleva + 1) % MAX;

            System.out.println("Thread " + s +
                    " ha consumato " + d);

            // Decremento il contatore: ho consumato un dato
            contatore--;
            notifyAll();

            return d;
        }
    }

    /**
     * TODO:
     * 1. Sincronizzare anche la stampa all'interno dei metodi
     *      synchronized.
     *      b.inserisci(d, this);
     * 2. Testare l'esecuzione con più produttori e più consumatori.
     * 3. Provare ad imbastire il codice dei filosofi a cena in Java.
     */
}

