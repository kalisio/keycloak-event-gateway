package com.kalisio.keycloak;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nullable;

import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;

import com.fasterxml.jackson.databind.JsonNode;

public class AdminEventOutput extends AbstractEventOutput {

	private final AuthDetailsOutput authDetails;
	private final OperationType operationType;
	private final ResourceType resourceType;
	private final String resourcePath;
	@Nullable
	private final String error;
	@Nullable
	private final String representation;
	@Nullable
	private final JsonNode value;

	public AdminEventOutput(
		final String kcVersion,
		final long time,
		final String realmId,
		final String realmName,
		final AuthDetailsOutput authDetails,
		final OperationType operationType,
		final ResourceType resourceType,
		final String resourcePath,
		@Nullable final String error,
		@Nullable final String representation,
		@Nullable final JsonNode value
	) {
		super( //
				kcVersion, //
				EventClassType.ADMIN_EVENT, //
				time, //
				realmId, //
				realmName);

		this.authDetails = checkNotNull(authDetails, "authDetails");
		this.operationType = checkNotNull(operationType, "operationType");
		this.resourceType = checkNotNull(resourceType, "resourceType");
		this.resourcePath = checkNotNull(resourcePath, "resourcePath");
		this.error = error;
		this.representation = representation;
		this.value = value;
	}

	public AuthDetailsOutput getAuthDetails() {

		return authDetails;
	}

	public OperationType getOperationType() {

		return operationType;
	}

	public ResourceType getResourceType() {

		return resourceType;
	}

	public String getResourcePath() {

		return resourcePath;
	}

	@Nullable
	public String getError() {

		return error;
	}

	@Nullable
	public String getRepresentation() {

		return representation;
	}

	@Nullable
	public JsonNode getValue() {

		return value;
	}
}
