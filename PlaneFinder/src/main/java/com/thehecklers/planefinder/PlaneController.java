package com.thehecklers.planefinder;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;

@Controller
public class PlaneController {

    private final PlaneFinderService pService;

    public PlaneController(PlaneFinderService pService) {
        this.pService = pService;
    }

    @ResponseBody
    @GetMapping("/aircraft")
    public Flux<Aircraft> getAircrafts() throws IOException {
        return pService.getAircraft();
    }

    @MessageMapping("acstream")
    public Flux<Aircraft> getCurrentACStream() throws IOException {
        return pService.getAircraft().concatWith(
                Flux.interval(Duration.ofSeconds(1))
                        .flatMap(l -> {
                            try {
                                return pService.getAircraft();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
        );
    }

}
