package ec.edu.ups.ppw.gproyecto.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("test")
public class TestApi {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "API funcionando";
    }
}
