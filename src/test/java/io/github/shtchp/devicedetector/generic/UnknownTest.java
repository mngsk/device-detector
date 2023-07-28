package io.github.shtchp.devicedetector.generic;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.github.shtchp.devicedetector.DeviceDetector;
import io.github.shtchp.devicedetector.client.Client;
import io.github.shtchp.devicedetector.client.ClientMixin;
import io.github.shtchp.devicedetector.device.Device;
import io.github.shtchp.devicedetector.operatingsystem.OperatingSystem;
import io.github.shtchp.devicedetector.operatingsystem.OperatingSystemMixin;
import org.junit.jupiter.api.BeforeAll;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.github.shtchp.devicedetector.device.DeviceMixin;

public class UnknownTest extends AbstractTest {

  @BeforeAll
  public static void beforeAll() {
    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    objectMapper.addMixIn(OperatingSystem.class, OperatingSystemMixin.class);
    objectMapper.addMixIn(Client.class, ClientMixin.class);
    objectMapper.addMixIn(Device.class, DeviceMixin.class);

    String fixtureFile = "fixtures/unknown.yml";
    InputStream inputStream = UnknownTest.class.getClassLoader().getResourceAsStream(fixtureFile);
    CollectionType listType =
        objectMapper.getTypeFactory().constructCollectionType(List.class, GenericFixture.class);
    try {
      FIXTURES = objectMapper.readValue(inputStream, listType);
    } catch (IOException e) {
      throw new RuntimeException("Could not load " + fixtureFile, e);
    }

    DD = new DeviceDetector.DeviceDetectorBuilder().build();
  }
}
