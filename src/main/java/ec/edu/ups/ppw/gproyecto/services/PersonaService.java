package ec.edu.ups.ppw.gproyecto.services;

import java.net.URI;
import java.util.List;

import ec.edu.ups.ppw.gproyecto.Persona;
import ec.edu.ups.ppw.gproyecto.bussines.GestionPersonas;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("persona")
public class PersonaService {
	
	@Inject
	private GestionPersonas gp;
	
	@GET
	@Produces("application/json")
	public Response getListarPersonas(){
		List<Persona> listado = gp.getPersonas();
		return Response.ok(listado).build(); //status code 200
	}
	
	@GET
	@Path("{id}")
	@Produces("application/json")
	public Response getPersona(@PathParam("id") String cedula) {
		Persona p;
		try {
			p = gp.getPersonas(cedula);
			
		} catch (Exception e) {
			e.printStackTrace();
			Error error = new Error(
					500,
					"Error interno",
					e.getMessage());
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error)
					.build();
					
		}
		
		if (p==null) {
				Error error = new Error(
				404,
				"No encontrado",
				"Persona con ID" + cedula + "no encontrada");
				return Response.status(Response. Status.NOT_FOUND)
				.entity(error)
				.build();
	}
		
	return Response.ok(p).build();
	
	}
		
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response createPersona(Persona persona, @Context UriInfo uriInfo) {
		
		try {
			gp.crearPersonas(persona);
		} catch (Exception e) {
			e.printStackTrace();
			Error error = new Error(
					500,
					"Error interno",
					e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error)
					.build();
					
		}
		
		URI location = uriInfo.getAbsolutePathBuilder()
				.path(persona.getCedula())
				.build();
		
		return Response.created(location)
	            .entity(persona)
	            .build();
	}
	
	@PUT
	@Path("{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updatePersona(
	        @PathParam("id") String cedula,
	        Persona persona) {

	    try {
	        Persona p = gp.getPersonas(cedula);

	        if (p == null) {
	            Error error = new Error(
	                404,
	                "No encontrado",
	                "Persona con ID " + cedula + " no existe");
	            return Response.status(Response.Status.NOT_FOUND)
	                    .entity(error)
	                    .build();
	        }

	        persona.setCedula(cedula);
	        gp.actualizarPersona(persona);

	        return Response.ok(persona).build(); // 200 OK

	    } catch (Exception e) {
	        e.printStackTrace();
	        Error error = new Error(
	            500,
	            "Error interno",
	            e.getMessage());
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                .entity(error)
	                .build();
	    }
	}
	
	@DELETE
	@Path("{id}")
	@Produces("application/json")
	public Response deletePersona(@PathParam("id") String cedula) {

	    try {
	        Persona p = gp.getPersonas(cedula);

	        if (p == null) {
	            Error error = new Error(
	                404,
	                "No encontrado",
	                "Persona con ID " + cedula + " no existe");
	            return Response.status(Response.Status.NOT_FOUND)
	                    .entity(error)
	                    .build();
	        }

	        gp.eliminarPersona(cedula);

	        return Response.noContent().build(); // 204

	    } catch (Exception e) {
	        e.printStackTrace();
	        Error error = new Error(
	            500,
	            "Error interno",
	            e.getMessage());
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                .entity(error)
	                .build();
	    }
	}



}
