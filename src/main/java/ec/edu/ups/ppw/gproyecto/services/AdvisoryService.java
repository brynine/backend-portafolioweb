	package ec.edu.ups.ppw.gproyecto.services;
	
	import java.net.URI;
	
	import ec.edu.ups.ppw.gproyecto.Advisory;
	import ec.edu.ups.ppw.gproyecto.bussines.GestionAdvisories;
	import jakarta.inject.Inject;
	import jakarta.ws.rs.*;
	import jakarta.ws.rs.core.*;
	
	@Path("advisories")
	public class AdvisoryService {
	
	    @Inject
	    private GestionAdvisories ga;
	
	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response listar() {
	        return Response.ok(ga.getAdvisories()).build();
	    }
	
	    @GET
	    @Path("{id}")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response get(@PathParam("id") String id) {
	
	        Advisory a = ga.getAdvisory(id);
	
	        if (a == null) {
	            return Response.status(Response.Status.NOT_FOUND).build();
	        }
	
	        return Response.ok(a).build();
	    }
	    
	    @POST
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response crear(Advisory advisory, @Context UriInfo uriInfo) {
	
	        try {
	            ga.crearAdvisory(advisory);
	        } catch (Exception e) {
	            return Response.status(Response.Status.BAD_REQUEST)
	                    .entity(e.getMessage())
	                    .build();
	        }
	
	        URI location = uriInfo.getAbsolutePathBuilder()
	                .path(advisory.getId())
	                .build();
	
	        return Response.created(location)
	                .entity(advisory)
	                .build();
	    }
	
	    @PUT
	    @Path("{id}")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response actualizar(
	            @PathParam("id") String id,
	            Advisory advisory) {
	
	        advisory.setId(id);
	        ga.actualizarAdvisory(advisory);
	
	        return Response.ok(advisory).build();
	    }
	
	    @DELETE
	    @Path("{id}")
	    public Response eliminar(@PathParam("id") String id) {
	
	        ga.eliminarAdvisory(id);
	        return Response.noContent().build();
	    }
	    
	    @GET
	    @Path("programador/{id}")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getByProgramador(@PathParam("id") String id) {
	        return Response.ok(ga.getAdvisoriesByProgramador(id)).build();
	    }
	    
	    @GET
	    @Path("user/{id}")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getByUser(@PathParam("id") String id) {
	        return Response.ok(ga.getAdvisoriesByUser(id)).build();
	    }
	    
	    @PUT
	    @Path("{id}/confirmar")
	    public Response confirmar(@PathParam("id") String id) {
	        try {
	            ga.confirmarAdvisory(id);
	            return Response.ok().build();
	        } catch (Exception e) {
	            return Response.status(Response.Status.BAD_REQUEST)
	                    .entity(e.getMessage())
	                    .build();
	        }
	    }
	    
	    
	    @PUT
	    @Path("{id}/estado")
	    @Consumes(MediaType.TEXT_PLAIN)
	    public Response actualizarEstado(
	            @PathParam("id") String id,
	            String estado) {

	        Advisory a = ga.getAdvisory(id);
	        if (a == null)
	            return Response.status(Response.Status.NOT_FOUND).build();

	        a.setEstado(estado);
	        ga.actualizarAdvisory(a);

	        return Response.ok().build();
	    }
	    
	    @GET
	    @Path("stats/programadores")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response asesoriasPorProgramador() {
	        return Response.ok(ga.getAsesoriasPorProgramador()).build();
	    }

	    @GET
	    @Path("stats/estado")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response asesoriasPorEstado() {
	        return Response.ok(ga.getAsesoriasPorEstado()).build();
	    }

	    @GET
	    @Path("stats/mes")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response asesoriasPorMes() {
	        return Response.ok(ga.getAsesoriasPorMes()).build();
	    }
	    
	    @GET
	    @Path("stats/total")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response totalAsesorias() {
	        return Response.ok(ga.getTotalAsesorias()).build();
	    }

	}
