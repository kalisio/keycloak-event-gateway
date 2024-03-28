package com.kalisio.keycloak;

import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class FeathersEmitterProviderFactory implements EventListenerProviderFactory {

    private static final Logger logger = Logger.getLogger(FeathersEmitterProviderFactory.class);

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new com.kalisio.keycloak.FeathersEmitterProvider(keycloakSession);
    }

    @Override
    public void init(Config.Scope config) {
        String endpoint = config.get("endpoint");
        logger.info("Initializing Feathers emitter");
        logger.infof("endpoint: %s", endpoint);
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "feathers-emitter";
    }
}
