package com.thehecklers.aircraftpositions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient
@WebFluxTest({PositionController.class})
class PositionControllerTest {

    @MockitoBean
    private PositionRetriever positionRetriever;
    
    // 샘플 데이터 처리를 위한 객체 선언
    private Aircraft ac1, ac2;
    
    // 각각의 테스트 동작 전 기능 - 테스트 환경 구성
    @BeforeEach
    void setUp() {
        // 샘플 데이터 처리
        ac1 = new Aircraft(1L, "SAL001", "sqwk", "N12345", "SAL001",
                "STL-SF0", "LJ", "ct",
                30000,280,440,0,0,
                39.2979849,-94.71921, 80, 80,80,
                true, false,
                Instant.now(), Instant.now(), Instant.now());
        ac2 = new Aircraft(2L, "SAL002", "sqwk", "N54321", "SAL002",
                "SF0-STL", "LJ", "ct",
                40000,65,440,0,0,
                39.8560963,-14.6759263, 0D, 0D,0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        // Mock을 통한 응답환경 구성...
        Mockito.when(positionRetriever.retrieveAircraftPositions())
                .thenReturn(List.of(ac1, ac2));
    }
    
    // 각각의 테스트 동작 후 기능 - 테스트 환경 정리
    @AfterEach
    void tearDown() {
        System.out.println("After each test");
    }

    @Test
    void getCurrentAircraftPositions(@Autowired WebTestClient client) {     // getter/setter 를 통해 생성자 주입.
        System.out.println("getCurrentAircraftPositions");

        final Iterable<Aircraft> acPositions =  client.get().uri("/aircraft")
                .exchange()
                .expectStatus().isOk()                                  // 응답정보
                .expectBodyList(Aircraft.class)
                .returnResult()
                .getResponseBody();

//        System.out.println(acPositions);
        assertEquals(List.of(ac1, ac2), acPositions);
    }

    @Test
    void getTest(){
        System.out.println("getTest");
    }
}