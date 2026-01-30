package ec.edu.ups.ppw.gproyecto.bussines;

import java.util.List;

import ec.edu.ups.ppw.gproyecto.Persona;
import ec.edu.ups.ppw.gproyecto.dao.PersonaDAO;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionPersonas {
	
	@Inject
	private PersonaDAO daoPersona;
	
	public List<Persona> getPersonas(){
		return daoPersona.getAll();
		
	}
	
	public Persona getPersonas(String id)throws Exception{
		if (id.isEmpty() || id.length() != 10)
			throw new Exception("Parametro vacio");
			
			Persona p = daoPersona.read(id);
			return p;
		}
	
	public void crearPersonas(Persona persona) throws Exception {
        if (persona == null) {
            throw new Exception("La persona no puede ser null");
        }
        daoPersona.insert(persona);
	}
	
	public void actualizarPersona(Persona persona) {
	    daoPersona.update(persona);
	}

	public void eliminarPersona(String cedula) {
	    daoPersona.delete(cedula);
	}


}
