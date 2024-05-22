package com.kalisio.keycloak;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.jboss.logging.Logger;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.KeycloakSession;

public class CustomHttpConnector {

	private final String url;
	private final String accessToken;

	private static final Logger log = Logger.getLogger(CustomHttpConnector.class);

	private final KeycloakSession session;

	public CustomHttpConnector(
		final KeycloakSession session,
		final String url,
		final String accessToken
	) {

		this.session = checkNotNull(session, "session");
		this.url = checkNotNull(url, "url");
		this.accessToken = checkNotNull(accessToken, "accessToken");
	}

	public void send(
		final String json
	) {

		if (log.isDebugEnabled()) {
			log.debugf("Sending JSON to: %s...", url);
		}
		try {

			@SuppressWarnings("unused")
			final int status;

			status = SimpleHttp.doPost(url, session) //
					.entity(new StringEntity(json, ContentType.APPLICATION_JSON)) //
					.asStatus();

		} catch (final IOException e) {

			log.errorf(e, "While sending JSON to: %s", url);

			throw new RuntimeException(e);
		}

		if (log.isDebugEnabled()) {
			log.debugf("Sending JSON to: %s done.", url);
		}
	}
}
