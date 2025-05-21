//package com.caracal.microservices.order;
//
//import com.caracal.microservices.order.stubs.InventoryClientStub;
//import org.flywaydb.database.mysql.MySQLConnection;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
//import org.testcontainers.containers.MySQLContainer;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWireMock(port = 0)
//class OrderServiceApplicationTests {
//
//    @ServiceConnection
//    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");
//
//    @LocalServerPort
//    private int port;
//
//    @BeforeEach
//    void setup() {
//        RestAssured.baseURI = "http://localhost";
//        RestAssured.port = port;
//    }
//
//    static {
//        mySQLContainer.start();
//    }
//
//    @Test
//    void shouldSubmitOrder() {
//        String submitOrderJson = """
//                {
//                    "skuCode": "Samsung Galaxy S23",
//                    "price": 800,
//                    "quantity": 50
//                }
//                """;
//        InventoryClientStub.stubInventoryCall("iphone_15", 1);
//        var responseBodyString = RestAssured.given()
//                .contentType("application/json")
//                .body(submitOrderJson)
//                .when()
//                .post("api/order")
//                .then()
//                .log().all()
//                .statusCode(201)
//                .extract()
//                .body().asString();
//
//        assertThat(responseBodyString, Matchers.is("Order Placed"));
//    }
//
//}