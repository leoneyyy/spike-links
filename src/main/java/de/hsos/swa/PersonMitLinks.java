package de.hsos.swa;

import java.util.List;

public record PersonMitLinks(long id, String name, String spielerid, List<LinkDTO> links) {
}
