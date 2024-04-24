package com.kalisio.keycloak;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jboss.logging.Logger;
import org.keycloak.connections.httpclient.HttpClientProvider;
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

		final HttpUriRequest request = RequestBuilder //
				.post(url) //
				.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON)) //
				.setHeader(HttpHeaders.CONTENT_TYPE, "application/json") //
				.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) //
				.build();

		try {

			final CloseableHttpClient httpClient = session.getProvider(HttpClientProvider.class) //
					.getHttpClient();

			httpClient.execute(request);

			// We do not close the HttpClient ourselves; It is the responsability
			// of the provider.

		} catch (final IOException e) {

			log.errorf(e, "While sending JSON to: %s", url);

			throw new RuntimeException(e);
		}

		if (log.isDebugEnabled()) {
			log.debugf("Sending JSON to: %s done.", url);
		}
	}
}
