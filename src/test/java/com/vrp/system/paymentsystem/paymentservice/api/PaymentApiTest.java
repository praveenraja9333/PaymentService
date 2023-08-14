package com.vrp.system.paymentsystem.paymentservice.api;


import com.vrp.system.paymentsystem.paymentservice.PaymentServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PaymentServiceApplication.class,webEnvironment = WebEnvironment.DEFINED_PORT)
class PaymentApiTest {
    static final String PROTOCOL="http://";
    static final String HOSTNAME="localhost:8080";
    static final String jsonPaylaod="{\n" +
            "  \"checkoutid\": \"c8be15de-4488-4490-9dc6-fab3f91435c6\",\n" +
            "  \"buyerinfo\":\"Praveen\",\n" +
            "  \"currencycode\":\"INR\",\n" +
            "  \"paymentOrderList\":[\n" +
            "    {\n" +
            "      \"sellername\":\"abc\",\n" +
            "      \"amount\":\"3241.83\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"sellername\":\"abc1\",\n" +
            "      \"amount\":\"4441.83\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    enum Endpoints{
        PAYMENT_ENDPOINT("/api/v1/payment");
        final String endpoint;
        Endpoints(String endpoint){
            this.endpoint=endpoint;
        }
        public String getUrl(){
            return PROTOCOL+HOSTNAME+endpoint;
        }
    }

    @Autowired
    private PaymentApi paymentApi;
    @Test
    void postPayment() throws IOException, InterruptedException {
        assertEquals("http://localhost:8080/api/v1/payment",Endpoints.PAYMENT_ENDPOINT.getUrl());
        HttpRequest request=HttpRequest.newBuilder(URI.create(Endpoints.PAYMENT_ENDPOINT.getUrl())).header(HttpHeaders.CONTENT_TYPE,"application/json").method(HttpMethod.POST.name(),HttpRequest.BodyPublishers.ofString(jsonPaylaod)).build();
        HttpResponse<String> response= HttpClient.newHttpClient().send(request,HttpResponse.BodyHandlers.ofString());

        assertEquals("0",response.body());
    }


}