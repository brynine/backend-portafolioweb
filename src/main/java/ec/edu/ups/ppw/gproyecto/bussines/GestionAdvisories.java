package ec.edu.ups.ppw.gproyecto.bussines;

import java.util.List;

import ec.edu.ups.ppw.gproyecto.Advisory;
import ec.edu.ups.ppw.gproyecto.Project;
import ec.edu.ups.ppw.gproyecto.User;
import ec.edu.ups.ppw.gproyecto.dao.AdvisoryDAO;
import ec.edu.ups.ppw.gproyecto.dao.ProjectDAO;
import ec.edu.ups.ppw.gproyecto.dao.UserDAO;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionAdvisories {

	@Inject
	private AdvisoryDAO advisoryDAO;

	@Inject
	private ProjectDAO projectDAO;

	@Inject
	private UserDAO userDAO;

	public List<Advisory> getAdvisories() {
		return advisoryDAO.getAll();
	}

	public Advisory getAdvisory(String id) {
		return advisoryDAO.read(id);
	}

	public void crearAdvisory(Advisory advisory) throws Exception {

		if (advisory.getId() == null || advisory.getId().isEmpty()) {
			throw new Exception("ID de la asesoría es obligatorio");
		}
		if (advisory.getUser() == null || advisory.getUser().getId() == null) {
			throw new Exception("Usuario Obligatorio");
			
		}
		if (advisory.getProject() == null || advisory.getProject().getId() == null) {
			throw new Exception("Proyecto Obligatorio");
		}

		User userBD = userDAO.read(advisory.getUser().getId());
		if (userBD == null) {
			throw new Exception("Usuario no existe");
		}

		Project projectBD = projectDAO.read(advisory.getProject().getId());
		if (projectBD == null) {
			throw new Exception("Proyecto no existe");
		}

		advisory.setUser(userBD);
		advisory.setProject(projectBD);

		advisoryDAO.insert(advisory);
	}

	public void actualizarAdvisory(Advisory advisory) {
		advisoryDAO.update(advisory);
	}

	public void eliminarAdvisory(String id) {
		advisoryDAO.delete(id);
	}

	public List<Advisory> getAdvisoriesByUser(String userId) {
		return advisoryDAO.findByUser(userId);
	}

	public List<Advisory> getAdvisoriesByEstado(String estado) {
		return advisoryDAO.findByEstado(estado);
	}

	public List<Advisory> getAdvisoriesByProgramador(String programadorId) {
		return advisoryDAO.findByProgramador(programadorId);
	}

}
