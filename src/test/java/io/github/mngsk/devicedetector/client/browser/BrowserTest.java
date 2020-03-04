package io.github.mngsk.devicedetector.client.browser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.github.mngsk.devicedetector.Detection;
import io.github.mngsk.devicedetector.DeviceDetector;
import io.github.mngsk.devicedetector.DeviceDetector.DeviceDetectorBuilder;

public class BrowserTest {

	private static List<BrowserFixture> FIXTURES;

	private static DeviceDetector DD;

	@BeforeAll
	public static void beforeAll() {
		ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.addMixIn(Browser.class, BrowserMixin.class);

		String fixtureFile = "fixtures/client/browser.yml";
		InputStream inputStream = BrowserTest.class.getClassLoader()
				.getResourceAsStream(fixtureFile);
		CollectionType listType = objectMapper.getTypeFactory()
				.constructCollectionType(List.class, BrowserFixture.class);
		try {
			FIXTURES = objectMapper.readValue(inputStream, listType);
		} catch (IOException e) {
			throw new RuntimeException("Could not load " + fixtureFile, e);
		}

		DD = new DeviceDetectorBuilder().disableEverything().enableBrowsers()
				.build();
	}

	public static Stream<BrowserFixture> fixtureProvider() {
		return FIXTURES.stream();
	}

	@ParameterizedTest
	@MethodSource("fixtureProvider")
	public void fixtures(BrowserFixture fixture) {
		Detection d = DD.detect(fixture.getUserAgent());
		assertTrue(d.isBrowser());
		assertEquals(fixture.getClient(), d.getClient().get(),
				fixture.getUserAgent());
	}

}
