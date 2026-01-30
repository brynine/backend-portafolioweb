package ec.edu.ups.ppw.gproyecto.bussines;

import java.util.List;

import ec.edu.ups.ppw.gproyecto.User;
import ec.edu.ups.ppw.gproyecto.dao.UserDAO;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionUsers {

    @Inject
    private UserDAO userDAO;

    public void crearUser(User user) throws Exception {
    	
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new Exception("Email obligatorio");
        }
        userDAO.insert(user);
    }

    public void actualizarUser(User user) {
        userDAO.update(user);
    }

    public User getUser(String id) {
        return userDAO.read(id);
    }

    public void eliminarUser(String id) {
        userDAO.delete(id);
    }

    public List<User> getUsers() {
        return userDAO.getAll();
    }
    
    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }
    
    public User syncUser(String email, String nombre) {

        User user = userDAO.findByEmail(email);

        if (user != null) {
            return user;
        }

        User nuevo = new User();
        
        String nuevoId = "U" + System.currentTimeMillis();
        nuevo.setId(nuevoId);
        
        nuevo.setEmail(email);
        nuevo.setNombre(nombre);
        nuevo.setActivo(true);
        nuevo.setRol("externo");

        userDAO.insert(nuevo);

        return nuevo;
    }


}
