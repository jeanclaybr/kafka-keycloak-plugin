/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jc.kafka.keycloak;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import java.util.HashMap;
import org.apache.kafka.common.security.oauthbearer.OAuthBearerToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author jean
 */
public class KeycloakClientTest {
    
    public KeycloakClientTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of login method, of class KeycloakClient.
     * {
  "realm": "kafka",
  "auth-server-url": "http://localhost:8080/auth/",
  "ssl-required": "external",
  "resource": "kafka-brokers",
  "credentials": {
    "secret": "4290fe2e-a909-48fe-a171-9dde28fa7266"
  },
  "confidential-port": 0
}
     */
    @Test
    public void testLogin() throws Exception {
        final String url = "http://localhost:8080";
        final String realm = "kafka";
        final String client = "kafka-brokers";
        final String secret = "4290fe2e-a909-48fe-a171-9dde28fa7266";
        final ClientConfig config = new ClientConfig();
        config.register(JacksonJsonProvider.class);
        final KeycloakClient keycloak = new KeycloakClient(
            new HashMap<String, String>(){{
                put(Options.KEYCLOAK_CLIENT_ID, client);
                put(Options.KEYCLOAK_CLIENT_SECRET, secret);
                put(Options.KEYCLOAK_REALM, realm);
                put(Options.KEYCLOAK_URL, url);
            }}
        );
        final OAuthBearerToken entity = keycloak.login();
        final long now = System.currentTimeMillis();
        Assertions.assertThat(entity.lifetimeMs()).isGreaterThan(now);
        Assertions.assertThat(entity.principalName()).isEqualTo(client);
        Assertions.assertThat(entity.value()).isNotEmpty();
    }
    
}
