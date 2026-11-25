package de.hsos.swa;

import io.quarkus.hal.HalEntityWrapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.reactive.common.util.RestMediaType;


@Path("/b/personen")
@Produces({MediaType.APPLICATION_JSON, RestMediaType.APPLICATION_HAL_JSON})
public class ManuellResource {

    @GET
    @Path("{id}")
    public HalEntityWrapper<PassDTO> lese(@PathParam("id") long id, @Context UriInfo uriInfo) {
        PassDTO p = DeklarativResource.DATEN.get(id);
        HalEntityWrapper<PassDTO> w = new HalEntityWrapper<>(p);
        UriBuilder basis = uriInfo.getBaseUriBuilder().path("b/personen").path(Long.toString(id));
        w.addLinks(Link.fromUri(basis.clone().build()).rel("self").build());
        if (p.spielerId() == null) {
            w.addLinks(Link.fromUri(basis.clone().path("spielerpass").build()).rel("spielerpass-registrieren").build());
        }
        return w;
    }
}
