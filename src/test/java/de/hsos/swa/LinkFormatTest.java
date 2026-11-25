package de.hsos.swa;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;


@QuarkusTest
class LinkFormatTest {

    @Test
    void header_einzelobjekt_zustandsabhaengigMoeglich() {
        List<String> ohnePass = given().get("/header/personen/1").then().statusCode(200)
                .extract().headers().getValues("Link");
        List<String> mitPass = given().get("/header/personen/2").then().statusCode(200)
                .extract().headers().getValues("Link");

        assertTrue(String.join(",", ohnePass).contains("spielerpass-registrieren"));
        assertFalse(String.join(",", mitPass).contains("spielerpass-registrieren"));
    }

    @Test
    void header_liste_keineLinksProEintrag() {
        given().get("/header/personen").then().statusCode(200)
                .body("[0]._links", nullValue())          // Eintraege haben keine Links
                .body("[1]._links", nullValue());
        // im Header steht nur der self-Link der ganzen Liste
    }

    @Test
    void hal_liste_linksProEintragZustandsabhaengig() {
        given().accept("application/hal+json").get("/hal/personen").then().statusCode(200)
                .body("_embedded.personen[0]._links.'spielerpass-registrieren'", notNullValue())
                .body("_embedded.personen[1]._links.'spielerpass-registrieren'", nullValue());
    }
    @Test
    void eigenesFormat_liste_linksProEintragZustandsabhaengig() {
        given().get("/eigen/personen").then().statusCode(200)
                .body("[0].links.rel", hasItem("spielerpass-registrieren"))
                .body("[1].links.rel", not(hasItem("spielerpass-registrieren")));
    }

    @Test
    void jsonApi_liste_linksProEintragZustandsabhaengig() {
        given().accept("application/vnd.api+json").get("/jsonapi/personen").then().statusCode(200)
                .body("data[0].type", equalTo("personen"))
                .body("data[0].links.'spielerpass-registrieren'", notNullValue())
                .body("data[1].links.'spielerpass-registrieren'", nullValue())
                .body("links.self", notNullValue());
    }
}