package oppgave1;

import java.io.*;
import java.net.*;

class SocketTjener {
    public static void main(String[] args) throws IOException {
        final int PORTNR = 1250;

        ServerSocket tjener = new ServerSocket(PORTNR);
        System.out.println("Logg for tjenersiden. Nå venter vi...");
        Socket forbindelse;

        while ((forbindelse = tjener.accept()) != null) { // venter inntil noen tar kontakt
            System.out.println("Tilkobling fra ny klient");
            new ClientHandler(forbindelse).start();
        }

        tjener.close();
    }
}
