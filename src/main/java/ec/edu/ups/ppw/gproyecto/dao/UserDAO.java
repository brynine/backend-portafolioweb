package ec.edu.ups.ppw.gproyecto.dao;

import java.util.List;

import ec.edu.ups.ppw.gproyecto.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserDAO {

	@PersistenceContext(unitName = "gproyectoPersistenceUnit")
    private EntityManager em;

    public void insert(User user) {
        em.persist(user);
    }

    public void update(User user) {
        em.merge(user);
    }

    public User read(String id) {
        return em.find(User.class, id);
    }

    public void delete(String id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

    public void listarUsers() {
        List<User> lista = em
                .createQuery("SELECT u FROM User u", User.class)
                .getResultList();

        System.out.println("USUARIOS:");
        for (User u : lista) {
            System.out.println(u.getNombre() + " - " + u.getEmail());
        }
    }
    
    public List<User> getAll() {
        return em.createQuery("SELECT u FROM User u", User.class)
                 .getResultList();
    }
    
    public User findByEmail(String email) {
        try {
            return em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email",
                User.class
            )
            .setParameter("email", email)
            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public User find(String id) {
        return em.find(User.class, id);
    }


}
