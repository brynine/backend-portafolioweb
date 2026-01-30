package ec.edu.ups.ppw.gproyecto.services;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/ping")
public class PingService {

    @GET
    public String ping() {
        return "OK";
    }
}
