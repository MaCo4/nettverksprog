import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Utstyr extends Remote {

    int finnNr();

    String finnBetegnelse();

    String finnLeverandor();

    int finnPaaLager();

    int finnNedreGrense();

    int finnBestKvantum();

    /**
     * Endringen kan være positiv eller negativ. Men det er ikke
     * mulig å ta ut mer enn det som fins på lager. Hvis klienten
     * prøver på det, vil metoden returnere false, og intet uttak gjøres.
     */
    boolean endreLagerbeholdning(int endring);

    void settNedreGrense(int nyNedreGrense);

    String toString();
}
