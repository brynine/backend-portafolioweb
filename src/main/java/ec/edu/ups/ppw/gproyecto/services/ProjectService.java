package ec.edu.ups.ppw.gproyecto.services;

import java.net.URI;

import ec.edu.ups.ppw.gproyecto.Project;
import ec.edu.ups.ppw.gproyecto.bussines.GestionProjects;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("projects")
public class ProjectService {

    @Inject
    private GestionProjects gp;

    // ================= LISTAR =================
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        return Response.ok(gp.getProjects()).build();
    }

    // ================= BUSCAR POR ID =================
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {

        Project p = gp.getProject(id);

        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Proyecto no encontrado")
                    .build();
        }

        return Response.ok(p).build();
    }

    // ================= CREAR =================
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(Project project, @Context UriInfo uriInfo) {

        try {
            gp.crearProject(project);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(project.getId())
                .build();

        return Response.created(location)
                .entity(project)
                .build();
    }

    // ================= ACTUALIZAR =================
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(
            @PathParam("id") String id,
            Project project) {

        project.setId(id); // 🔑 CLAVE
        gp.actualizarProject(project);

        return Response.ok(project).build();
    }

    // ================= ELIMINAR =================
    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") String id) {
        try {
            gp.eliminarProject(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.CONFLICT) // 409
                    .entity(e.getMessage())
                    .build();
        }
    }

    
 // ================= PROYECTOS POR USUARIO =================
    @GET
    @Path("/user/{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByUser(@PathParam("uid") String uid) {

        return Response.ok(gp.getProjectsByUser(uid)).build();
    }
    
    

}
