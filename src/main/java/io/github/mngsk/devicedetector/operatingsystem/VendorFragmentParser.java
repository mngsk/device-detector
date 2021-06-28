package io.github.mngsk.devicedetector.operatingsystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.github.mngsk.devicedetector.util.AbstractParser;

public class VendorFragmentParser extends AbstractParser<String> {

  private Map<String, String[]> brands;

  public VendorFragmentParser() {
    this(new ObjectMapper(new YAMLFactory()));
  }

  public VendorFragmentParser(ObjectMapper objectMapper) {
    String fixtureFile = "regexes/vendorfragments.yml";
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fixtureFile);
    MapType mapType =
        objectMapper
            .getTypeFactory()
            .constructMapType(LinkedHashMap.class, String.class, String[].class);
    try {
      this.brands = objectMapper.readValue(inputStream, mapType);
    } catch (IOException e) {
      throw new RuntimeException("Could not load " + fixtureFile, e);
    }
  }

  @Override
  public Optional<String> parse(String userAgent) {
    for (Entry<String, String[]> brand : this.brands.entrySet()) {
      for (String regex : brand.getValue()) {
        Matcher matcher =
            Pattern.compile(regex + "[^a-z0-9]+", Pattern.CASE_INSENSITIVE).matcher(userAgent);
        if (matcher.find()) {
          return Optional.of(brand.getKey());
        }
      }
    }

    return Optional.empty();
  }
}
