package de.hsos.swa;


import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@QuarkusTest
class LinkVarianteTest {

    @Test
    void deklarativ_mitPass_bekommtTrotzdemRegistrierLink() {
        given().accept("application/hal+json").get("/a/personen/2")
                .then().statusCode(200)
                .body("_links.spielerpass-registrieren", notNullValue());   // unerwuenscht
    }

    @Test
    void manuell_mitPass_hatKeinenRegistrierLink() {
        given().accept("application/hal+json").get("/b/personen/2")
                .then().statusCode(200)
                .body("_links.spielerpass-registrieren", nullValue());      // gewuenscht
    }

    @Test
    void manuell_ohnePass_hatRegistrierLink() {
        given().accept("application/hal+json").get("/b/personen/1")
                .then().statusCode(200)
                .body("_links.spielerpass-registrieren", notNullValue());
    }
}