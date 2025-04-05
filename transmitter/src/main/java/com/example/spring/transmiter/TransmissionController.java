package com.example.spring.transmiter;

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

    @PostMapping
    public void transmit(@RequestBody TransmissionDto transmissionDto) {
        repository.save(transmissionDto.toTransmission());
    }

    public record TransmissionDto(String name, String value) {

        public Transmission toTransmission() {
            return new Transmission().setName(name).setValue(value);
        }

    }
}
