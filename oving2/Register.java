/*
 * Klassen Utstyr er mutabel. Antall på lager og nedre grense for
 * bestilling kan endres.
 */
class UtstyrImpl implements Utstyr {
    public static final int bestillingsfaktor = 5;
    private int nr;  // entydig identifikasjon
    private String betegnelse;
    private String leverandør;
    private int påLager;     // mengde på lager
    private int nedreGrense;

    public UtstyrImpl(int startNr, String startBetegnelse, String startLeverandør,
                      int startPåLager, int startNedreGrense) {
        nr = startNr;
        betegnelse = startBetegnelse;
        leverandør = startLeverandør;
        påLager = startPåLager;
        nedreGrense = startNedreGrense;
    }

    @java.lang.Override
    public int finnNr() {
        return nr;
    }

    @java.lang.Override
    public String finnBetegnelse() {
        return betegnelse;
    }

    @java.lang.Override
    public String finnLeverandør() {
        return leverandør;
    }

    @java.lang.Override
    public int finnPåLager() {
        return påLager;
    }

    @java.lang.Override
    public int finnNedreGrense() {
        return nedreGrense;
    }

    @java.lang.Override
    public int finnBestKvantum() {
        if (påLager < nedreGrense) return bestillingsfaktor * nedreGrense;
        else return 0;
    }

    /*
     * Endringen kan være positiv eller negativ. Men det er ikke
     * mulig å ta ut mer enn det som fins på lager. Hvis klienten
     * prøver på det, vil metoden returnere false, og intet uttak gjøres.
     */
    @java.lang.Override
    public boolean endreLagerbeholdning(int endring) {
        System.out.println("Endrer lagerbeholdning, utstyr nr " + nr + ", endring: " + endring);
        if (påLager + endring < 0) return false;
        else {
            påLager += endring;
            return true;
        }
    }

    @java.lang.Override
    public void settNedreGrense(int nyNedreGrense) {
        nedreGrense = nyNedreGrense;
    }

    @java.lang.Override
    public String toString() {
        String resultat = "Nr: " + nr + ", " +
                "Betegnelse: " + betegnelse + ", " + "Leverandør: " +
                leverandør + ", " + "På lager: " + påLager + ", " +
                "Nedre grense: " + nedreGrense;
        return resultat;
    }
}

/*
 *
 * Et register holder orden på en mengde Utstyrsobjekter. En klient kan legge inn nye
 * Utstyr-objekter i registeret, og også endre varebeholdningen for et
 * allerede registrert objekt. Bestillingsliste for alle varene kan lages.
 */
class Register {
    public static final int ok = -1;
    public static final int ugyldigNr = -2;
    public static final int ikkeNokPåLager = -3;

    private ArrayList<Utstyr> registeret = new ArrayList<Utstyr>();

    @java.lang.Override
    public boolean regNyttUtstyr(int startNr, String startBetegnelse,
                                 String startLeverandør, int startPåLager, int startNedreGrense) {
        if (finnUtstyrindeks(startNr) < 0) { // fins ikke fra før
            Utstyr nytt = new Utstyr(startNr, startBetegnelse, startLeverandør,
                    startPåLager, startNedreGrense);
            registeret.add(nytt);
            return true;
        } else {
            return false;
        }
    }

    @java.lang.Override
    public int endreLagerbeholdning(int nr, int mengde) {
        int indeks = finnUtstyrindeks(nr);
        if (indeks < 0) return ugyldigNr;
        else {
            if (!(registeret.get(indeks)).endreLagerbeholdning(mengde)) {
                return ikkeNokPåLager;
            } else return ok;
        }
    }

    private int finnUtstyrindeks(int nr) {
        for (int i = 0; i < registeret.size(); i++) {
            int funnetNr = (registeret.get(i)).finnNr();
            if (funnetNr == nr) return i;
        }
        return -1;
    }

    @java.lang.Override
    public String lagBestillingsliste() {
        String resultat = "\n\nBestillingsliste:\n";
        for (int i = 0; i < registeret.size(); i++) {
            Utstyr u = registeret.get(i);
            resultat += u.finnNr() + ", " + u.finnBetegnelse() + ": " +
                    u.finnBestKvantum() + "\n";
        }
        return resultat;
    }

    @java.lang.Override
    public String lagDatabeskrivelse() {
        String resultat = "Alle data:\n";
        for (int i = 0; i < registeret.size(); i++) {
            resultat += (registeret.get(i)).toString() + "\n";
        }
        return resultat;
    }
}