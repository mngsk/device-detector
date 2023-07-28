package io.github.shtchp.devicedetector.generic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import io.github.shtchp.devicedetector.Detection;
import io.github.shtchp.devicedetector.DeviceDetector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractTest {

  protected static List<GenericFixture> FIXTURES;

  protected static DeviceDetector DD;

  protected ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

  public static Stream<GenericFixture> fixtureProvider() {
    return FIXTURES.stream();
  }

  @ParameterizedTest
  @MethodSource("fixtureProvider")
  protected void fixtures(GenericFixture fixture) {
    Detection d = DD.detect(fixture.getUserAgent());
    Assertions.assertEquals(
        fixture.getOperatingSystem(),
        d.getOperatingSystem().orElse(null),
        () ->
            debug(
                fixture.getUserAgent(),
                fixture.getOperatingSystem(),
                d.getOperatingSystem().orElse(null)));
    Assertions.assertEquals(
        fixture.getClient(),
        d.getClient().orElse(null),
        () -> debug(fixture.getUserAgent(), fixture.getClient(), d.getClient().orElse(null)));
    Assertions.assertEquals(
        fixture.getDevice(),
        d.getDevice().orElse(null),
        () -> debug(fixture.getUserAgent(), fixture.getDevice(), d.getDevice().orElse(null)));
  }

  public String debug(String userAgent, Object expected, Object actual) {
    StringBuilder sb = new StringBuilder(userAgent);
    try {
      sb.append("\nexpected: ").append(this.objectMapper.writeValueAsString(expected));
      sb.append("\n  actual: ").append(this.objectMapper.writeValueAsString(actual));
    } catch (Exception e) {
    }
    return sb.toString();
  }
}
