package com.thehecklers.aircraftpositions;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
public class PositionController {
//    @NonNull
//    private final AircraftRepository repository;
//    private WebClient client =
//            WebClient.create("http://localhost:7634/aircraft");
    private final PositionRetriever retriever;

    @GetMapping("/aircraft")
    public Iterable<Aircraft> getCurrentAircraftPositions() {
        return retriever.retrieveAircraftPositions();
//        repository.deleteAll();
//
//        client.get()
//                .retrieve()
//                .bodyToFlux(Aircraft.class)
//                .filter(plane -> !plane.getReg().isEmpty())
//                .toStream()
//                .forEach(repository::save);
//
//        return repository.findAll();
    }
}