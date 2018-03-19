package oppgave2;

import javax.persistence.*;

public class Konto {
    @Id
    private long kontonr;
    private double saldo;
    private String eier;
}
