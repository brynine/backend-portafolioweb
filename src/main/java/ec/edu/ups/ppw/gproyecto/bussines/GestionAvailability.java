package ec.edu.ups.ppw.gproyecto.bussines;

import java.util.List;

import ec.edu.ups.ppw.gproyecto.Availability;
import ec.edu.ups.ppw.gproyecto.User;
import ec.edu.ups.ppw.gproyecto.dao.AvailabilityDAO;
import ec.edu.ups.ppw.gproyecto.dao.UserDAO;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionAvailability {

    @Inject
    private AvailabilityDAO dao;
    
    @Inject
    private UserDAO userDAO;


    public void crear(Availability a) throws Exception {

        if (a.getUser() == null || a.getUser().getId() == null) {
            throw new Exception("Debe tener usuario");
        }

        a.setId("A" + System.currentTimeMillis());

        User user = userDAO.read(a.getUser().getId());

        if (user == null) {
            throw new Exception("Usuario no existe");
        }

        a.setUser(user);

        dao.insert(a);
    }

    public List<Availability> getAll() {
        return dao.getAll();
    }

    public Availability get(String id) {
        return dao.read(id);
    }

    public void actualizar(Availability a) {
        dao.update(a);
    }

    public void eliminar(String id) {
        dao.delete(id);
    }
    
    public List<Availability> getByUser(String userId) {
        return dao.getByUser(userId);
    }

}
