package com.caracal.microservices.order.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class InventoryClientStub {

    public static void stubInventoryCall(String skuCode, Integer quantity) {
        String url = "/api/inventory?skuCode=" + skuCode + "&quantity=" + quantity;
        System.out.println(">>> Stubbing inventory call for URL: " + url);
        stubFor(get(urlEqualTo(url))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")
                ));
    }
}
