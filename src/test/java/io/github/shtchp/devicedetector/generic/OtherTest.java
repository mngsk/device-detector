package io.github.shtchp.devicedetector.generic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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

public class OtherTest extends AbstractTest {

  @BeforeAll
  public static void beforeAll() {
    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    objectMapper.addMixIn(OperatingSystem.class, OperatingSystemMixin.class);
    objectMapper.addMixIn(Client.class, ClientMixin.class);
    objectMapper.addMixIn(Device.class, DeviceMixin.class);

    FIXTURES = new ArrayList<>();
    List<String> fixtureFiles =
        Arrays.asList(
            "fixtures/camera.yml",
            "fixtures/car_browser.yml",
            "fixtures/console.yml",
            "fixtures/desktop.yml",
            "fixtures/feature_phone.yml",
            "fixtures/feed_reader.yml",
            "fixtures/mediaplayer.yml",
            "fixtures/mobile_apps.yml",
            "fixtures/peripheral.yml",
            "fixtures/phablet.yml",
            "fixtures/portable_media_player.yml",
            "fixtures/smart_display.yml",
            "fixtures/smart_speaker.yml",
            "fixtures/tv.yml",
            "fixtures/tv-1.yml",
            "fixtures/wearable.yml");
    for (String fixtureFile : fixtureFiles) {
      InputStream inputStream = OtherTest.class.getClassLoader().getResourceAsStream(fixtureFile);
      CollectionType listType =
          objectMapper.getTypeFactory().constructCollectionType(List.class, GenericFixture.class);
      try {
        List<GenericFixture> genericFixtures = objectMapper.readValue(inputStream, listType);
        FIXTURES.addAll(genericFixtures);
      } catch (IOException e) {
        throw new RuntimeException("Could not load " + fixtureFile, e);
      }
    }

    DD = new DeviceDetector.DeviceDetectorBuilder().build();
  }
}
