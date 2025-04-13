package com.example.spring.transmiter;

import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/transmitter")
@RequiredArgsConstructor()
public class TransmissionController {

    private final TransmissionRepository repository;

    private final MeterRegistry meterRegistry;

    private final TransmissionService transmissionService;

    @WithSpan("first span")
    @PostMapping
    public void transmit(@RequestBody TransmissionDto transmissionDto) {
        log.info("Transmitting {}", transmissionDto);
        transmissionService.logSpan();
        repository.save(transmissionDto.toTransmission());
        meterRegistry.counter("transmission.count").increment();
    }

    public record TransmissionDto(String name, String value) {

        public Transmission toTransmission() {
            return new Transmission().setName(name).setValue(value);
        }

    }
}
