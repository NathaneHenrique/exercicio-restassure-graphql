package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public abstract class TestBase {

    public final RequestSpecification SPEC = new RequestSpecBuilder()
            .addHeader("accept", "application/json")
            .setBaseUri("https://countries.trevorblades.com/").build();

}