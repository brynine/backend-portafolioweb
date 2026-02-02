package ec.edu.ups.ppw.gproyecto.dao;

import java.util.List;

import ec.edu.ups.ppw.gproyecto.Advisory;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class AdvisoryDAO {

    @PersistenceContext(unitName = "gproyectoPersistenceUnit")
    private EntityManager em;

    // INSERTAR
    public void insert(Advisory advisory) {
        em.persist(advisory);
    }

    // BUSCAR POR ID
    public Advisory read(String id) {
        return em.find(Advisory.class, id);
    }

    // LISTAR TODOS
    public List<Advisory> getAll() {
        return em.createQuery(
                "SELECT a FROM Advisory a",
                Advisory.class
        ).getResultList();
    }

    // ACTUALIZAR
    public void update(Advisory advisory) {
        em.merge(advisory);
    }

    // ELIMINAR
    public void delete(String id) {
        Advisory advisory = read(id);
        if (advisory != null) {
            em.remove(advisory);
        }
    }

    // BUSCAR POR USUARIO
    public List<Advisory> findByUser(String userId) {
        return em.createQuery(
                "SELECT a FROM Advisory a WHERE a.user.id = :userId",
                Advisory.class
        )
        .setParameter("userId", userId)
        .getResultList();
    }

    // BUSCAR POR ESTADO
    public List<Advisory> findByEstado(String estado) {
        return em.createQuery(
                "SELECT a FROM Advisory a WHERE a.estado = :estado",
                Advisory.class
        )
        .setParameter("estado", estado)
        .getResultList();
    }
    
    public List<Advisory> findByProgramador(String programadorId) {
        return em.createQuery(
            "SELECT a FROM Advisory a WHERE a.user.id = :id",
            Advisory.class
        )
        .setParameter("id", programadorId)
        .getResultList();
    }

}
