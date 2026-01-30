package ec.edu.ups.ppw.gproyecto.dao;

import java.util.List;

import ec.edu.ups.ppw.gproyecto.Persona;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class PersonaDAO {

    @PersistenceContext(unitName = "gproyectoPersistenceUnit")
    private EntityManager em;

    public void insert(Persona persona) {
        if (persona.getCedula() == null || persona.getCedula().length() != 10) {
            throw new IllegalArgumentException("Formato de cédula incorrecto");
        }
        if (persona.getNombre() == null || persona.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        em.persist(persona);
    }

    public void update(Persona persona) {
        em.merge(persona);
    }

    public Persona read(String pk) {
        return em.find(Persona.class, pk);
    }

    public void delete(String pk) {
        Persona persona = read(pk);
        if (persona != null) {
            em.remove(persona);
        }
    }

    public List<Persona> getAll() {
        return em.createQuery("SELECT p FROM Persona p", Persona.class)
                 .getResultList();
    }
}

