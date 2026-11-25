package de.hsos.swa;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

import java.util.ArrayList;
import java.util.List;

@Path("/eigen/personen")
@Produces(MediaType.APPLICATION_JSON)
public class EigenesFormatResource {

    @GET
    public List<PersonMitLinks> alle(@Context UriInfo uriInfo) {
        List<PersonMitLinks> ergebnis = new ArrayList<>();
        for (long id : List.of(1L, 2L)) {
            PassDTO p = DeklarativResource.DATEN.get(id);
            String basis = uriInfo.getBaseUriBuilder().path("eigen/personen").path(Long.toString(id)).build().toString();
            List<LinkDTO> links = new ArrayList<>();
            links.add(new LinkDTO("self", basis));
            if (p.spielerId() == null) {
                links.add(new LinkDTO("spielerpass-registrieren", basis + "/spielerpass"));
            }
            ergebnis.add(new PersonMitLinks(p.id(), p.name(), p.spielerId(), links));
        }
        return ergebnis;
    }
}
