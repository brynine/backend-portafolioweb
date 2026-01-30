package ec.edu.ups.ppw.gproyecto.dao;

import java.util.List;

import ec.edu.ups.ppw.gproyecto.Project;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class ProjectDAO {

    @PersistenceContext(unitName = "gproyectoPersistenceUnit")
    private EntityManager em;

    public void insert(Project project) {
        em.persist(project);
    }
    
    public void update(Project project) {
        em.merge(project);
    }

    public Project read(String id) {
        return em.find(Project.class, id);
    }

    public void delete(String id) {
        Project project = em.find(Project.class, id);
        if (project != null) {
            em.remove(project);
        }
    }
    
    // Devuelve la lista de proyectos
    public List<Project> listarProjects() {
        List<Project> lista = em.createQuery("SELECT p FROM Project p", Project.class)
                                .getResultList();

        System.out.println("PROJECTS:");
        for (Project p : lista) {
            System.out.println(
                p.getNombre() + " - " + (p.getTecnologias() != null ? String.join(", ", p.getTecnologias()) : "")
            );
        }
        return lista;
    }
    
    public List<Project> getAll() {
        return em.createQuery("SELECT p FROM Project p", Project.class)
                 .getResultList();
    }
    
    public List<Project> getByUser(String uid) {
        return em.createQuery(
            "SELECT p FROM Project p WHERE p.user.id = :uid",
            Project.class
        )
        .setParameter("uid", uid)
        .getResultList();
    }
    
}
