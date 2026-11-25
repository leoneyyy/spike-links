package de.hsos.swa;

import java.util.List;
import java.util.Map;

public record JsonApiDokument(List<JsonApiRessource> data, Map<String, String> links) {
}
