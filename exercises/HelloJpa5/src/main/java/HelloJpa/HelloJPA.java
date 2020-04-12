package HelloJpa;

import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class HelloJPA {

    public static void main(final String[] args) {

        /* Instanzieren Sie einen Entity-Manager mit Variablenname em */
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("HelloJpaPU2");
        final EntityManager em = emf.createEntityManager();


        final Query query = em.createNamedQuery("Professoren.findAll");
        for (final Professoren p : (List<Professoren>) query.getResultList()) {
            System.out.println(p.getName());

            for (final Vorlesungen v : p.getVorlesungenCollection()) {
                System.out.printf("  - %s", v.getTitel());
            }
        }
        /* Selektieren Sie via JPQL die Vorlesungen, welche Sokrates liest, und geben sie Sie via
         System.out aus. Verwenden sie dazu eine neue JPQL Query mit em.createQuery. */

        /* Setzen sie den Raum von Sokrates auf 1234 und persistieren sie dies auf der Datenbank mit em.merge() */

        /* Fügen Sie einen neuen Professor mit Namen „Precht“ via JPA hinzu (em.persist()) */

        /* Fügen Sie eine neue Vorlesung mit Namen „Postmoderne“ hinzu, welche Precht liest */

    }
}
