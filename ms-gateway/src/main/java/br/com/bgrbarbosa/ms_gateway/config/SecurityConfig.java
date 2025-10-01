package br.com.bgrbarbosa.ms_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.crypto.spec.SecretKeySpec;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Order(-1)
public class SecurityConfig {

    @Value("${spring.security.jwt.secret}")
    private String jwtSecret;

    private static final String[] URLS_PUBLIC = { "/actuator/**", "/ms-oauth/**", "/ms-user/user/login/**"};

    private static final String[] URLS_USER = {
        "/ms-customer/customer/**", "/ms-exam/**", "/ms-scheduling/**", "/ms-notification/**"};

    private static final String[] URLS_ADMIN = {
        "/ms-customer/customer/**", "/ms-exam/**", "/ms-scheduling/**", "/ms-notification/**", "/ms-user/**"};


    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // Rotas liberadas publicamente
                        .pathMatchers(URLS_PUBLIC).permitAll()

                        // Rotas liberadas apenas para Admin
                        .pathMatchers(HttpMethod.PUT, URLS_ADMIN).hasRole("ADMIN")
                        .pathMatchers(HttpMethod.POST, URLS_ADMIN).hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, URLS_ADMIN).hasRole("ADMIN")

                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
                        .jwtAuthenticationConverter(reactiveJwtAuthenticationConverterAdapter())
                ))
                .build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(jwtSecret.getBytes(), "HmacSha256");
        return NimbusReactiveJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverterAdapter reactiveJwtAuthenticationConverterAdapter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<Map<String, String>> roles = jwt.getClaim("roles");

            if (roles == null) {
                return Collections.emptyList();
            }

            return roles.stream()
                    .map(roleMap -> roleMap.get("authority"))
                    .filter(authority -> authority != null && !authority.isEmpty())
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });

        return new ReactiveJwtAuthenticationConverterAdapter(converter);
    }



}
