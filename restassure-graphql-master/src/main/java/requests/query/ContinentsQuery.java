package requests.query;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;

final public class ContinentsQuery {

    public static Response continentsWithoutCode(List<String> continentsCode, RequestSpecification spec) {

        StringBuilder codesForBody = new StringBuilder("[");

        for (String code: continentsCode) {
            codesForBody.append("\\\"").append(code).append("\\\"").append(",");
        }

        codesForBody.append("]");

        return given().
                    spec(spec).
                    header("Content-Type", "application/json").
                    and().
                    body("{\"query\":\"query {\\r\\n  continents(filter: {\\r\\n      code: { nin: "+codesForBody+"}\\r\\n  }) {\\r\\n    code\\r\\n    name\\r\\n  }\\r\\n}\\r\\n\",\"variables\":{}}").
                when().
                    post();
    }

    public static Response Allcontinents(RequestSpecification spec) {
        return given().
                    spec(spec).
                    header("Content-Type", "application/json").
                    and().
                    body("{\"query\":\"query {\\r\\n   continents {\\r\\n    code\\r\\n    name\\r\\n  }\\r\\n}\\r\\n\",\"variables\":{}}").
                when().
                    post();
    }

    public static Response withSintaxErro(RequestSpecification spec) {
        return given().
                    spec(spec).
                    header("Content-Type", "application/json").
                    and().
                    body("{\"query\":\"query {\\r\\n   continents {\\r\\n  }\\r\\n}\\r\\n\",\"variables\":{}}").
                when().
                    post();
    }

    public static Response getOneContinent(String code, RequestSpecification spec) {
        return given().
                    spec(spec).
                    header("Content-Type", "application/json").
                    and().
                    body("{\"query\":\"query {\\r\\n  continents(filter: {\\r\\n      code: { eq: \\\""+ code +"\\\"}\\r\\n  }) {\\r\\n    code\\r\\n    name\\r\\n  }\\r\\n}\\r\\n\",\"variables\":{}}").
                when().
                    post();
    }
}
