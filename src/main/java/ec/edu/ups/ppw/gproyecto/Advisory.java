package ec.edu.ups.ppw.gproyecto;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@Entity
@Table(name = "advisories")
public class Advisory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;
    
    @Column(name = "nombre_cliente", nullable = false)
    private String nombreCliente;

    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(nullable = false)
    private String hora;

    @Column(nullable = false)
    private String estado;
    
    @Column(nullable = false)
    private String correoCliente;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = true)
    @JoinColumn(name = "project_id", nullable = true)
    private Project project;

    public Advisory() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getNombreCliente() {
	    return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
	    this.nombreCliente = nombreCliente;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCorreoCliente() {
		return correoCliente;
	}

	public void setCorreoCliente(String correoCliente) {
		this.correoCliente = correoCliente;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
    
}
