package de.hsos.swa;

import io.quarkus.resteasy.reactive.links.InjectRestLinks;
import io.quarkus.resteasy.reactive.links.RestLink;
import io.quarkus.resteasy.reactive.links.RestLinkType;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.common.util.RestMediaType;

import java.awt.*;
import java.util.Map;

@Path("/a/personen")
@Produces({MediaType.APPLICATION_JSON, RestMediaType.APPLICATION_HAL_JSON})
public class DeklarativResource {

    static final Map<Long, PassDTO> DATEN = Map.of(
            1L, new PassDTO(1,"Ohne Pass", null),
            2L, new PassDTO(2,"Mit Pass","SP-0001")
    );
    @GET
    @Path("{id}")
    @RestLink(rel = "self", entityType = PassDTO.class)
    @InjectRestLinks(RestLinkType.INSTANCE)
    public PassDTO lese(@PathParam("id") long id){
        return DATEN.get(id);
    }

    @POST
    @Path("{id}/spielerpass")
    @RestLink(rel = "spielerpass-registrieren", entityType = PassDTO.class)
    public PassDTO registriere(@PathParam("id")long id){
        return DATEN.get(id);
    }


}
