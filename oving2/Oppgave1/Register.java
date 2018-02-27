import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Register extends Remote {

    boolean regNyttUtstyr(int startNr, String startBetegnelse, String startLeverandor, int startPaaLager, int startNedreGrense) throws RemoteException;

    int endreLagerbeholdning(int nr, int mengde) throws RemoteException;

    String lagBestillingsliste() throws RemoteException;

    String lagDatabeskrivelse() throws RemoteException;
}
