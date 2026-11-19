package ProgrammazioneDiRete;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TrovaIP {
    static void main(String[] args) throws UnknownHostException {
        // Indirizzo IP risultante dalla risoluzione
        String host;
        // Lettura da tastiera
        Scanner input = new Scanner(System.in);
        // Oggetto che contiene le informazioni sulla risoluzione
        InetAddress address;

        System.out.println("\nInserisci hostname: ");
        host = input.next();

        // Risoluzione dell'hostname
        address = InetAddress.getByName(host);
        System.out.println("Stampa usando getHostAddress(): " +
                address.getHostAddress());

        System.out.println("Stampa usando toString(): " +
                address);

        // IP locale
        InetAddress localAddress = InetAddress.getLocalHost();
        System.out.println("Indirizzo IP locale: " + localAddress);
        System.out.println("Indirizzo di loopback: " +
                InetAddress.getLoopbackAddress());
    }
}
