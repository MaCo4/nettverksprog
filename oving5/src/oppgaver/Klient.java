package oppgaver;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Klient {

    public static void main(String[] args) throws Exception {
        //oppgave2();
        //oppgave3();
        oppgave4();
    }

    public static void oppgave2() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Oving5PU");
        KontoDAO dao = new KontoDAO(entityManagerFactory);


        // Opprett to kontoer og lagre til databasen
        Konto konto1 = new Konto();
        konto1.setKontonr(1234);
        konto1.setEier("Magnus");
        konto1.setSaldo(1000);

        dao.lagreNyKonto(konto1);

        Konto konto2 = new Konto();
        konto2.setKontonr(2521);
        konto2.setEier("Arne");
        konto2.setSaldo(500);

        dao.lagreNyKonto(konto2);


        // List kontoer med saldo over et gitt beløp
        double saldo = 700;
        System.out.println("Kontoer med saldo over " + saldo + ":");
        for (Konto konto : dao.getKontoerOverSaldo(saldo)) {
            System.out.println("    " + konto);
        }


        // Endre eier på en konto og oppdater databasen
        konto2.setEier("Frank");
        dao.oppdaterKonto(konto2);
    }

    public static void oppgave3() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Oving5PU");
        KontoDAO dao = new KontoDAO(entityManagerFactory);

        Thread thread = new Thread(() -> {
            dao.overfor(1234, 2521, 100);
        });
        thread.start();
        dao.overfor(1234, 2521, 100);
        thread.join();

        System.out.println("Forventet: Konto 1234, saldo 800. Konto 2521, saldo 700");
        System.out.println(dao.getKonto(1234));
        System.out.println(dao.getKonto(2521));


        // Tilbakestill saldoene
        Konto konto = dao.getKonto(1234);
        konto.setSaldo(1000);
        dao.oppdaterKonto(konto);
        konto = dao.getKonto(2521);
        konto.setSaldo(500);
        dao.oppdaterKonto(konto);
    }

    public static void oppgave4() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Oving5PU");
        KontoDAO dao = new KontoDAO(entityManagerFactory);

        /*
        KontoLocked konto1 = new KontoLocked();
        konto1.setKontonr(7777);
        konto1.setEier("Magnus");
        konto1.setSaldo(1000);
        dao.lagreNyKontoLocked(konto1);

        KontoLocked konto2 = new KontoLocked();
        konto2.setKontonr(8888);
        konto2.setEier("Arne");
        konto2.setSaldo(500);
        dao.lagreNyKontoLocked(konto2);
        */

        Thread thread = new Thread(() -> {
            dao.overforLocked(7777, 8888, 100);
        });
        thread.start();
        dao.overforLocked(7777, 8888, 100);
        thread.join();

        System.out.println("Forventet: Konto 7777, saldo 800. Konto 8888, saldo 700");
        System.out.println(dao.getKontoLocked(7777));
        System.out.println(dao.getKontoLocked(8888));


        // Tilbakestill saldoene
        KontoLocked konto = dao.getKontoLocked(7777);
        konto.setSaldo(1000);
        dao.oppdaterKontoLocked(konto);
        konto = dao.getKontoLocked(8888);
        konto.setSaldo(500);
        dao.oppdaterKontoLocked(konto);
    }
}
