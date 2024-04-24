package com.kalisio.keycloak;

import javax.annotation.Nullable;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class CustomEventListenerProviderFactory implements EventListenerProviderFactory {

	@Nullable
	private String kcVersion = null; // e.g. "22.0.5"

	@Override
	public EventListenerProvider create(
		final KeycloakSession keycloakSession
	) {

		return new CustomEventListenerProvider( //
				kcVersion, //
				keycloakSession);
	}

	@Override
	public void init(
		final Config.Scope scope
	) {
		kcVersion = System.getProperty("kc.version"); // e.g. "22.0.5"
	}

	@Override
	public void postInit(
		final KeycloakSessionFactory keycloakSessionFactory
	) {

	}

	@Override
	public void close() {

	}

	@Override
	public String getId() {

		return "keycloak-event-gateway";
	}
}