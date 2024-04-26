package com.kalisio.keycloak;

import static com.kalisio.keycloak.EventListenerConfigUtils.extractEventListenerConfigs;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class EventListenerConfigUtilsTest {

	@Test
	public void testEventListenerConfigUtils_empty() throws Exception {

		final EventListenerConfig[] configs = extractEventListenerConfigs(map());

		assertEquals(0, configs.length);
	}

	@Test
	public void testEventListenerConfigUtils_one_partial() throws Exception {

		final EventListenerConfig[] configs = extractEventListenerConfigs(map( //
				"keycloakEventHttpListenerUrl", "http://localhost:8080/api/keycloak-events"));

		assertEquals(1, configs.length);
		assertEquals(null, configs[0].getPrefix());
		assertEquals("http://localhost:8080/api/keycloak-events", configs[0].getHttpListenerUrl());
		assertEquals(null, configs[0].getAccessToken());
		assertEquals(null, configs[0].getQueueName());
	}

	@Test
	public void testEventListenerConfigUtils_one_full() throws Exception {

		final EventListenerConfig[] configs = extractEventListenerConfigs(map( //
				"keycloakEventHttpListenerUrl", "http://localhost:8080/api/keycloak-events", //
				"accessToken", "abcxyz", //
				"keycloakEventQueue", "monqueue"));

		assertEquals(1, configs.length);
		assertEquals(null, configs[0].getPrefix());
		assertEquals("http://localhost:8080/api/keycloak-events", configs[0].getHttpListenerUrl());
		assertEquals("abcxyz", configs[0].getAccessToken());
		assertEquals("monqueue", configs[0].getQueueName());
	}

	@Test
	public void testEventListenerConfigUtils_two() throws Exception {

		final EventListenerConfig[] configs = extractEventListenerConfigs(map( //
				"a.keycloakEventHttpListenerUrl", "http://localhost:8080/api/keycloak-events", //
				"a.accessToken", "abcxyz", //
				"b.keycloakEventQueue", "monqueue"));

		assertEquals(2, configs.length);

		assertEquals("a", configs[0].getPrefix());
		assertEquals("http://localhost:8080/api/keycloak-events", configs[0].getHttpListenerUrl());
		assertEquals("abcxyz", configs[0].getAccessToken());
		assertEquals(null, configs[0].getQueueName());

		assertEquals("b", configs[1].getPrefix());
		assertEquals(null, configs[1].getHttpListenerUrl());
		assertEquals(null, configs[1].getAccessToken());
		assertEquals("monqueue", configs[1].getQueueName());
	}

	@Test
	public void testEventListenerConfigUtils_one_accessToken_splitInFour() throws Exception {

		final EventListenerConfig[] configs = extractEventListenerConfigs(map( //
				"keycloakEventHttpListenerUrl", "http://localhost:8080/api/keycloak-events", //
				"accessToken.0", "eyJhbGciOiJIUzI1NiIsInR5cCI6ImFjY2VzcyJ9.eyJpYXQiOjE3MTQxMzczNTcsImV4cCI6MTcx", //
				"accessToken.1", "NDIyMzc1NywiYXVkIjoia2FsaXNpbyIsImlzcyI6ImthbGlzaW8iLCJzdWIiOiI2NjJiYTYxNTRkM", //
				"accessToken.2", "mYxZjBhN2ZmZDhiMTUiLCJqdGkiOiI1MzAwNDk3Mi01YWU5LTRkMTEtOTMxOS0zOTA0YTIxYTVmNW", //
				"accessToken.3", "QifQ.dJxU1RhGs5wTwTt6DdJ3g_xIKmCYSohfmJVNilcTUBM", //
				"keycloakEventQueue", "monqueue"));

		assertEquals(1, configs.length);
		assertEquals(null, configs[0].getPrefix());
		assertEquals("http://localhost:8080/api/keycloak-events", configs[0].getHttpListenerUrl());
		assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6ImFjY2VzcyJ9.eyJpYXQiOjE3MTQxMzczNTcsImV4cCI6MTcx"
				+ "NDIyMzc1NywiYXVkIjoia2FsaXNpbyIsImlzcyI6ImthbGlzaW8iLCJzdWIiOiI2NjJiYTYxNTRkM"
				+ "mYxZjBhN2ZmZDhiMTUiLCJqdGkiOiI1MzAwNDk3Mi01YWU5LTRkMTEtOTMxOS0zOTA0YTIxYTVmNW"
				+ "QifQ.dJxU1RhGs5wTwTt6DdJ3g_xIKmCYSohfmJVNilcTUBM", configs[0].getAccessToken());
		assertEquals("monqueue", configs[0].getQueueName());
	}

	@Test
	public void testEventListenerConfigUtils_oneWithPrefix_accessToken_splitInFour() throws Exception {

		final EventListenerConfig[] configs = extractEventListenerConfigs(map( //
				"a1.keycloakEventHttpListenerUrl", "http://localhost:8080/api/keycloak-events", //
				"a1.accessToken.0", "eyJhbGciOiJIUzI1NiIsInR5cCI6ImFjY2VzcyJ9.eyJpYXQiOjE3MTQxMzczNTcsImV4cCI6MTcx", //
				"a1.accessToken.1", "NDIyMzc1NywiYXVkIjoia2FsaXNpbyIsImlzcyI6ImthbGlzaW8iLCJzdWIiOiI2NjJiYTYxNTRkM", //
				"a1.accessToken.2", "mYxZjBhN2ZmZDhiMTUiLCJqdGkiOiI1MzAwNDk3Mi01YWU5LTRkMTEtOTMxOS0zOTA0YTIxYTVmNW", //
				"a1.accessToken.3", "QifQ.dJxU1RhGs5wTwTt6DdJ3g_xIKmCYSohfmJVNilcTUBM", //
				"a1.keycloakEventQueue", "monqueue"));

		assertEquals(1, configs.length);
		assertEquals("a1", configs[0].getPrefix());
		assertEquals("http://localhost:8080/api/keycloak-events", configs[0].getHttpListenerUrl());
		assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6ImFjY2VzcyJ9.eyJpYXQiOjE3MTQxMzczNTcsImV4cCI6MTcx"
				+ "NDIyMzc1NywiYXVkIjoia2FsaXNpbyIsImlzcyI6ImthbGlzaW8iLCJzdWIiOiI2NjJiYTYxNTRkM"
				+ "mYxZjBhN2ZmZDhiMTUiLCJqdGkiOiI1MzAwNDk3Mi01YWU5LTRkMTEtOTMxOS0zOTA0YTIxYTVmNW"
				+ "QifQ.dJxU1RhGs5wTwTt6DdJ3g_xIKmCYSohfmJVNilcTUBM", configs[0].getAccessToken());
		assertEquals("monqueue", configs[0].getQueueName());
	}

	private static Map<String, List<String>> map(
		final String... s
	) {

		final Map<String, List<String>> map = Maps.newHashMap();

		for (int i = 0; i < s.length; i += 2) {

			final String name = s[i];
			final String value = s[i + 1];

			map.put(name, Lists.newArrayList(value));
		}

		return map;
	}
}
