package continents;

import base.TestBase;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import requests.query.ContinentsQuery;
import java.util.Arrays;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class ContinentsQueryTest extends TestBase {

    @Test
    public void shouldReturnContinentsWithoutAfricaAndAntarctica() {
        List<String> codes = Arrays.asList("AF", "AN");
        Response continentsQuery = ContinentsQuery.continentsWithoutCode(codes, SPEC);

        continentsQuery
                .then()
                    .assertThat()
                    .body(matchesJsonSchemaInClasspath("schemas/continentsWithCodeAndName.json"))
                    .body("data.continents", not(containsString(codes.get(0))))
                    .body("data.continents", not(containsString(codes.get(1))));
    }

    @Test
    public void ShouldReturnAllContinents() {
        Response continentsQuery = ContinentsQuery.Allcontinents(SPEC);

        continentsQuery
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/continentsWithCodeAndName.json"))
                .body("data.continents.size()", is(7));
    }

    @Test
    public void shouldReturnInvalidSintaxMessageAndStatusCodeBadRequest() {
        Response continentsQuery = ContinentsQuery.withSintaxErro(SPEC);

        continentsQuery
                .then()
                    .assertThat()
                    .statusCode(400)
                    .body("errors[0].message", containsString("Syntax Error: Expected Name, found"))
                    .body("errors[0].extensions.code", is("GRAPHQL_PARSE_FAILED"));
    }

    @Test
    public void shouldReturnOneContinent() {
        String code = "AF";
        String name = "Africa";

        Response continentsQuery = ContinentsQuery.getOneContinent(code, SPEC);

        continentsQuery
                .then()
                    .assertThat()
                    .body(matchesJsonSchemaInClasspath("schemas/continentsWithCodeAndName.json"))
                    .body("data.continents.size()", is(1))
                    .body("data.continents[0].code", is(code))
                    .body("data.continents[0].name", is(name));
    }
}
