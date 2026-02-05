package ec.edu.ups.ppw.gproyecto.bussines;

import java.util.List;

import ec.edu.ups.ppw.gproyecto.User;
import ec.edu.ups.ppw.gproyecto.dao.UserDAO;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

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

    public User actualizarUser(User user) {

        User existente = userDAO.read(user.getId());

        if (existente == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (user.getNombre() != null)
            existente.setNombre(user.getNombre());

        if (user.getEspecialidad() != null)
            existente.setEspecialidad(user.getEspecialidad());

        if (user.getBio() != null)
            existente.setBio(user.getBio());

        if (user.getGithub() != null)
            existente.setGithub(user.getGithub());

        if (user.getLinkedin() != null)
            existente.setLinkedin(user.getLinkedin());

        if (user.getInstagram() != null)
            existente.setInstagram(user.getInstagram());

        if (user.getSitioWeb() != null)
            existente.setSitioWeb(user.getSitioWeb());

        userDAO.update(existente);
        return existente;
    }

    public User getUser(String id) {
        return userDAO.read(id);
    }

    @Transactional
    public void eliminarUser(String id) {

        User user = userDAO.read(id);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // 🔒 REGLA DE NEGOCIO
        if (user.getAdvisories() != null && !user.getAdvisories().isEmpty()) {
            throw new RuntimeException(
                "No se puede eliminar el programador porque tiene asesorías asociadas"
            );
        }

        userDAO.delete(user);
    }


    public List<User> getUsers() {
        return userDAO.getAll();
    }
    
    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }
    
    public List<User> getProgramadores() {
        return userDAO.getProgramadores();
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
    
    public User crearOActualizarProgramador(String email, String nombre, String especialidad) {

        User user = userDAO.findByEmail(email);

        if (user == null) {
            user = new User();
            user.setId("U" + System.currentTimeMillis());
            user.setEmail(email);
            user.setActivo(true);
        }

        user.setNombre(nombre);
        user.setRol("programador");
        user.setEspecialidad(especialidad);

        userDAO.update(user);

        return user;
    }


}
