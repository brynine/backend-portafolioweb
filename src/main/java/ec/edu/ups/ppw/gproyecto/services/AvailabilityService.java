package ec.edu.ups.ppw.gproyecto.services;

import java.net.URI;
import java.util.List;

import ec.edu.ups.ppw.gproyecto.Availability;
import ec.edu.ups.ppw.gproyecto.bussines.GestionAvailability;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("availability")
public class AvailabilityService {

    @Inject
    private GestionAvailability ga;

    @GET
    @Produces("application/json")
    public Response listar() {
        List<Availability> lista = ga.getAll();
        return Response.ok(lista).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response get(@PathParam("id") String id) {
        Availability a = ga.get(id);

        if (a == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(a).build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response crear(
            Availability availability,
            @Context UriInfo uriInfo) {

        try {
            ga.crear(availability);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(availability.getId())
                .build();

        return Response.created(location)
                .entity(availability)
                .build();
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response actualizar(
            @PathParam("id") String id,
            Availability availability) {

        availability.setId(id); // 🔑 clave
        ga.actualizar(availability);

        return Response.ok(availability).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") String id) {
        ga.eliminar(id);
        return Response.noContent().build();
    }
}
