package com.example.spring.transmiter;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transmitter")
@RequiredArgsConstructor()
public class TransmissionController {

    private final TransmissionRepository repository;

    private final MeterRegistry meterRegistry;

    @PostMapping
    public void transmit(@RequestBody TransmissionDto transmissionDto) {
        repository.save(transmissionDto.toTransmission());
        meterRegistry.counter("transmission.count").increment();
    }

    public record TransmissionDto(String name, String value) {

        public Transmission toTransmission() {
            return new Transmission().setName(name).setValue(value);
        }

    }
}
