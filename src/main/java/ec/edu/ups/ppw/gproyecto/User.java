package ec.edu.ups.ppw.gproyecto;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String rol;
    
    @Column(name = "especialidad")
    private String especialidad;
    
    @Column(length = 500)
    private String bio;

    @Column(length = 255)
    private String github;

    @Column(length = 255)
    private String linkedin;

    @Column(length = 255)
    private String instagram;

    @Column(name = "sitio_web", length = 255)
    private String sitioWeb;

    
    private boolean activo;
    
    @OneToMany(
    	    mappedBy = "user",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true
    	)
    	@JsonIgnore
    	private List<Project> projects;

    	@OneToMany(
    	    mappedBy = "user",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true
    	)
    	@JsonIgnore
    	private List<Advisory> advisories;


    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    

    public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getGithub() {
		return github;
	}

	public void setGithub(String github) {
		this.github = github;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getSitioWeb() {
		return sitioWeb;
	}

	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Advisory> getAdvisories() {
		return advisories;
	}

	public void setAdvisories(List<Advisory> advisories) {
		this.advisories = advisories;
	}

	public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
