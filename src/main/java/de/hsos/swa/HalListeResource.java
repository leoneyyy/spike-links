package de.hsos.swa;

import io.quarkus.hal.HalCollectionWrapper;
import io.quarkus.hal.HalEntityWrapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.reactive.common.util.RestMediaType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Path("/hal/personen")
@Produces({MediaType.APPLICATION_JSON, RestMediaType.APPLICATION_HAL_JSON})
public class HalListeResource {

    @GET
    public HalCollectionWrapper<PassDTO> alle(@Context UriInfo uriInfo){
        List<HalEntityWrapper<PassDTO>> eintraege = new ArrayList<>();
        for(long id: List.of(1L, 2L)){
            PassDTO p = DeklarativResource.DATEN.get(id);
            HalEntityWrapper<PassDTO> w = new HalEntityWrapper<>(p);
            UriBuilder basis = uriInfo.getBaseUriBuilder().path("hal/personen").path(Long.toString(id));
            w.addLinks(Link.fromUri(basis.clone().build()).rel("self").build());
            if(p.spielerId() == null){
                w.addLinks(Link.fromUri(basis.clone().path("spielerpass").build()).rel("spielerpass-registrieren").build());
            }
            eintraege.add(w);
        }
        return new HalCollectionWrapper<>(eintraege,"personen",Link.fromUri(uriInfo.getRequestUri()).rel("self").build());
    }

}
