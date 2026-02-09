package com.BookManagement.BookManagement.Config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class AppTenantContext implements Filter {

    private static final String LOGGER_TENANT_ID = "tenant_id";
    public static final String TENANT_HEADER = "X-TenantID";
    private static final String DEFAULT_TENANT = "public";
    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static String getCurrentTenant() {
        String tenant = currentTenant.get();
        return Objects.requireNonNullElse(tenant, DEFAULT_TENANT);
    }

    public static void setCurrentTenant(String tenant) {
        MDC.put(LOGGER_TENANT_ID, tenant);
        currentTenant.set(tenant);
    }

    public static void clear() {
        MDC.clear();
        currentTenant.remove();
    }

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        String tenant = req.getHeader(TENANT_HEADER);
//        if (tenant != null) {
//            setCurrentTenant(tenant);
//        }
//        chain.doFilter(request, response);
//        clear();
//    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String tenant = req.getHeader(TENANT_HEADER);
        log.info("========Tenant========");
        log.info("Incoming request: {} {}",
                req.getMethod(), req.getRequestURI());
        log.info("Tenant header received: {}", tenant);

        if (tenant != null && !tenant.isBlank()) {
            setCurrentTenant(tenant);

            log.info("Tenant set in context: {}", tenant);

        } else {
            log.warn("No tenant header, using DEFAULT tenant");
        }

        try {
            chain.doFilter(request, response);
        } finally {
            clear();
        }
    }

}
