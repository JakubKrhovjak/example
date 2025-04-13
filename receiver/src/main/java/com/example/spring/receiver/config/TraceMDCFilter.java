package com.example.spring.receiver.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.opentelemetry.api.trace.Span;

import org.slf4j.MDC;

import java.io.IOException;

@Component
public class TraceMDCFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            Span span = Span.current();
            if (span.getSpanContext().isValid()) {
                MDC.put("trace_id", span.getSpanContext().getTraceId());
            }
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}