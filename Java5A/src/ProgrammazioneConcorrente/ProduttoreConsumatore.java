package ProgrammazioneConcorrente;

public class ProduttoreConsumatore {
    public static void main(String[] args) {
        Buffer b = new Buffer();
        Produttore p = new Produttore(b);
        Consumatore c = new Consumatore(b);

        p.start();
        c.start();
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
                    b.inserisci(d);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread " + super.getName() +
                        " ha prodotto " + d);

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
                    d = b.consuma();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread " + super.getName() +
                        " ha consumato " + d);
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

        public synchronized void inserisci(double d) throws InterruptedException {
            // Attendo finché il buffer è pieno
            while(contatore == MAX)
                wait();

            // Inseriamo il nuovo valore prodotto nel buffer
            b[inserisci] = d;
            // Aggiornamento circolare del buffer
            inserisci = (inserisci + 1) % MAX;

            // Aumento il contatore perché c'è un nuovo dato nel buffer
            contatore++;
            notifyAll();
        }

        public synchronized double consuma() throws InterruptedException {
            // Attendo finché il buffer è vuoto: non c'è niente da consumare
            while(contatore == 0)
                wait();

            // Prelevo e faccio l'aggiornamento (circolare) dell'indice
            double d = b[preleva];
            preleva = (preleva + 1) % MAX;

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

