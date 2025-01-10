package com.thehecklers.aircraftpositions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// 슬라이스 테스트
@DataJpaTest
class AircraftRepositoryTest {

    // 의존성 주입
    @Autowired
    private AircraftRepository repository;

    private Aircraft ac1, ac2;


    @BeforeEach
    void setUp() {
        // 샘플링 데이터를 repository 저장...
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

        repository.saveAll(List.of(ac1, ac2));
    }

    @AfterEach
    void tearDown() {
        // 테스트에 사용했던 샘플링 데이터를 제거...
        repository.deleteAll();

    }

    @Test
    void testFindAll() {
        // 저장소에 있는 데이터를 불러오는 테스트 지정..
//        System.out.println(repository.findAll());
        assertEquals(List.of(ac1, ac2), repository.findAll());
    }

    @Test
    void testFindById() {
        assertEquals(ac1, repository.findById(1L).get());
        assertEquals(ac2, repository.findById(2L).get());
    }
}