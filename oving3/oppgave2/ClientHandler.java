package oppgave2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

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

            ArrayList<String> request = new ArrayList<>();
            /* Mottar data fra klienten */
            String linje = in.readLine();  // mottar en linje med tekst
            while (linje != null) {  // forbindelsen på klientsiden er lukket
                System.out.println("En klient skrev: " + linje);

                if (linje.equals("")) {
                    // Skriv header. Linjene skilles med CRLF
                    out.print("HTTP/1.0 200 OK\r\n");
                    out.print("Content-type: text/html; charset=UTF-8\r\n");
                    out.print("\r\n");

                    // Skriv body
                    out.println("<html><body>");
                    out.println("<h1>Forespørselen din var:</h1>");
                    out.println("<ul>");
                    for (String header : request) {
                        out.println("<li>" + header + "</li>");
                    }
                    out.println("</ul>");
                    out.println("</body></html>");

                    break;
                }

                request.add(linje);
                linje = in.readLine();
            }

        } catch (IOException ignored) {
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }

        System.out.println("Lukket tilkobling til klient");
    }
}
