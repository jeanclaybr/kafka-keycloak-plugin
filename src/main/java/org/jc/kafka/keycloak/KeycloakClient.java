/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jc.kafka.keycloak;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import java.util.Base64;
import java.util.Map;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import org.apache.kafka.common.security.oauthbearer.OAuthBearerToken;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author jean
 */
public class KeycloakClient {

    private final Map<String, String> options;

    public KeycloakClient(final Map<String, String> options) {
        this.options = options;
    }

    public OAuthBearerToken login() {
        final ClientConfig config = new ClientConfig();
        config.register(JacksonJsonProvider.class);
        final KeycloakBearerToken entity = ClientBuilder.newClient(config)
            .target(System.getProperty(Options.KEYCLOAK_URL))
            .path("/auth/realms/{realm}/protocol/openid-connect/token")
            .resolveTemplate("realm", System.getProperty(Options.KEYCLOAK_REALM))
            .request(MediaType.APPLICATION_FORM_URLENCODED)
            .header("Authorization", basic())
            .post(body())
            .readEntity(KeycloakBearerToken.class);
        return entity;
    }

    private Entity<Form> body() {
        final Form form = new Form()
            .param("grant_type", "client_credentials")
            .param("client_id", System.getProperty(Options.KEYCLOAK_CLIENT_ID));
        return Entity.form(form);
    }

    private String basic() {
        return String.format(
            "Basic %s",
            Base64.getEncoder().encodeToString(
                String.format(
                    "secret:%s",
                    System.getProperty(Options.KEYCLOAK_CLIENT_SECRET)
                ).getBytes()
            )
        );
    }
}
