package com.example.spring.transmiter;

import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@Slf4j
@RestController
@RequestMapping("/transmitter")
@RequiredArgsConstructor
public class TransmissionController {

    private final TransmissionRepository repository;

    private final MeterRegistry meterRegistry;

    private final TransmissionService transmissionService;

    private final RestClient restClient;

     @WithSpan("first span")
    @PostMapping
    public void transmit(@RequestBody TransmissionDto transmissionDto) {
        log.info("Transmitting {}", transmissionDto);
        transmissionService.logSpan();
        repository.save(transmissionDto.toTransmission());
        meterRegistry.counter("transmission.count").increment();
         var retrieve = restClient.post()
                 .uri("/receive")
                 .body(transmissionDto.value)
                 .retrieve()
                 .body(String.class);
     }

    public record TransmissionDto(String name, String value) {

        public Transmission toTransmission() {
            return new Transmission().setName(name).setValue(value);
        }

    }
}
