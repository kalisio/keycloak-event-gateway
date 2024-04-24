package com.kalisio.keycloak;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.annotation.Nullable;

import org.keycloak.events.EventType;

import com.google.common.collect.ImmutableMap;

public class EventOutput extends AbstractEventOutput {

	private final String ipAddress;
	@Nullable
	private final String clientId;
	@Nullable
	private final String sessionId;
	private final EventType type;
	private final String userId;
	private final String username;
	@Nullable
	private final String error;
	private final Map<String, String> details;

	public EventOutput(
		final String kcVersion,
		final long time,
		final String realmId,
		final String realmName,
		final String ipAddress,
		@Nullable final String clientId,
		@Nullable final String sessionId,
		final EventType type,
		final String userId,
		final String username,
		@Nullable final String error,
		final Map<String, String> details
	) {
		super( //
				kcVersion, //
				EventClassType.EVENT, //
				time, //
				realmId, //
				realmName);

		this.ipAddress = checkNotNull(ipAddress, "ipAddress");
		this.clientId = clientId;
		this.sessionId = sessionId;
		this.type = checkNotNull(type, "type");
		this.userId = checkNotNull(userId, "userId");
		this.username = checkNotNull(username, "username");
		this.error = error;
		this.details = checkNotNull(details, "details");
	}

	public String getIpAddress() {

		return ipAddress;
	}

	@Nullable
	public String getClientId() {

		return clientId;
	}

	@Nullable
	public String getSessionId() {

		return sessionId;
	}

	public EventType getType() {

		return type;
	}

	public String getUserId() {

		return userId;
	}

	public String getUsername() {

		return username;
	}

	@Nullable
	public String getError() {

		return error;
	}

	public Map<String, String> getDetails() {

		return ImmutableMap.copyOf(details);
	}
}
