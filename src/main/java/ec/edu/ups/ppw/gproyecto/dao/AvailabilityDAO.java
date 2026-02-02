package ec.edu.ups.ppw.gproyecto.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import ec.edu.ups.ppw.gproyecto.Availability;

@Stateless
public class AvailabilityDAO {

    @PersistenceContext(unitName = "gproyectoPersistenceUnit")
    private EntityManager em;

    public void insert(Availability availability) {
        em.persist(availability);
    }

    public Availability update(Availability availability) {
        return em.merge(availability);
    }

    public Availability read(String id) {
        return em.find(Availability.class, id);
    }

    public void delete(String id) {
        Availability availability = read(id);
        if (availability != null) {
            em.remove(availability);
        }
    }

    public List<Availability> getAll() {
        return em.createQuery(
            "SELECT a FROM Availability a",
            Availability.class
        ).getResultList();
    }
    
    public List<Availability> getByUser(String userId) {
        return em.createQuery(
            "SELECT a FROM Availability a WHERE a.user.id = :userId",
            Availability.class
        )
        .setParameter("userId", userId)
        .getResultList();
    }

}
