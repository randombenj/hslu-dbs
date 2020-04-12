package HelloJpa;

import java.lang.invoke.VolatileCallSite;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
                System.out.printf("  - %s\n", v.getTitel());
            }
        }


        /* Selektieren Sie via JPQL die Vorlesungen, welche Sokrates liest, und geben sie Sie via
         System.out aus. Verwenden sie dazu eine neue JPQL Query mit em.createQuery. */
         System.out.println("\nSokrates liest folgende vorlesungen:");
         em.createQuery("SELECT v FROM Vorlesungen v, Professoren p WHERE p.name = :profName and v.gelesenVon = p.persNr")
            .setParameter("profName", "Sokrates")
            .getResultList()
            .forEach((v) -> System.out.printf("  - %s\n", ((Vorlesungen)v).getTitel()));

        em.getTransaction().begin();

        /* Setzen sie den Raum von Sokrates auf 1234 und persistieren sie dies auf der Datenbank mit em.merge() */
        System.out.println("\nSokrates hat nun folgendn raum:");
        final Professoren sokrates = (Professoren)em.createNamedQuery("Professoren.getByName")
            .setParameter("name", "Sokrates")
            .getResultList().get(0);

        sokrates.setRaum(1234);
        em.merge(sokrates);

        System.out.println(((Professoren)em.createNamedQuery("Professoren.getByName")
            .setParameter("name", "Sokrates")
            .getResultList().get(0)).getRaum());

        /* Fügen Sie einen neuen Professor mit Namen „Precht“ via JPA hinzu (em.persist()) */
        System.out.println("\nNun arbetet auch Precht hier!");
        final Professoren precht = new Professoren(1337, "Precht");
        em.persist(precht);

        System.out.println(((Professoren)em.createNamedQuery("Professoren.getByName")
            .setParameter("name", "Precht")
            .getResultList().get(0)).getName());

        /* Fügen Sie eine neue Vorlesung mit Namen „Postmoderne“ hinzu, welche Precht liest */
        System.out.println("\nPrecht ist nicht faul, er macht Postmoderne!");
        final Vorlesungen postmoderne = new Vorlesungen(2369);
        postmoderne.setTitel("Postmoderne");
        postmoderne.setGelesenVon(precht);
        em.persist(postmoderne);

        em.getTransaction().commit();
    }
}
