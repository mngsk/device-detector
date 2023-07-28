package io.github.shtchp.devicedetector.generic;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.github.shtchp.devicedetector.client.Client;
import io.github.shtchp.devicedetector.client.browser.Browser;
import io.github.shtchp.devicedetector.device.Device;
import io.github.shtchp.devicedetector.operatingsystem.OperatingSystem;

@JsonDeserialize(builder = GenericFixture.Builder.class)
public class GenericFixture {

  private final String userAgent;
  private final OperatingSystem operatingSystem;
  private final Client client;
  private final Device device;

  public GenericFixture(
      String userAgent, OperatingSystem operatingSystem, Client client, Device device) {
    this.userAgent = userAgent;
    this.operatingSystem = operatingSystem;
    this.client = client;
    this.device = device;
  }

  public String getUserAgent() {
    return this.userAgent;
  }

  public OperatingSystem getOperatingSystem() {
    return this.operatingSystem;
  }

  public Client getClient() {
    return this.client;
  }

  public Device getDevice() {
    return this.device;
  }

  public static class Builder {

    private String userAgent;
    private List<OperatingSystem> operatingSystems;
    private Client client;
    private Device device;
    private String osFamily;
    private String browserFamily;

    @JsonProperty("user_agent")
    public Builder withUserAgent(String userAgent) {
      this.userAgent = userAgent;
      return this;
    }

    @JsonProperty("os")
    public Builder withOperatingSystems(List<OperatingSystem> operatingSystems) {
      this.operatingSystems = operatingSystems;
      return this;
    }

    public Builder withClient(Client client) {
      this.client = client;
      return this;
    }

    public Builder withDevice(Device device) {
      this.device = device;
      return this;
    }

    @JsonProperty("os_family")
    public Builder withOsFamily(String osFamily) {
      this.osFamily = osFamily;
      return this;
    }

    @JsonProperty("browser_family")
    public Builder withBrowserFamily(String browserFamily) {
      this.browserFamily = browserFamily;
      return this;
    }

    public GenericFixture build() {
      OperatingSystem operatingSystem = null;
      if (this.operatingSystems != null && !this.operatingSystems.isEmpty()) {
        OperatingSystem os = this.operatingSystems.get(0);
        operatingSystem =
            new OperatingSystem(
                os.getName(),
                this.osFamily.equalsIgnoreCase("unknown") ? null : this.osFamily,
                os.getPlatform().orElse(null),
                os.getVersion().orElse(null));
      }

      Client client;
      if (this.client != null && this.client.getType().equals("browser")) {
        Browser browser = (Browser) this.client;
        client =
            new Browser(
                browser.getName().orElse(null),
                browser.getVersion().orElse(null),
                browser.getEngine().orElse(null),
                this.browserFamily.equalsIgnoreCase("unknown") ? null : this.browserFamily);
      } else {
        client = this.client;
      }

      return new GenericFixture(this.userAgent, operatingSystem, client, this.device);
    }
  }
}
