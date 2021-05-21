/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jc.kafka.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.kafka.common.security.oauthbearer.OAuthBearerToken;

/**
 *
 * @author jean
 */
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class KeycloakBearerToken implements OAuthBearerToken {
    @JsonProperty("access_token")
    private String access;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("expires_in")
    private long lifetime;
    private final long start;
    private final AtomicReference<String> token;
    
    public KeycloakBearerToken() {
        this.token = new AtomicReference<>("");
        this.start = System.currentTimeMillis();
    }

    public KeycloakBearerToken(final String access) {
        this();
        this.access = access;
    }
    
    @Override
    public String value() {
        return this.access;
    }

    @Override
    public Set<String> scope() {
        return Arrays.stream(this.scope.split(" ")).collect(Collectors.toSet());
    }

    @Override
    public long lifetimeMs() {
        return this.start + (this.lifetime * 1000);
    }

    @Override
    public String principalName() {
        String principal;
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final JsonNode tree = mapper.readTree(
                new String(
                    Base64.getDecoder().decode(this.access.split("\\.")[1])
                )
            );
            principal = Optional
                .ofNullable(tree.findValue("clientId"))
                .map(value -> value.asText())
                .orElse("");
        } catch (JsonProcessingException ex) {
            Logger.getLogger(KeycloakBearerToken.class.getName()).log(Level.SEVERE, null, ex);
            principal = "";
        }
        return principal;
    }

    @Override
    public Long startTimeMs() {
        return this.start;
    }
    
}
