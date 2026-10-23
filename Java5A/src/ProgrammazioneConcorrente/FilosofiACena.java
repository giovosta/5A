package ProgrammazioneConcorrente;

/**
 * Soluzione in Java del problema classico dei 5 filosofi a cena.
 *
 * Codice scarno, eventualmente aggiungere delle stampe per osservarne l'esecuzione.
 */

public class FilosofiACena {
    public static void main(String[] args) {
        Tavola t = new Tavola();
        Thread f[] = new Thread[5];
        
        for(int i = 0; i < 5; i++) {
            f[i] = new Filosofo(t, i);
            f[i].start();
        }
    }

    public static class Filosofo extends Thread {
        // Riferimento al monitor condiviso
        private Tavola t;
        // Numero identificativo del filosofo
        private int numero;

        // Costruttore
        public Filosofo(Tavola t, int i) {
            this.t = t;
            this.numero = i;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    // Pensa (eventualmente per renderlo meno deterministico usare:
                    // Thread.sleep((int)(Math.random() * 1000));
                    Thread.sleep(1000);

                    // Raccolta atomica delle bacchette
                    t.raccogliAtomico(numero);

                    // Mangia
                    Thread.sleep(1000);

                    // Rilascio atomico delle bacchette
                    t.depositaAtomico(numero);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static class Tavola {
        // Array di boolean che rappresenta la disponibilità di una singola bacchetta
        private boolean bacchette[] = {true, true, true, true, true};

        // Raccolta atomica
        public synchronized void raccogliAtomico(int i) throws InterruptedException {
            // Finché la bacchetta i-esima oppure la i-esima+1 non sono disponibili vado in attesa
            while(!bacchette[i] || !bacchette[(i+1)%5])
                wait();

            // Finita l'attesa le bacchette le posso prendere, quindi imposto a false.
            bacchette[i] = false;
            bacchette[(i+1)%5] = false;
        }

        // Rilascio atomico
        public synchronized void depositaAtomico(int i) {
            bacchette[i] = true;
            bacchette[(i+1)%5] = true;

            notifyAll();
        }
    }
}
