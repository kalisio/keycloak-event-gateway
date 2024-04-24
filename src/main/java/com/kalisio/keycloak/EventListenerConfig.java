package com.kalisio.keycloak;

import javax.annotation.Nullable;

public interface EventListenerConfig {

	String KEYCLOAK_EVENT_HTTP_LISTENER_URL = "keycloakEventHttpListenerUrl";
	String ACCESS_TOKEN = "accessToken";
	String KEYCLOAK_EVENT_QUEUE = "keycloakEventQueue";

	@Nullable
	String getPrefix();

	@Nullable
	String getHttpListenerUrl();

	@Nullable
	String getAccessToken();

	@Nullable
	String getQueueName();
}
