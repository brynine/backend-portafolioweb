package ec.edu.ups.ppw.gproyecto.services;

import java.net.URI;
import java.util.List;

import ec.edu.ups.ppw.gproyecto.Notification;
import ec.edu.ups.ppw.gproyecto.bussines.GestionNotifications;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationService {

    @Inject
    private GestionNotifications gn;

    @GET
    public Response listar() {
        List<Notification> lista = gn.getAll();
        return Response.ok(lista).build();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") String id) {
        Notification n = gn.getById(id);

        if (n == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(n).build();
    }

    @POST
    public Response crear(Notification n, @Context UriInfo uriInfo) {
        try {
            gn.crear(n);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(n.getId())
                .build();

        return Response.created(location).entity(n).build();
    }

    @PUT
    @Path("{id}")
    public Response actualizar(
            @PathParam("id") String id,
            Notification n) {

        n.setId(id);
        gn.actualizar(n);

        return Response.ok(n).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") String id) {
        gn.eliminar(id);
        return Response.noContent().build();
    }
    
    @GET
    @Path("user/{id}")
    public Response getByUser(@PathParam("id") String userId) {
        return Response.ok(gn.getByUser(userId)).build();
    }

    @PUT
    @Path("{id}/leido")
    public Response marcarLeido(@PathParam("id") String id) {
        try {
            gn.marcarLeido(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }


}
