package ec.edu.ups.ppw.gproyecto.bussines;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import ec.edu.ups.ppw.gproyecto.*;
import ec.edu.ups.ppw.gproyecto.dao.*;

@Singleton
@Startup
public class Demo {

    @Inject
    private UserDAO userDAO;

    @Inject
    private PersonaDAO personaDAO;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private AdvisoryDAO advisoryDAO;

    @Inject
    private NotificationDAO notificationDAO;

    @Inject
    private AvailabilityDAO availabilityDAO;

    @PostConstruct
    public void init() {

        // ===================== USER =====================
        User u = userDAO.read("U1");
        if (u == null) {
            u = new User();
            u.setId("U1");
            u.setNombre("Bryam Mejia");
            u.setEmail("bryammejia999@gmail.com");
            u.setRol("admin");
            u.setEspecialidad("Ingeniero");
            u.setActivo(true);
            userDAO.insert(u);
        }

        // ===================== PERSONA =====================
        Persona persona = personaDAO.read("0102030405");
        if (persona == null) {
            persona = new Persona();
            persona.setCedula("0102030405");
            persona.setNombre("Juan");
            persona.setDireccion("Ricaurte");
            persona.setUser(u);
            personaDAO.insert(persona);
        }

        // ===================== PROJECT =====================
        Project p = projectDAO.read("P1");
        if (p == null) {
            p = new Project();
            p.setId("P1");
            p.setNombre("Sistema Web");
            p.setDescripcion("Proyecto PPW");
            p.setTipo(TipoProyecto.ACADEMICO);
            p.setParticipacion("Desarrollador principal");
            p.setTecnologias(Arrays.asList("Java", "Jakarta EE"));
            p.setRepositorio("https://github.com/bryam/proyecto");
            p.setDeploy("http://localhost:8080/gproyecto");
            p.setCreatedAt(LocalDateTime.now());
            p.setUser(u);
            projectDAO.insert(p);
        }

        // ===================== ADVISORY =====================
        if (advisoryDAO.getAll().isEmpty()) {

            Advisory a = new Advisory();

            a.setMensaje("Revisión del alcance del proyecto");
            a.setFecha(LocalDate.of(2026, 1, 7));
            a.setHora("10:00");
            a.setEstado("PENDIENTE");
            a.setCorreoCliente("andresmejiajr10@gmail.com");
            a.setUser(u);
            a.setProject(p);

            advisoryDAO.insert(a);
        }



        // ===================== NOTIFICATION =====================
        if (notificationDAO.read("N1") == null) {
            Notification n = new Notification();
            n.setId("N1");
            n.setMensaje("Tienes una nueva asesoría asignada");
            n.setFecha(LocalDate.of(2026, 1, 7));
            n.setLeido(false);
            n.setUser(u);
            notificationDAO.insert(n);
        }

        // ===================== AVAILABILITY =====================
        if (availabilityDAO.read("AV1") == null) {
            Availability av = new Availability();
            av.setId("AV1");
            av.setDia("Lunes");
            av.setHoraInicio("08:00");
            av.setHoraFin("10:00");
            av.setUser(u);
            availabilityDAO.insert(av);
        }

        System.out.println("✅ Demo verificado (sin duplicados)");
    }
}
