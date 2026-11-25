package de.hsos.swa;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



@Path("/jsonapi/personen")
@Produces({ "application/vnd.api+json", MediaType.APPLICATION_JSON })
public class JsonApiResource {

    @GET
    public JsonApiDokument alle(@Context UriInfo uriInfo) {
        List<JsonApiRessource> data = new ArrayList<>();
        for (long id : List.of(1L, 2L)) {
            PassDTO p = DeklarativResource.DATEN.get(id);
            String basis = uriInfo.getBaseUriBuilder().path("jsonapi/personen").path(Long.toString(id)).build().toString();

            Map<String, Object> attribute = new LinkedHashMap<>();
            attribute.put("name", p.name());
            attribute.put("spielerId", p.spielerId());

            Map<String, String> links = new LinkedHashMap<>();
            links.put("self", basis);
            if (p.spielerId() == null) {
                links.put("spielerpass-registrieren", basis + "/spielerpass");
            }
            data.add(new JsonApiRessource("personen", Long.toString(p.id()), attribute, links));
        }
        return new JsonApiDokument(data, Map.of("self", uriInfo.getRequestUri().toString()));
    }
}
