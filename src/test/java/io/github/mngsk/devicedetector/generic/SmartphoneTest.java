package io.github.mngsk.devicedetector.generic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.github.mngsk.devicedetector.DeviceDetector.DeviceDetectorBuilder;
import io.github.mngsk.devicedetector.client.Client;
import io.github.mngsk.devicedetector.client.ClientMixin;
import io.github.mngsk.devicedetector.device.Device;
import io.github.mngsk.devicedetector.device.DeviceMixin;
import io.github.mngsk.devicedetector.operatingsystem.OperatingSystem;
import io.github.mngsk.devicedetector.operatingsystem.OperatingSystemMixin;

public class SmartphoneTest extends AbstractTest {

	@BeforeAll
	public static void beforeAll() {
		ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper
				.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		objectMapper.addMixIn(OperatingSystem.class,
				OperatingSystemMixin.class);
		objectMapper.addMixIn(Client.class, ClientMixin.class);
		objectMapper.addMixIn(Device.class, DeviceMixin.class);

		FIXTURES = new ArrayList<>();
		List<String> fixtureFiles = Arrays.asList("fixtures/smartphone.yml",
				"fixtures/smartphone-1.yml", "fixtures/smartphone-2.yml",
				"fixtures/smartphone-3.yml", "fixtures/smartphone-4.yml",
				"fixtures/smartphone-5.yml", "fixtures/smartphone-6.yml",
				"fixtures/smartphone-7.yml", "fixtures/smartphone-8.yml",
				"fixtures/smartphone-9.yml", "fixtures/smartphone-10.yml",
				"fixtures/smartphone-11.yml", "fixtures/smartphone-12.yml",
				"fixtures/smartphone-13.yml", "fixtures/smartphone-14.yml",
				"fixtures/smartphone-15.yml", "fixtures/smartphone-16.yml");
		for (String fixtureFile : fixtureFiles) {
			InputStream inputStream = SmartphoneTest.class.getClassLoader()
					.getResourceAsStream(fixtureFile);
			CollectionType listType = objectMapper.getTypeFactory()
					.constructCollectionType(List.class, GenericFixture.class);
			try {
				List<GenericFixture> genericFixtures = objectMapper
						.readValue(inputStream, listType);
				FIXTURES.addAll(genericFixtures);
			} catch (IOException e) {
				throw new RuntimeException("Could not load " + fixtureFile, e);
			}
		}

		DD = new DeviceDetectorBuilder().build();
	}

}
