package org.jc.kafka.keycloak;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.AppConfigurationEntry;
import org.apache.kafka.common.security.auth.AuthenticateCallbackHandler;
import org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule;
import org.apache.kafka.common.security.oauthbearer.OAuthBearerTokenCallback;

/**
 *
 * @author jean
 */
public class KeycloakLoginCallbackHandler implements AuthenticateCallbackHandler {

    private final AtomicBoolean configured = new AtomicBoolean(false);
    private final Map<String, String> options = new HashMap<>();

    @Override
    public void configure(final Map<String, ?> map, final String mechanism,
        final List<AppConfigurationEntry> list) {
        if (!OAuthBearerLoginModule.OAUTHBEARER_MECHANISM.equals(mechanism)) {
            throw new IllegalArgumentException(
                String.format(
                    "Unsupported SASL mechanism: %s. Should be SASL_SSL",
                    mechanism
                )
            );
        }
        System.out.println("#################################################");
        System.out.println("map: " + map);
        System.out.println("mechanism: " + mechanism);
        System.out.println("AppConfigurationEntry: " + list);
        System.out.println("#################################################");
        if (map != null) {
            map.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .map(
                    entry -> new AbstractMap.SimpleEntry<>(
                        entry.getKey(), entry.getValue().toString()
                    )
                )
                .forEach(entry -> this.options.put(entry.getKey(), entry.getValue()));
        }
        this.configured.set(true);
    }

    @Override
    public void close() {
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException,
        UnsupportedCallbackException {
        if (isNotConfigured()) {
            throw new IllegalStateException("Callback handler not configured");
        }
        final OAuthBearerTokenCallback bearer = Arrays
            .stream(callbacks)
            .filter(callback -> callback instanceof OAuthBearerTokenCallback)
            .map(OAuthBearerTokenCallback.class::cast)
            .findFirst()
            .orElseThrow(() -> new UnsupportedCallbackException(callbacks[0]));
        acquireToken(bearer);
    }

    private boolean isNotConfigured() {
        return !this.configured.get();
    }

    private void acquireToken(final OAuthBearerTokenCallback callback) {
        if (callback.token() != null) {
            throw new IllegalStateException("Callback already has a token");
        }
        callback.token(new KeycloakClient(options).login());
    }

}
