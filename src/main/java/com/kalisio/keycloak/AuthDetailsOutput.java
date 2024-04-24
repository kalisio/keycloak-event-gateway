package com.kalisio.keycloak;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nullable;

public class AuthDetailsOutput {

	private final String ipAddress;
	private final String realmId;
	private final String realmName;
	@Nullable
	private final String clientId;
	private final String userId;
	private final String username;

	public AuthDetailsOutput(
		final String ipAddress,
		final String realmId,
		final String realmName,
		@Nullable final String clientId,
		final String userId,
		final String username
	) {

		this.ipAddress = checkNotNull(ipAddress, "ipAddress");
		this.realmId = checkNotNull(realmId, "realmId");
		this.realmName = checkNotNull(realmName, "realmName");
		this.clientId = clientId;
		this.userId = checkNotNull(userId, "userId");
		this.username = checkNotNull(username, "username");
	}

	public String getIpAddress() {

		return ipAddress;
	}

	public String getRealmId() {

		return realmId;
	}

	public String getRealmName() {

		return realmName;
	}

	@Nullable
	public String getClientId() {

		return clientId;
	}

	public String getUserId() {

		return userId;
	}

	public String getUsername() {

		return username;
	}
}
