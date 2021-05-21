/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jc.kafka.keycloak;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author jean
 */
public class KafkaLoginConfigTest {

    /*
    configs: 
    KAFKA_REALM
    
    */
    private KeycloakLoginCallbackHandler callback;

    @BeforeEach
    public void setUp() {
        this.callback = new KeycloakLoginCallbackHandler();
    }

    @Test
    public void shouldUseSasSsl() {
        final Throwable thrown = Assertions.catchThrowable(
            () -> this.callback.configure(null, "SASL_PLAIN", null)
        );
        final String msg =
            "Unsupported SASL mechanism: SASL_PLAIN. Should be SASL_SSL";
        Assertions
            .assertThat(thrown)
            .hasMessage(msg);
    }
}
