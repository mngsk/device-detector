package io.github.shtchp.devicedetector.operatingsystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Matcher;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.github.shtchp.devicedetector.util.AbstractParser;

public class OperatingSystemParser extends AbstractParser<OperatingSystem> {

  private List<OperatingSystemRegex> operatingSystems;
  private Map<String, List<String>> families;
  private List<PlatformRegex> platforms;

  public OperatingSystemParser() {
    this(new ObjectMapper(new YAMLFactory()));
  }

  public OperatingSystemParser(ObjectMapper objectMapper) {
    String fixtureFile;
    InputStream inputStream;
    CollectionType listType;

    fixtureFile = "regexes/oss.yml";
    inputStream = getClass().getClassLoader().getResourceAsStream(fixtureFile);
    listType =
        objectMapper
            .getTypeFactory()
            .constructCollectionType(List.class, OperatingSystemRegex.class);
    try {
      this.operatingSystems = objectMapper.readValue(inputStream, listType);
    } catch (IOException e) {
      throw new RuntimeException("Could not load " + fixtureFile, e);
    }

    fixtureFile = "regexes/ossfamilies.yml";
    inputStream = getClass().getClassLoader().getResourceAsStream(fixtureFile);
    TypeReference<Map<String, List<String>>> mapType =
        new TypeReference<Map<String, List<String>>>() {};
    try {
      this.families = objectMapper.readValue(inputStream, mapType);
    } catch (IOException e) {
      throw new RuntimeException("Could not load " + fixtureFile, e);
    }

    fixtureFile = "regexes/ossplatforms.yml";
    inputStream = getClass().getClassLoader().getResourceAsStream(fixtureFile);
    listType =
        objectMapper.getTypeFactory().constructCollectionType(List.class, PlatformRegex.class);
    try {
      this.platforms = objectMapper.readValue(inputStream, listType);
    } catch (IOException e) {
      throw new RuntimeException("Could not load " + fixtureFile, e);
    }
  }

  @Override
  public Optional<OperatingSystem> parse(String userAgent) {
    String name = null;
    String version = null;
    for (OperatingSystemRegex os : this.operatingSystems) {
      Matcher matcher = os.getPattern().matcher(userAgent);
      if (matcher.find()) {
        name = super.buildByMatch(os.getName(), matcher);

        version = super.buildVersion(os.getVersion().orElse(null), matcher);

        break;
      }
    }
    if (name == null) {
      return Optional.empty();
    }

    if (name.equals("lubuntu")) {
      name = "Lubuntu";
    } else if (name.equals("debian")) {
      name = "Debian";
    } else if (name.equals("YunOS")) {
      name = "YunOs";
    }

    String family = null;
    for (Entry<String, List<String>> entry : this.families.entrySet()) {
      if (entry.getValue().contains(name)) {
        family = entry.getKey();
        break;
      }
    }

    String platform = null;
    for (PlatformRegex p : this.platforms) {
      Matcher matcher = p.getPattern().matcher(userAgent);
      if (matcher.find()) {
        platform = p.getName();
        break;
      }
    }

    return Optional.of(new OperatingSystem(name, family, platform, version));
  }
}
