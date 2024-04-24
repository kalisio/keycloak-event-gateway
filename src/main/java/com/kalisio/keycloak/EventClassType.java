package com.kalisio.keycloak;

import static com.google.common.base.Preconditions.checkNotNull;

public enum EventClassType {

	EVENT("Event"), ADMIN_EVENT("AdminEvent");

	private final String label;

	EventClassType(
		final String label
	) {

		this.label = checkNotNull(label, "label");
	}

	public String label() {

		return label;
	}

	public static EventClassType byLabel(
		final String label
	) {

		for (final EventClassType eventClassType : values()) {

			if (eventClassType.label.equals(label)) {

				return eventClassType;
			}
		}

		throw new IllegalArgumentException("label: " + label + ", "//
				+ "should be one of: " //
				+ EVENT.label + ", " //
				+ ADMIN_EVENT.label);
	}

	@Override
	public String toString() {

		return label; // So in the JSON output we don’t get the uppercase name
	}
}
