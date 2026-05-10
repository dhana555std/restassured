package org.example;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.example.config.ConfigLoader;
import org.example.listeners.ExtentLoggingFilter;
import org.example.models.PostRequest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Slf4j
public class SampleTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigLoader.getBaseUrl();
        RestAssured.filters(new ExtentLoggingFilter());
        log.info("Base URL set to: {}", RestAssured.baseURI);
    }

    @Test
    public void test1() {
        RestAssured.given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreatePost() {
        PostRequest postRequest = new PostRequest("foo", "bar", 1);
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("/posts")
                .then()
                .statusCode(201);
    }
}
