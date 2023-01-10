package rest_examples;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class JsonPlaceHolder
{
    private static RequestSpecification requestSpecification;
    private static ResponseSpecification responseSpecification;

    @BeforeClass
    public static void setup()
    {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com/posts/1")
                .setContentType(ContentType.JSON)
                .build();
        responseSpecification= new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
    @Test
    public void getData ()
    {
        given()
                .spec(requestSpecification)
                .when()
                .get()
                .then()
                .spec(responseSpecification)
                .log()
                .body();
    }
    @Test
    public void deleteData()
    {
        given()
                .spec(requestSpecification)
                .when().delete("https://jsonplaceholder.typicode.com/posts/1")
                .then()
                .spec(responseSpecification)
                .log()
                .body();
    }
    @Test
    public void putData()
    {
        given()
                .spec(requestSpecification)
                .when()
                .put()
                .then()
                .spec(responseSpecification)
                .log()
                .body();
    }
    @Test
    public void post()
    {
        String body = """
                {
                    "title": "foo",

                    "body": "bar",

                    "userId": 1
                  }
                """;
        given()
                .body(body)
                .contentType(ContentType.JSON)
                //.header("charset",body)
                .when()
                .post("https://jsonplaceholder.typicode.com/posts")
                .then()
                .assertThat().contentType(ContentType.JSON)
                .statusCode(201)
                .log()
                .body();
    }

}
