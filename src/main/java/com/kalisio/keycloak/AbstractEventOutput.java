package com.kalisio.keycloak;

import static com.google.common.base.Preconditions.checkNotNull;

import org.joda.time.DateTime;

public class AbstractEventOutput {

	private final String kcVersion; // e.g. "22.0.5"
	private final EventClassType eventClassType;
	private final long time;
	private final String realmId;
	private final String realmName;

	protected AbstractEventOutput(
		final String kcVersion,
		final EventClassType eventClassType,
		final long time,
		final String realmId,
		final String realmName
	) {

		this.kcVersion = checkNotNull(kcVersion, "kcVersion");
		this.eventClassType = checkNotNull(eventClassType, "eventClassType");
		this.time = time;
		this.realmId = checkNotNull(realmId, "realmId");
		this.realmName = checkNotNull(realmName, "realmName");
	}

	public final String getKcVersion() {

		return kcVersion;
	}

	public final String getEventClass() {

		return eventClassType.label();
	}

	public final long getTime() {

		return time;
	}

	public final String getDateTime() {

		return new DateTime(time).toString();
	}

	public final String getRealmId() {

		return realmId;
	}

	public final String getRealmName() {

		return realmName;
	}
}
