package io.github.mngsk.devicedetector.generic;

import java.io.IOException;
import java.io.InputStream;
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

public class UnknownTest extends AbstractTest {

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

		String fixtureFile = "fixtures/unknown.yml";
		InputStream inputStream = UnknownTest.class.getClassLoader()
				.getResourceAsStream(fixtureFile);
		CollectionType listType = objectMapper.getTypeFactory()
				.constructCollectionType(List.class, GenericFixture.class);
		try {
			FIXTURES = objectMapper.readValue(inputStream, listType);
		} catch (IOException e) {
			throw new RuntimeException("Could not load " + fixtureFile, e);
		}

		DD = new DeviceDetectorBuilder().build();
	}

}
