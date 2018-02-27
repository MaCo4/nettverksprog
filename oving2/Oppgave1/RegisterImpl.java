import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Et register holder orden på en mengde Utstyrsobjekter. En klient kan legge inn nye
 * Utstyr-objekter i registeret, og også endre varebeholdningen for et
 * allerede registrert objekt. Bestillingsliste for alle varene kan lages.
 */
public class RegisterImpl extends UnicastRemoteObject implements Register {
    public static final int ok = -1;
    public static final int ugyldigNr = -2;
    public static final int ikkeNokPaaLager = -3;

    private ArrayList<Utstyr> registeret = new ArrayList<>();

    public RegisterImpl() throws RemoteException {

    }

    public synchronized boolean regNyttUtstyr(int startNr, String startBetegnelse,
                                 String startLeverandor, int startPaaLager, int startNedreGrense) {
        if (finnUtstyrindeks(startNr) < 0) { // fins ikke fra før
            Utstyr nytt = new UtstyrImpl(startNr, startBetegnelse, startLeverandor,
                    startPaaLager, startNedreGrense);
            registeret.add(nytt);
            return true;
        }
        else {
            return false;
        }
    }

    public synchronized int endreLagerbeholdning(int nr, int mengde) {
        int indeks = finnUtstyrindeks(nr);
        if (indeks < 0) return ugyldigNr;
        else {
            if (!(registeret.get(indeks)).endreLagerbeholdning(mengde)) {
                return ikkeNokPaaLager;
            }
            else {
                return ok;
            }
        }
    }

    private synchronized int finnUtstyrindeks(int nr) {
        for (int i = 0; i < registeret.size(); i++) {
            int funnetNr = (registeret.get(i)).finnNr();
            if (funnetNr == nr) {
                return i;
            }
        }
        return -1;
    }

    public synchronized String lagBestillingsliste() {
        String resultat = "\n\nBestillingsliste:\n";
        for (int i = 0; i < registeret.size(); i++) {
            Utstyr u = registeret.get(i);
            resultat += u.finnNr() + ", " + u.finnBetegnelse() + ": " + u.finnBestKvantum() + "\n";
        }
        return resultat;
    }

    public synchronized String lagDatabeskrivelse() {
        String resultat = "Alle data:\n";
        for (int i = 0; i < registeret.size(); i++) {
            resultat += "    " + (registeret.get(i)).toString() + "\n";
        }
        return resultat;
    }
}