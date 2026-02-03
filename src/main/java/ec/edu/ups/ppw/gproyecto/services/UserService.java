package ec.edu.ups.ppw.gproyecto.services;

import java.net.URI;

import ec.edu.ups.ppw.gproyecto.User;
import ec.edu.ups.ppw.gproyecto.bussines.GestionUsers;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("users")
public class UserService {

    @Inject
    private GestionUsers gu;

    // ================= LISTAR =================
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        return Response.ok(gu.getUsers()).build();
    }

    // ================= BUSCAR POR ID =================
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {

        User u = gu.getUser(id);

        if (u == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuario no encontrado")
                    .build();
        }

        return Response.ok(u).build();
    }

    // ================= CREAR =================
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(User user, @Context UriInfo uriInfo) {

        try {
            gu.crearUser(user);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(user.getId())
                .build();

        return Response.created(location)
                .entity(user)
                .build();
    }

    // ================= ACTUALIZAR =================
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(
            @PathParam("id") String id,
            User user) {

        try {
            user.setId(id); // 🔑 CLAVE
            gu.actualizarUser(user);
            return Response.ok(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    // ================= ELIMINAR =================
    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") String id) {

        try {
            gu.eliminarUser(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
    
 // ================= BUSCAR POR EMAIL =================
    @GET
    @Path("email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByEmail(@PathParam("email") String email) {

        User u = gu.getUserByEmail(email);

        if (u == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuario no encontrado")
                    .build();
        }

        return Response.ok(u).build();
    }
    
    @POST
    @Path("sync")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sync(UserSyncDTO dto) {

        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Email obligatorio")
                    .build();
        }

        User user = gu.syncUser(dto.getEmail(), dto.getNombre());

        return Response.ok(user).build();
    }
    
    @POST
    @Path("programador")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearOAsignarProgramador(User user) {
    	
    	System.out.println("EMAIL: " + user.getEmail());
        System.out.println("NOMBRE: " + user.getNombre());
        System.out.println("ESPECIALIDAD: " + user.getEspecialidad());

        try {
        	User u = gu.crearOActualizarProgramador(
        		    user.getEmail(),
        		    user.getNombre(),
        		    user.getEspecialidad()
        		);

            return Response.ok(u).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("programadores")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarProgramadores() {
        return Response.ok(gu.getProgramadores()).build();
    }

}
