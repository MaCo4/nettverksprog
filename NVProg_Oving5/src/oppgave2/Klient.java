package oppgave2;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Klient {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NVProg_Oving5PU");
        KontoDAO dao = new KontoDAO(entityManagerFactory);

        Konto konto1 = new Konto();
        konto1.setEier("Magnus");
        konto1.setKontonr(1234);
        konto1.setSaldo(1000);

        dao.lagreNyKonto(konto1);

        Konto konto2 = new Konto();
        konto2.setEier("Arne");
        konto2.setKontonr(2521);
        konto2.setSaldo(500);

        dao.lagreNyKonto(konto2);


    }
}
