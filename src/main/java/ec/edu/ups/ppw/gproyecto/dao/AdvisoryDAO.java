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
    
    public Long countByProject(String projectId) {
        return em.createQuery(
            "SELECT COUNT(a) FROM Advisory a WHERE a.project.id = :pid",
            Long.class
        )
        .setParameter("pid", projectId)
        .getSingleResult();
    }
    
    public List<Object[]> getAsesoriasPorProgramador() {
        return em.createQuery(
            "SELECT a.user.nombre, COUNT(a) " +
            "FROM Advisory a GROUP BY a.user.nombre",
            Object[].class
        ).getResultList();
    }

    public List<Object[]> getAsesoriasPorEstado() {
        return em.createQuery(
            "SELECT a.estado, COUNT(a) " +
            "FROM Advisory a GROUP BY a.estado",
            Object[].class
        ).getResultList();
    }

    public List<Object[]> getAsesoriasPorMes() {
        return em.createQuery(
            "SELECT FUNCTION('MONTH', a.fecha), COUNT(a) " +
            "FROM Advisory a GROUP BY FUNCTION('MONTH', a.fecha) " +
            "ORDER BY FUNCTION('MONTH', a.fecha)",
            Object[].class
        ).getResultList();
    }

    public Long countAll() {
        return em.createQuery(
            "SELECT COUNT(a) FROM Advisory a",
            Long.class
        ).getSingleResult();
    }

    public List<Object[]> countByEstado() {
        return em.createQuery(
            "SELECT a.estado, COUNT(a) FROM Advisory a GROUP BY a.estado",
            Object[].class
        ).getResultList();
    }

    public List<Object[]> countByProgramador() {
        return em.createQuery(
            "SELECT a.user.nombre, COUNT(a) FROM Advisory a GROUP BY a.user.nombre",
            Object[].class
        ).getResultList();
    }

}
