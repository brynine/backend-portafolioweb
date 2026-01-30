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

// INSERTAR
public void insert(Notification n) {
    em.persist(n);
}

// BUSCAR POR ID
public Notification read(String id) {
    return em.find(Notification.class, id);
}

// ACTUALIZAR
public void update(Notification n) {
    em.merge(n);
}

// ELIMINAR
public void delete(String id) {
    Notification n = read(id);
    if (n != null) {
        em.remove(n);
    }
}

// LISTAR TODOS
public List<Notification> getAll() {
    return em.createQuery(
            "SELECT n FROM Notification n",
            Notification.class
    ).getResultList();
}

}