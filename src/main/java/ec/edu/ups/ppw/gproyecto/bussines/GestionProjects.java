package ec.edu.ups.ppw.gproyecto.bussines;

import java.time.LocalDateTime;
import java.util.List;

import ec.edu.ups.ppw.gproyecto.Project;
import ec.edu.ups.ppw.gproyecto.User;
import ec.edu.ups.ppw.gproyecto.dao.AdvisoryDAO;
import ec.edu.ups.ppw.gproyecto.dao.ProjectDAO;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Stateless
public class GestionProjects {

    @Inject
    private ProjectDAO projectDAO;
    
    @Inject
    private AdvisoryDAO advisoryDAO;

    @Inject
    private GestionUsers gestionUsers;

    // LISTAR
    public List<Project> getProjects() {
        return projectDAO.getAll();
    }

    // BUSCAR POR ID
    public Project getProject(String id) {
        return projectDAO.read(id);
    }

    public void crearProject(Project project) throws Exception {

        if (project.getUser() == null || project.getUser().getId() == null) {
            throw new Exception("Usuario obligatorio");
        }

        User userBD = gestionUsers.getUser(project.getUser().getId());

        if (userBD == null) {
            throw new Exception("El usuario no existe");
        }

        project.setId(java.util.UUID.randomUUID().toString());
        project.setCreatedAt(LocalDateTime.now());
        project.setUser(userBD);

        projectDAO.insert(project);
    }


    // ACTUALIZAR
    public void actualizarProject(Project project) {
        projectDAO.update(project);
    }

    @Transactional
    public void eliminarProject(String id) {

        Project project = projectDAO.read(id);
        if (project == null) {
            throw new RuntimeException("Proyecto no encontrado");
        }

        Long total = advisoryDAO.countByProject(id);
        if (total > 0) {
            throw new RuntimeException(
                "No se puede eliminar el proyecto porque tiene asesorías asociadas"
            );
        }

        projectDAO.delete(id);
    }

    public List<Project> getProjectsByUser(String uid) {
        return projectDAO.getByUser(uid);
    }
}
