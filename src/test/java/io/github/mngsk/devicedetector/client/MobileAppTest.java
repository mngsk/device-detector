package io.github.mngsk.devicedetector.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.github.mngsk.devicedetector.Detection;
import io.github.mngsk.devicedetector.DeviceDetector;
import io.github.mngsk.devicedetector.DeviceDetector.DeviceDetectorBuilder;

public class MobileAppTest {

  private static List<ClientFixture> FIXTURES;

  private static DeviceDetector DD;

  @BeforeAll
  public static void beforeAll() {
    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    objectMapper.addMixIn(Client.class, ClientMixin.class);

    String fixtureFile = "fixtures/client/mobile_app.yml";
    InputStream inputStream = MobileAppTest.class.getClassLoader().getResourceAsStream(fixtureFile);
    CollectionType listType =
        objectMapper.getTypeFactory().constructCollectionType(List.class, ClientFixture.class);
    try {
      FIXTURES = objectMapper.readValue(inputStream, listType);
    } catch (IOException e) {
      throw new RuntimeException("Could not load " + fixtureFile, e);
    }

    DD = new DeviceDetectorBuilder().disableEverything().enableMobileApps().build();
  }

  public static Stream<ClientFixture> fixtureProvider() {
    return FIXTURES.stream();
  }

  @ParameterizedTest
  @MethodSource("fixtureProvider")
  public void fixtures(ClientFixture fixture) {
    Detection d = DD.detect(fixture.getUserAgent());
    assertTrue(d.isMobileApp());
    assertEquals(fixture.getClient(), d.getClient().get(), fixture.getUserAgent());
  }
}
