package com.kalisio.keycloak;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.kalisio.keycloak.EventListenerConfig.KEYCLOAK_EVENT_HTTP_LISTENER_URL;
import static com.kalisio.keycloak.EventListenerConfig.KEYCLOAK_EVENT_QUEUE;
import static com.kalisio.keycloak.EventListenerConfigUtils.extractEventListenerConfigs;

import java.util.Map;

import javax.annotation.Nullable;

import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.AuthDetails;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class CustomEventListenerProvider implements EventListenerProvider {

	private final String kcVersion; // e.g. "22.0.5"

	private static final String KEYCLOAK_EVENT_GATEWAY_USER = "keycloak-event-gateway";

	private static final Logger log = Logger.getLogger(CustomEventListenerProvider.class);

	private final KeycloakSession session;

	public CustomEventListenerProvider(
		final String kcVersion,
		final KeycloakSession session
	) {

		this.kcVersion = checkNotNull(kcVersion, "keycloakVersion");

		this.session = session;
	}

	@Override
	public void onEvent(
		final Event event
	) {
		final long nowMs = System.currentTimeMillis();

		final String realmId = event.getRealmId();
		final String ipAddress = event.getIpAddress();
		@Nullable
		final String clientId = event.getClientId();
		@Nullable
		final String sessionId = event.getSessionId();
		final EventType type = event.getType();
		final String userId = event.getUserId();
		@Nullable
		final String error = event.getError();
		final Map<String, String> details = event.getDetails();

		final RealmModel realm = session.realms().getRealm(event.getRealmId());
		final UserModel user = session.users().getUserById(realm, userId);
		final String username = user.getUsername();

		final EventOutput eventOutput = new EventOutput( //
				kcVersion, //
				nowMs, //
				realmId, //
				realm.getName(), //
				ipAddress, //
				clientId, //
				sessionId, //
				type, //
				userId, //
				username, //
				error, //
				details);

		if (type == EventType.REGISTER) {

			log.infof("## NEW %s EVENT", event.getType());
			log.info("-----------------------------------------------------------");

			final String email = user.getEmail();

			log.infof("Email: %s", email);
			log.infof("Username: %s", username);
			log.infof("Client: %s", clientId);

			log.info("");
		}

		send(extractEventContext(event), eventOutput);
	}

	@Override
	public void onEvent(
		final AdminEvent adminEvent,
		final boolean includeRepresentation
	) {
		if (log.isDebugEnabled()) {

			log.debug("onEvent(AdminEvent)...");
		}

		final long nowMs = System.currentTimeMillis();

		final String realmId = adminEvent.getRealmId();
		final OperationType operationType = adminEvent.getOperationType();
		final ResourceType resourceType = adminEvent.getResourceType();
		final String resourcePath = adminEvent.getResourcePath();
		@Nullable
		final String error = adminEvent.getError();
		@Nullable
		final String representation = adminEvent.getRepresentation();

		final RealmModel realm = session.realms().getRealm(adminEvent.getRealmId());

		final AuthDetails authDetails = adminEvent.getAuthDetails();
		final String authDetailsRealmId = authDetails.getRealmId();
		final RealmModel authDetailsRealm = session.realms().getRealm(authDetailsRealmId);
		final String authDetailsUserId = authDetails.getUserId();
		final UserModel authDetailsUser = session.users().getUserById(authDetailsRealm, authDetailsUserId);

		final AuthDetailsOutput authDetailsOutput = new AuthDetailsOutput( //
				authDetails.getIpAddress(), //
				authDetailsRealmId, //
				authDetailsRealm.getName(), //
				authDetails.getClientId(), //
				authDetailsUserId, //
				authDetailsUser.getUsername());

		final AdminEventOutput adminEventOutput = new AdminEventOutput( //
				kcVersion, //
				nowMs, //
				realmId, //
				realm.getName(), //
				authDetailsOutput, //
				operationType, //
				resourceType, //
				resourcePath, //
				error, //
				representation);

		send(extractEventContext(adminEvent), adminEventOutput);
	}

	@Override
	public void close() {

	}

	private void send(
		final EventContext eventContext,
		final AbstractEventOutput eventOutput
	) {

		final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

		final String json;

		try {

			json = objectWriter.writeValueAsString(eventOutput);

		} catch (final JsonProcessingException e) {

			throw new RuntimeException(e);
		}

		if (log.isDebugEnabled()) {

			log.debugf("send(): JSON to be sent: %s", json);
		}

		// final JSONObject jsonObject = new JSONObject(json); // to send it via RESTful

		if (log.isInfoEnabled()) {

			log.info("send(): Sending JSON...");
		}

		// Actually send the JSON

		final String realmName = eventContext.getRealmName();

		final RealmModel realm = session.realms().getRealmByName(realmName);

		final UserModel eventEmitterUser = session.users().getUserByUsername(realm, KEYCLOAK_EVENT_GATEWAY_USER);

		if (eventEmitterUser == null) {
			log.warnf("No \"%s\" user has been declared in realm: %s", //
					KEYCLOAK_EVENT_GATEWAY_USER, //
					realmName);
			return;
		}

		final EventListenerConfig[] configs = extractEventListenerConfigs(eventEmitterUser.getAttributes());

		if (configs.length == 0) {
			log.warnf("No configuration via custom attributes for the \"%s\" user in realm: %s has been declared.", //
					KEYCLOAK_EVENT_GATEWAY_USER, //
					realmName);
			return;
		}

		for (final EventListenerConfig config : configs) {

			@Nullable
			final String prefix = config.getPrefix();
			@Nullable
			final String httpListenerUrl = config.getHttpListenerUrl();
			@Nullable
			final String accessToken = config.getAccessToken();
			@Nullable
			final String queueName = config.getQueueName();

			final String prefixForLogging = (prefix == null) ? "" : (prefix + ".");

			if (queueName != null) {

				log.errorf("Not implemented: %s%s: %s != null", //
						prefixForLogging, //
						KEYCLOAK_EVENT_QUEUE, //
						queueName);

			} else if (httpListenerUrl != null) {

				new Thread() {

					@Override
					public void run() {

						new CustomHttpConnector( //
								session, //
								httpListenerUrl, //
								accessToken //
						).send(json);
					}

				}.start();

			} else {

				log.warnf("Either %s%s or %s%s should be non null", //
						prefixForLogging, //
						KEYCLOAK_EVENT_HTTP_LISTENER_URL, //
						prefixForLogging, //
						KEYCLOAK_EVENT_QUEUE);
			}
		}
	}

	private EventContext extractEventContext(
		final Event event
	) {

		return new EventContext() {

			@Override
			public String getRealmName() {

				return session.realms().getRealm(event.getRealmId()).getName();
			}
		};
	}

	private EventContext extractEventContext(
		final AdminEvent adminEvent
	) {

		return new EventContext() {

			@Override
			public String getRealmName() {

				return session.realms().getRealm(adminEvent.getRealmId()).getName();
			}
		};
	}

	private interface EventContext {

		String getRealmName();
	}
}
