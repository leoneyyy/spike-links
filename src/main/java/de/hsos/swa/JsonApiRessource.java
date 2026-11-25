package de.hsos.swa;

import java.util.Map;

public record JsonApiRessource(String type, String id, Map<String, Object> attributes, Map<String, String> links) {
}
