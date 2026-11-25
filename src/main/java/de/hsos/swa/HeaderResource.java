package de.hsos.swa;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Path("/header/personen")
@Produces(MediaType.APPLICATION_JSON)
public class HeaderResource {

    @GET
    @Path("{id}")
    public Response lese(@PathParam("id") long id, @Context UriInfo uriInfo){
        PassDTO p = DeklarativResource.DATEN.get(id);
        Response.ResponseBuilder antwort = Response.ok(p).links(Link.fromUri(uriInfo.getAbsolutePath()).rel("self").build());
        if(p.spielerId() == null){
            antwort.links(Link.fromUri(UriBuilder.fromUri(uriInfo.getAbsolutePath()).path("spielerpass").build()).rel("spielerpass-registrieren").build());
        }
        return antwort.build();
    }
    @GET
    public Response alle(@Context UriInfo uriInfo){
        List<PassDTO> liste = new ArrayList<>(DeklarativResource.DATEN.values());
        return Response.ok(liste).links(Link.fromUri(uriInfo.getAbsolutePath()).rel("self").build()).build();
    }
}
