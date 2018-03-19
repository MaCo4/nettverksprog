package oppgave2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class KontoDAO {
    private EntityManagerFactory entityManagerFactory;

    public KontoDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    private void closeEntityManager(EntityManager manager) {
        if (manager != null && manager.isOpen()) {
            manager.close();
        }
    }

    public void lagreNyKonto(Konto konto) {
        EntityManager manager = getEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(konto);
            manager.getTransaction().commit();
        }
        finally {
            closeEntityManager(manager);
        }
    }

    public void oppdaterKonto(Konto konto) {
        EntityManager manager = getEntityManager();
        try {
            manager.getTransaction().begin();
            manager.merge(konto);
            manager.getTransaction().commit();
        }
        finally {
            closeEntityManager(manager);
        }
    }


}
