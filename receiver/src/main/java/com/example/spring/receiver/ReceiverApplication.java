package com.example.spring.receiver;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping
@RestController
@SpringBootApplication
@RequiredArgsConstructor
public class ReceiverApplication {

    public static void main(String[] args) {
        MDC.put("trace_id", "init");
        SpringApplication.run(ReceiverApplication.class, args);
    }

    private final  Tracer tracer;


    @PostMapping("/receive")
    public void receive(String message) {
        log.info("Received message: {}", message);

        var span = tracer.spanBuilder("ManualSpan").startSpan();
        try (var scope = span.makeCurrent()) {
            log.info("Manual span method manualSpan1");
        } finally {
            span.end();  // Ensure span is ended.
        }
    }

}
