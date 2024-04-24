package com.kalisio.keycloak;

import static com.kalisio.keycloak.EventListenerConfig.ACCESS_TOKEN;
import static com.kalisio.keycloak.EventListenerConfig.KEYCLOAK_EVENT_HTTP_LISTENER_URL;
import static com.kalisio.keycloak.EventListenerConfig.KEYCLOAK_EVENT_QUEUE;
import static com.google.common.base.Preconditions.checkNotNull;

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

		final Map<String, Map<String, String>> configAttributesByKeys = Maps.newHashMap();

		for (final String key : attributes.keySet()) {

			final List<String> values = attributes.get(key);

			if (values.isEmpty()) {

				continue;
			}

			final String value = values.get(0); // We only use the first value

			@Nullable
			final String prefix;
			final String suffix;

			final int index = key.indexOf(".");

			if (index == -1) {

				prefix = null;
				suffix = key;

			} else {

				prefix = key.substring(0, index);
				suffix = key.substring(index + 1);
			}

			final String configKey = (prefix == null) ? "" : prefix;

			if (KEYCLOAK_EVENT_HTTP_LISTENER_URL.equals(suffix) //
					|| ACCESS_TOKEN.equals(suffix) //
					|| KEYCLOAK_EVENT_QUEUE.equals(suffix)) {

				final Map<String, String> configAttributes;

				if (configAttributesByKeys.containsKey(configKey)) {

					configAttributes = configAttributesByKeys.get(configKey);

				} else {

					configAttributes = Maps.newHashMap();

					configAttributesByKeys.put(configKey, configAttributes);
				}

				configAttributes.put(suffix, value);
			}
		}

		final List<EventListenerConfig> configs = Lists.newArrayList();

		for (final String configKey : Sets.newTreeSet(configAttributesByKeys.keySet())) {

			final Map<String, String> configAttributes = configAttributesByKeys.get(configKey);

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
					return configAttributes.get(KEYCLOAK_EVENT_HTTP_LISTENER_URL);
				}

				@Override
				@Nullable
				public String getAccessToken() {
					return configAttributes.get(ACCESS_TOKEN);
				}

				@Override
				@Nullable
				public String getQueueName() {
					return configAttributes.get(KEYCLOAK_EVENT_QUEUE);
				}
			});
		}

		return Iterables.toArray(configs, EventListenerConfig.class);
	}
}
