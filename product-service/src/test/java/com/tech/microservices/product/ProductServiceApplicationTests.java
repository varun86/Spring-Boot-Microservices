package com.tech.microservices.product;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI="http://localhost";
		RestAssured.port=port;

	}

	static {
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() {
        String repository= """
                {
                    "name":"iphone 15",
                    "description":"iphone 15 is a smatphone from apple",
                    "price":"1500"
                }""";
        RestAssured.given()
                .contentType("application/json")
                .body(repository)
                .when()
                .post("/api/product")
                .then()
                .statusCode(201)
                .body("id",org.hamcrest.Matchers.notNullValue())
                .body("name",org.hamcrest.Matchers.equalTo("iphone 15"))
                .body("description",org.hamcrest.Matchers.equalTo("iphone 15 is a smatphone from apple"))
                .body("price",org.hamcrest.Matchers.equalTo("1500"));
    }

}
