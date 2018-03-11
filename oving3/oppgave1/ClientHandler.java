package oppgave1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        /* Åpner strømmer for kommunikasjon med klientprogrammet */
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            /* Sender innledning til klienten */
            out.println("Hei, du har kontakt med tjenersiden!");
            out.println("Skriv et regnestykke av type addisjon eller subtraksjon, avslutt med linjeskift.");

            /* Mottar data fra klienten */
            String linje = in.readLine();  // mottar en linje med tekst
            while (linje != null) {  // forbindelsen på klientsiden er lukket
                System.out.println("En klient skrev: " + linje);

                if (linje.contains("+")) {
                    String[] parts = linje.split("\\+");
                    double tall1 = Double.parseDouble(parts[0].trim());
                    double tall2 = Double.parseDouble(parts[1].trim());
                    out.println("Resultatet er: " + (tall1 + tall2));
                }
                else if (linje.contains("-")) {
                    String[] parts = linje.split("-");
                    double tall1 = Double.parseDouble(parts[0].trim());
                    double tall2 = Double.parseDouble(parts[1].trim());
                    out.println("Resultatet er: " + (tall1 - tall2));
                }

                linje = in.readLine();
            }

            System.out.println("Lukket tilkobling til klient");

        } catch (IOException ignored) {
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
