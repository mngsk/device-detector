package io.github.mngsk.devicedetector.device;

import java.util.Optional;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ModelRegex {

  private String regex;
  private String model;
  private String device;
  private String brand;

  @JsonIgnore private Pattern pattern;

  public String getRegex() {
    return this.regex;
  }

  public void setRegex(String regex) {
    this.regex = regex;
    this.pattern =
        Pattern.compile(
            "(?:^|[^A-Z0-9\\-_]|[^A-Z0-9\\-]_|sprd-)(?:" + regex + ")", Pattern.CASE_INSENSITIVE);
  }

  public String getModel() {
    return this.model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Optional<String> getDevice() {
    return Optional.ofNullable(this.device);
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public Optional<String> getBrand() {
    return Optional.ofNullable(this.brand);
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public Pattern getPattern() {
    return this.pattern;
  }
}
