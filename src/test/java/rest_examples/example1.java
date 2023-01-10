package rest_examples;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;



public class example1
{
    @Test
    public void getCountryData ()
    {
        when().get("http://api.zippopotam.us/us/90210")
                .then()
                .assertThat()
                //.contentType(ContentType.XML)
                .log()
                .all()
                .statusCode(201);
    }
    @Test
    public void ex1()
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
    @Test
    public void getListData ()
    {
        given()
                .get("https://jsonplaceholder.typicode.com/posts")
                .then()
                .body("response",hasSize(100))
                .log()
                .body();
    }
    @Test
    public void checkSize()
    {
        given().
                get("http://api.zippopotam.us/us/90210")
                .then()
                .body("places",hasSize(1))
                .log()
                .all();
    }
    @Test
    public void checkState()
    {
        given().
                get("http://api.zippopotam.us/us/90210")
                .then()
                .body("places[0].state",equalTo("California"))
                .log()
                .all();
    }
    @Test
    public void getElement()
    {
        Response res = given().when().get("http://api.zippopotam.us/us/90210");
        Assert.assertEquals(res.jsonPath().get("country"),"United States");
    }
}
