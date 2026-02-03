package ec.edu.ups.ppw.gproyecto.bussines;

import java.time.LocalDate;
import java.util.List;

import ec.edu.ups.ppw.gproyecto.Advisory;
import ec.edu.ups.ppw.gproyecto.Notification;
import ec.edu.ups.ppw.gproyecto.Project;
import ec.edu.ups.ppw.gproyecto.User;
import ec.edu.ups.ppw.gproyecto.dao.AdvisoryDAO;
import ec.edu.ups.ppw.gproyecto.dao.NotificationDAO;
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
	
	@Inject
	private NotificationDAO notificationDAO;

	public List<Advisory> getAdvisories() {
		return advisoryDAO.getAll();
	}

	public Advisory getAdvisory(String id) {
		return advisoryDAO.read(id);
	}

	public void crearAdvisory(Advisory advisory) throws Exception {

	    if (advisory.getUser() == null || advisory.getUser().getId() == null) {
	        throw new Exception("Usuario obligatorio");
	    }

	    User userBD = userDAO.read(advisory.getUser().getId());
	    if (userBD == null) {
	        throw new Exception("Usuario no existe");
	    }

	    Project projectBD = null;
	    if (advisory.getProject() != null && advisory.getProject().getId() != null) {
	        projectBD = projectDAO.read(advisory.getProject().getId());
	    }

	    advisory.setUser(userBD);
	    advisory.setProject(projectBD);
	    advisory.setEstado("PENDIENTE");

	    // 1️⃣ Guardar asesoría
	    advisoryDAO.insert(advisory);

	    // 2️⃣ Crear notificación 🔔
	    Notification n = new Notification();
	    n.setMensaje(
	        "Nueva solicitud de asesoría para el " 
	        + advisory.getFecha() + " a las " + advisory.getHora()
	    );
	    n.setUser(userBD);
	    n.setLeido(false);
	    n.setFecha(LocalDate.now());

	    notificationDAO.insert(n);
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
