package com.thehecklers.aircraftpositions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class PositionControllerTest {

    // 각각의 테스트 동작 전 기능 - 테스트 환경 구성
    @BeforeEach
    void setUp() {
        System.out.println("Before each test");
    }
    
    // 각각의 테스트 동작 후 기능 - 테스트 환경 정리
    @AfterEach
    void tearDown() {
        System.out.println("After each test");
    }

    @Test
    void getCurrentAircraftPositions(@Autowired WebTestClient client) {
        System.out.println("getCurrentAircraftPositions");

        assert  client.get().uri("/aircraft")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Iterable.class)
                .returnResult()
                .getResponseBody()
                .iterator()
                .hasNext();
    }

    @Test
    void getTest(){
        System.out.println("getTest");
    }
}