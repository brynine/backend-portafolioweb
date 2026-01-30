package ec.edu.ups.ppw.gproyecto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification {

@Id
private String id;

@Column(nullable = false)
private String mensaje;

@Column(nullable = false)
private LocalDate fecha;

@Column(nullable = false)
private boolean leido;

@ManyToOne(optional = false)
@JoinColumn(name = "user_id")
private User user;

public Notification() {
}

public String getId() {
    return id;
}

public void setId(String id) {
    this.id = id;
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

public boolean isLeido() {
    return leido;
}

public void setLeido(boolean leido) {
    this.leido = leido;
}

public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}

}