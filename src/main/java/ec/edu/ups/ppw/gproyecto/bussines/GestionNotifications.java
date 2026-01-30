package ec.edu.ups.ppw.gproyecto.bussines;

import java.util.List;

import ec.edu.ups.ppw.gproyecto.Notification;
import ec.edu.ups.ppw.gproyecto.dao.NotificationDAO;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionNotifications {

    @Inject
    private NotificationDAO notificationDAO;

    public void crear(Notification n) throws Exception {
        if (n.getId() == null || n.getId().isEmpty())
            throw new Exception("ID obligatorio");

        notificationDAO.insert(n);
    }

    public List<Notification> getAll() {
        return notificationDAO.getAll();
    }

    public Notification getById(String id) {
        return notificationDAO.read(id);
    }

    public void actualizar(Notification n) {
        notificationDAO.update(n);
    }

    public void eliminar(String id) {
        notificationDAO.delete(id);
    }
}
