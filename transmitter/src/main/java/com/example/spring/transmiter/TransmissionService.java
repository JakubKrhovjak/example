package com.example.spring.transmiter;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import org.springframework.stereotype.Service;

@Service
public class TransmissionService {

    @WithSpan("logSpan")
    public void logSpan() {

    }
}
