package com.BookManagement.BookManagement.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component
public class HttpLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        ContentCachingRequestWrapper wrappedRequest =
                new ContentCachingRequestWrapper(request);

        ContentCachingResponseWrapper wrappedResponse =
                new ContentCachingResponseWrapper(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        logRequest(wrappedRequest);
        logResponse(wrappedResponse, start);

        wrappedResponse.copyBodyToResponse();
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        String body = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);

        System.out.println("➡ METHOD: " + request.getMethod());
        System.out.println("➡ URL: " + request.getRequestURI());
        System.out.println("➡ HEADERS:");

        Collections.list(request.getHeaderNames())
                .forEach(h -> System.out.println("   " + h + " = " + request.getHeader(h)));

        System.out.println("➡ BODY: " + body);
    }

    private void logResponse(ContentCachingResponseWrapper response, long start) throws IOException {
        long time = System.currentTimeMillis() - start;
        String body = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);

        System.out.println("⬅ STATUS: " + response.getStatus());
        System.out.println("⬅ TIME: " + time + "ms");
        System.out.println("⬅ RESPONSE BODY: " + body);
    }
}

