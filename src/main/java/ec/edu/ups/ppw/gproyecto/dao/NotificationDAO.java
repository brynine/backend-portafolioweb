package ec.edu.ups.ppw.gproyecto.dao;

import java.util.List;

import ec.edu.ups.ppw.gproyecto.Notification;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class NotificationDAO {

@PersistenceContext(unitName = "gproyectoPersistenceUnit")
private EntityManager em;

public void insert(Notification n) {
    em.persist(n);
}

public Notification read(String id) {
    return em.find(Notification.class, id);
}

public void update(Notification n) {
    em.merge(n);
}

public void delete(String id) {
    Notification n = read(id);
    if (n != null) {
        em.remove(n);
    }
}

public List<Notification> getAll() {
    return em.createQuery(
            "SELECT n FROM Notification n",
            Notification.class
    ).getResultList();
}

public List<Notification> findByUser(String userId) {
    return em.createQuery(
        "SELECT n FROM Notification n WHERE n.user.id = :userId ORDER BY n.fecha DESC",
        Notification.class
    )
    .setParameter("userId", userId)
    .getResultList();
}

public void marcarLeido(String id) {
    Notification n = read(id);
    if (n != null) {
        n.setLeido(true);
        em.merge(n);
    }
}


}