import java.io.Serializable;

/**
 * Klassen Utstyr er mutabel. Antall på lager og nedre grense for
 * bestilling kan endres.
 */
public class UtstyrImpl implements Utstyr {
    public static final int bestillingsfaktor = 5;
    private int nr;  // entydig identifikasjon
    private String betegnelse;
    private String leverandor;
    private int paaLager;     // mengde på lager
    private int nedreGrense;

    public UtstyrImpl(int startNr, String startBetegnelse, String startLeverandor,
                      int startPaaLager, int startNedreGrense) {
        nr = startNr;
        betegnelse = startBetegnelse;
        leverandor = startLeverandor;
        paaLager = startPaaLager;
        nedreGrense = startNedreGrense;
    }

    public int finnNr() {
        return nr;
    }

    public String finnBetegnelse() {
        return betegnelse;
    }

    public String finnLeverandor() {
        return leverandor;
    }

    public int finnPaaLager() {
        return paaLager;
    }

    public int finnNedreGrense() {
        return nedreGrense;
    }

    public int finnBestKvantum() {
        if (paaLager < nedreGrense) {
            return bestillingsfaktor * nedreGrense;
        }
        else {
            return 0;
        }
    }

    /**
     * Endringen kan være positiv eller negativ. Men det er ikke
     * mulig å ta ut mer enn det som fins på lager. Hvis klienten
     * prøver på det, vil metoden returnere false, og intet uttak gjøres.
     */
    public boolean endreLagerbeholdning(int endring) {
        System.out.println("Endrer lagerbeholdning, utstyr nr " + nr + ", endring: " + endring);
        if (paaLager + endring < 0) {
            return false;
        }
        else {
            paaLager += endring;
            return true;
        }
    }

    public void settNedreGrense(int nyNedreGrense) {
        nedreGrense = nyNedreGrense;
    }

    public String toString() {
        return "Nr: " + nr + ", " +
                "Betegnelse: " + betegnelse + ", " + "Leverandør: " +
                leverandor + ", " + "På lager: " + paaLager + ", " +
                "Nedre grense: " + nedreGrense;
    }
}