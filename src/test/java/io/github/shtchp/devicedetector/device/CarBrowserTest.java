package io.github.shtchp.devicedetector.device;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import io.github.shtchp.devicedetector.Detection;
import io.github.shtchp.devicedetector.DeviceDetector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class CarBrowserTest {

  private static List<DeviceFixture> FIXTURES;

  private static DeviceDetector DD;

  @BeforeAll
  public static void beforeAll() {
    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    objectMapper.addMixIn(Device.class, DeviceMixin.class);

    String fixtureFile = "fixtures/device/car_browser.yml";
    InputStream inputStream =
        CarBrowserTest.class.getClassLoader().getResourceAsStream(fixtureFile);
    CollectionType listType =
        objectMapper.getTypeFactory().constructCollectionType(List.class, DeviceFixture.class);
    try {
      FIXTURES = objectMapper.readValue(inputStream, listType);
    } catch (IOException e) {
      throw new RuntimeException("Could not load " + fixtureFile, e);
    }

    DD = new DeviceDetector.DeviceDetectorBuilder().disableEverything().enableCars().build();
  }

  public static Stream<DeviceFixture> fixtureProvider() {
    return FIXTURES.stream();
  }

  @ParameterizedTest
  @MethodSource("fixtureProvider")
  public void fixtures(DeviceFixture fixture) {
    Detection d = DD.detect(fixture.getUserAgent());
    assertTrue(d.isCarBrowser());
    Assertions.assertEquals(fixture.getDevice(), d.getDevice().get(), fixture.getUserAgent());
  }
}
