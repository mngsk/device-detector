package io.github.shtchp.devicedetector.client;

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

public class LibraryTest {

  private static List<ClientFixture> FIXTURES;

  private static DeviceDetector DD;

  @BeforeAll
  public static void beforeAll() {
    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    objectMapper.addMixIn(Client.class, ClientMixin.class);

    String fixtureFile = "fixtures/client/library.yml";
    InputStream inputStream = LibraryTest.class.getClassLoader().getResourceAsStream(fixtureFile);
    CollectionType listType =
        objectMapper.getTypeFactory().constructCollectionType(List.class, ClientFixture.class);
    try {
      FIXTURES = objectMapper.readValue(inputStream, listType);
    } catch (IOException e) {
      throw new RuntimeException("Could not load " + fixtureFile, e);
    }

    DD = new DeviceDetector.DeviceDetectorBuilder().disableEverything().enableLibraries().build();
  }

  public static Stream<ClientFixture> fixtureProvider() {
    return FIXTURES.stream();
  }

  @ParameterizedTest
  @MethodSource("fixtureProvider")
  public void fixtures(ClientFixture fixture) {
    Detection d = DD.detect(fixture.getUserAgent());
    assertTrue(d.isLibrary());
    Assertions.assertEquals(fixture.getClient(), d.getClient().get(), fixture.getUserAgent());
  }
}
