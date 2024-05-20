package com.kalisio.keycloak;

import static com.google.common.base.Charsets.UTF_8;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.io.FileUtils;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

/**
 * Tests against the JSON examples given in the <code>Examples.md</code> file.
 */
public class ExamplesMdTest {

	@ParameterizedTest(name = "{0}")
	@MethodSource("jsonExamples")
	public void testJsonCanBeDeserialized(
		final String exampleTitle,
		final String json
	) throws Exception {

		new JSONParser().parse(json); // Check with json-simple

		final JsonNode jsonNode = new ObjectMapper().readTree(json); // Check with Jackson

		@Nullable
		final JsonNode valueNode = jsonNode.get("value");

		@Nullable
		final JsonNode representationNode = jsonNode.get("representation");

		if (valueNode == null) {
			assertNull(representationNode, "representationNode");
		}

		if (representationNode == null) {
			assertNull(valueNode, "valueNode");
		}
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("jsonExamples")
	public void testRepresentationStringCanBeDeserializedToValue(
		final String exampleTitle,
		final String json
	) throws Exception {

		final JsonNode jsonNode = new ObjectMapper().readTree(json); // Check with Jackson

		@Nullable
		final JsonNode representationNode = jsonNode.get("representation");

		assumeTrue(representationNode != null && !representationNode.isNull());

		@Nullable
		final JsonNode valueNode = jsonNode.get("value");

		final String representation = representationNode.asText();

		final JsonNode deserializedRepresentationNode = new ObjectMapper().readTree(representation);

		assertJsonNodeEquals(valueNode, deserializedRepresentationNode);
	}

	private static void assertJsonNodeEquals(
		final JsonNode ref,
		final JsonNode test
	) {

		assertSame(ref.getNodeType(), test.getNodeType());

		switch (ref.getNodeType()) {

		case BOOLEAN:
			assertEquals(ref.asBoolean(), test.asBoolean());
			break;

		case NUMBER:
			assertEquals(ref.asLong(), test.asLong());
			break;

		case STRING:
			assertEquals(ref.asText(), test.asText());
			break;

		default:
			break;
		}

		assertEquals(ref.size(), test.size());

		for (final Iterator<String> it = ref.fieldNames(); it.hasNext();) {

			final String fieldName = it.next();

			final JsonNode refItem = ref.get(fieldName);

			final JsonNode testItem = test.get(fieldName);

			assertJsonNodeEquals(refItem, testItem);
		}
	}

	private static Stream<Arguments> jsonExamples() throws IOException {

		final List<Arguments> arguments = Lists.newArrayList();

		@Nullable
		String currentH3Title = null;

		boolean inCodeBlock = false;

		final StringBuilder sb = new StringBuilder();

		for (final String line : FileUtils.readLines(new File("docs", "Examples.md"), UTF_8)) {

			if (line.startsWith("### ") && !inCodeBlock) {

				currentH3Title = substringAfter(line, "### ").replace("`", "").trim();

			} else if (line.startsWith("```")) {

				inCodeBlock = !inCodeBlock;

				if (!inCodeBlock) {

					final String json = sb.toString();

					arguments.add(Arguments.of(currentH3Title, json));

					sb.setLength(0);
				}

			} else if (inCodeBlock) {

				sb.append(line).append("\n");
			}
		}

		return arguments.stream();
	}
}
