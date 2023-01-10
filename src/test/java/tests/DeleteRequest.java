package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DeleteRequest
{
    String endPoint;
    String uri;
    RequestSpecification requestSpecification;

    public DeleteRequest(String endPoint, String uri) {
        this.endPoint = endPoint;
        this.uri = uri;
        requestSpecification= RestAssured.given().baseUri(uri);
    }
    public void addParameters(String key ,String value)
    {
        requestSpecification.param(key,value);
    }
    public Response send ()
    {
        return requestSpecification.delete(endPoint);
    }
}

