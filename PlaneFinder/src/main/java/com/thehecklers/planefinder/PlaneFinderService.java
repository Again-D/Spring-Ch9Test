package com.thehecklers.planefinder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class PlaneFinderService {
    private final PlaneRepository repo;
    private final FlightGenerator generator;
    private URL acURL;
    private final ObjectMapper om;

    @SneakyThrows
    public PlaneFinderService(PlaneRepository repo, FlightGenerator generator) {
        this.repo = repo;
        this.generator = generator;

//        acURL = new URL("http://192.168.1.139/ajax/aircraft");   // JAVA 17...
        acURL = (new URI("http://localhost/ajax/aircraft")).toURL();
        om = new ObjectMapper();
    }

    public Flux<Aircraft> getAircraft() throws IOException {
        List<Aircraft> positions = new ArrayList<>();
//        log.info("getAircraft called");
        JsonNode aircraftNodes = null;
        try {
            aircraftNodes = om.readTree(acURL.openStream()).get("aircraft");
            aircraftNodes.iterator().forEachRemaining(node -> {
                try{
                    positions.add(om.treeToValue(node, Aircraft.class));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.println("\n>>> IO Exception: "+e.getLocalizedMessage()+", generating and providing sample date.\n");
            return saveSamplePositions();
        }

        if (positions.size() > 0) {
            positions.forEach(System.out::println);

            repo.deleteAll();
            return repo.saveAll(positions);
        }else {
            System.out.println("\n>>> No positions to report, generating and provideing sample data.\n");
            return saveSamplePositions();
        }
    }

    private Flux<Aircraft> saveSamplePositions() {
        final Random rnd = new Random();

        // DbConxInit 코드를 참조하여 동작 화인
        for(int i=0; i<rnd.nextInt(15); i++) {
            repo.save(generator.generate())
                    .thenMany(repo.findAll())
                    .subscribe();
        }
        return repo.findAll();
        // 한번에 처리하는 코드 (단, id가 null로 나옴)
//        return Flux.range(1, 10)
//                .map(i -> generator.generate())
//                .flatMap(repo::save);

    }
}