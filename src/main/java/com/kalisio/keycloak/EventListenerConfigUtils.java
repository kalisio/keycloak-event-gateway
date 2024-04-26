package com.kalisio.keycloak;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.kalisio.keycloak.EventListenerConfig.ACCESS_TOKEN;
import static com.kalisio.keycloak.EventListenerConfig.KEYCLOAK_EVENT_HTTP_LISTENER_URL;
import static com.kalisio.keycloak.EventListenerConfig.KEYCLOAK_EVENT_QUEUE;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public abstract class EventListenerConfigUtils {

	public static EventListenerConfig[] extractEventListenerConfigs(
		final Map<String, List<String>> attributes
	) {

		checkNotNull(attributes, "attributes");

		final Map<String, Map<SplitKey, String>> configAttributesByKeys = Maps.newHashMap();

		for (final String key : attributes.keySet()) {

			final List<String> values = attributes.get(key);

			if (values.isEmpty()) {

				continue;
			}

			final String value = values.get(0); // We only use the first value

			@Nullable
			final String prefix;
			final String command;
			@Nullable
			final Integer splitIndex;

			final int index = key.indexOf(".");

			if (index == -1) {

				prefix = null;
				command = key;
				splitIndex = null;

			} else {

				final int index2 = key.indexOf(".", index + 1);

				if (index2 != -1) {

					prefix = key.substring(0, index);
					command = key.substring(index + 1, index2);
					splitIndex = parseIntOrNull(key.substring(index2 + 1));

				} else {

					final String left = key.substring(0, index);
					final String right = key.substring(index + 1);

					if (ACCESS_TOKEN.equals(left)) {

						prefix = null;
						command = left;
						splitIndex = parseIntOrNull(key.substring(index + 1));

					} else {

						prefix = left;
						command = right;
						splitIndex = null;
					}
				}
			}

			final String configKey = (prefix == null) ? "" : prefix;

			if (KEYCLOAK_EVENT_HTTP_LISTENER_URL.equals(command) //
					|| ACCESS_TOKEN.equals(command) //
					|| KEYCLOAK_EVENT_QUEUE.equals(command)) {

				final Map<SplitKey, String> configAttributes;

				if (configAttributesByKeys.containsKey(configKey)) {

					configAttributes = configAttributesByKeys.get(configKey);

				} else {

					configAttributes = Maps.newHashMap();

					configAttributesByKeys.put(configKey, configAttributes);
				}

				configAttributes.put(new SplitKey(command, splitIndex), value);
			}
		}

		final List<EventListenerConfig> configs = Lists.newArrayList();

		for (final String configKey : Sets.newTreeSet(configAttributesByKeys.keySet())) {

			final Map<SplitKey, String> configAttributes = configAttributesByKeys.get(configKey);

			configs.add(new EventListenerConfig() {

				@Override
				@Nullable
				public String getPrefix() {
					return (configKey == null || configKey.isBlank()) //
							? null //
							: configKey;
				}

				@Override
				@Nullable
				public String getHttpListenerUrl() {
					return concatenateSplitValues(configAttributes, KEYCLOAK_EVENT_HTTP_LISTENER_URL);
				}

				@Override
				@Nullable
				public String getAccessToken() {
					return concatenateSplitValues(configAttributes, ACCESS_TOKEN);
				}

				@Override
				@Nullable
				public String getQueueName() {
					return concatenateSplitValues(configAttributes, KEYCLOAK_EVENT_QUEUE);
				}
			});
		}

		return Iterables.toArray(configs, EventListenerConfig.class);
	}

	@Nullable
	private static String concatenateSplitValues(
		final Map<SplitKey, String> configAttributes,
		final String command
	) {

		final StringBuilder sb = new StringBuilder();

		final Map<Integer, String> splitValues = Maps.newHashMap();

		for (final Map.Entry<SplitKey, String> entry : configAttributes.entrySet()) {

			final SplitKey splitKey = entry.getKey();
			final String value = entry.getValue();

			if (!splitKey.command.equals(command)) {
				continue;
			}

			if (splitKey.splitIndex == null) {
				return value;
			}

			splitValues.put(splitKey.splitIndex, value);
		}

		if (splitValues.isEmpty()) {

			return null;
		}

		splitValues.keySet().stream().sorted().forEach(splitIndex

		-> sb.append(splitValues.get(splitIndex)));

		return sb.toString();
	}

	@Nullable
	private static Integer parseIntOrNull(
		final String s
	) {

		try {

			return Integer.parseInt(s);

		} catch (final NumberFormatException e) {

			return null;
		}
	}

	private static class SplitKey {

		/**
		 * e.g. "accessToken", "keycloakEventHttpListenerUrl", "keycloakEventQueue"
		 */
		public final String command;

		@Nullable
		public final Integer splitIndex;

		public SplitKey(
			final String command,
			@Nullable final Integer splitIndex
		) {

			this.command = checkNotNull(command, "command");
			this.splitIndex = splitIndex;
		}
	}
}
