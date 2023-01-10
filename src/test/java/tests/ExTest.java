package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class ExTest {
    private static RequestSpecification requestSpecification;
    GetRequest getRequest;
    DeleteRequest deleteRequest;
    private String URL = "https://reqres.in/api/";

    // private static ResponseSpecification responseSpecification;
    @BeforeClass
    public static void setup() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/api/")
                .setContentType(ContentType.JSON)
                .build();
//            responseSpecification= new ResponseSpecBuilder()
//                    .expectStatusCode(200)
//                    .expectContentType(ContentType.JSON)
//                    .build();

    }
    // using Get Request object
    @Test
    public void getData() {
        SoftAssert softAssert = new SoftAssert();
        getRequest = new GetRequest("/users/2", URL);
        Response res = getRequest.send();
        softAssert.assertEquals(res.jsonPath().get("data.id"), (Integer) 2);
        softAssert.assertEquals(res.jsonPath().get("data.first_name"), "Janet");
        softAssert.assertEquals(res.jsonPath().get("data.last_name"), "Weaver");
        softAssert.assertEquals(res.jsonPath().get("data.avatar"), "https://reqres.in/img/faces/2-image.jpg");
        softAssert.assertEquals(res.jsonPath().get("support.url"), "https://reqres.in/#support-heading");
        softAssert.assertEquals(res.jsonPath().get("support.text"), "To keep ReqRes free, contributions towards server costs are appreciated!");
        softAssert.assertAll();
    }

    @Test
    public void createAccount() {
        String body = """
                {
                    "name": "Nourhan",
                    "job": "Test Engineer"
                }
                """;
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .spec(requestSpecification)
                .basePath("users")
                .when()
                .post()
                .then()
                .assertThat().contentType(ContentType.JSON)
                .statusCode(201)
                .log()
                .body();
    }

    @Test
    public void updateAccount() {
        String body = """
                {
                    "name": "Hend",
                    "job": "Software Engineer"
                }
                """;
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .spec(requestSpecification)
                .basePath("users/2")
                .when()
                .put()
                .then()
                .assertThat().contentType(ContentType.JSON)
                .statusCode(200)
                .log()
                .body();
    }
   // using Delete Request object
    @Test
    public void deleteData() {
        deleteRequest = new DeleteRequest("users/2", URL);
        Response response = deleteRequest.send();
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test
    public void getUserList() {
        given().spec(requestSpecification).
                basePath("users?page=2?id=7,8,9,10,11,12")
                .when()
                .get()
                .then()
                .log()
                .body();
    }

    @Test
    public void getWrongUser() {
        given()
                .spec(requestSpecification)
                .basePath("users/m")
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(404).log();
    }
}
