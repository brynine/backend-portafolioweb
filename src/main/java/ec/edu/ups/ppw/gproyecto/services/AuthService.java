package ec.edu.ups.ppw.gproyecto.services;

import ec.edu.ups.ppw.gproyecto.User;
import ec.edu.ups.ppw.gproyecto.bussines.GestionUsers;
import ec.edu.ups.ppw.gproyecto.security.JwtUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.HashMap;
import java.util.Map;

@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthService {

    @Inject
    private GestionUsers gestionUsers;

    @POST
    @Path("login")
    public Response login(Map<String, String> data) {

        String email = data.get("email");

        if (email == null) {
            return Response.status(400).entity("Email requerido").build();
        }

        User user = gestionUsers.getUserByEmail(email);

        if (user == null) {
            return Response.status(401).entity("Usuario no encontrado").build();
        }

        String token = JwtUtil.generateToken(user.getId(), user.getRol());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);

        return Response.ok(response).build();
    }
}
