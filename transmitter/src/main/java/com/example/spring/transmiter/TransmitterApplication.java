package com.example.spring.transmiter;

import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class TransmitterApplication {

    public static void main(String[] args) {
        MDC.put("trace_id", "init");
        SpringApplication.run(TransmitterApplication.class, args);
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder().baseUrl("http://localhost:8081").build();
    }
}
