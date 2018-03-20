package oppgaver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class SocketTjener {
    public static void main(String[] args) throws IOException {
        final int PORTNR = 80;

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
