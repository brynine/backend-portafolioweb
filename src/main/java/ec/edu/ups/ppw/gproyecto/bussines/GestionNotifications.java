package ec.edu.ups.ppw.gproyecto.bussines;

import java.util.List;

import ec.edu.ups.ppw.gproyecto.Notification;
import ec.edu.ups.ppw.gproyecto.User;
import ec.edu.ups.ppw.gproyecto.dao.NotificationDAO;
import ec.edu.ups.ppw.gproyecto.dao.UserDAO;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionNotifications {

    @Inject
    private NotificationDAO notificationDAO;

    @Inject
    private UserDAO userDAO;

    public void crear(Notification n) throws Exception {

        if (n.getUser() == null || n.getUser().getId() == null)
            throw new Exception("Usuario obligatorio");

        // 🔑 Cargar el usuario desde BD
        User userBD = userDAO.read(n.getUser().getId());
        if (userBD == null)
            throw new Exception("Usuario no existe");

        // 🔗 Asociar entidad gestionada
        n.setUser(userBD);

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
    
    public List<Notification> getByUser(String userId) {
        return notificationDAO.findByUser(userId);
    }
    
    public void marcarLeido(String id) {
        notificationDAO.marcarLeido(id);
    }


}
