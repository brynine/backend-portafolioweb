package ec.edu.ups.ppw.gproyecto;

import jakarta.persistence.*;

@Entity
@Table(name = "TBL_PERSONA")
public class Persona {

    @Id
    @Column(name = "per_cedula", length = 10)
    private String cedula;

    @Column(name = "per_nombre", length = 60, nullable = false)
    private String nombre;

    @Column(name = "per_direccion", length = 120)
    private String direccion;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public Persona() {}

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
