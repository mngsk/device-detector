package io.github.mngsk.devicedetector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.mngsk.devicedetector.DeviceDetector.DeviceDetectorBuilder;
import io.github.mngsk.devicedetector.client.Client;
import io.github.mngsk.devicedetector.client.browser.Browser;
import io.github.mngsk.devicedetector.device.Device;
import io.github.mngsk.devicedetector.operatingsystem.OperatingSystem;

public class DetectionTest {

  private static DeviceDetector DD;

  @BeforeAll
  public static void beforeAll() {
    DD = new DeviceDetectorBuilder().build();
  }

  @Test
  public void invalidUserAgent() {
    Detection d = DD.detect("12345");

    assertEquals("12345", d.getUserAgent());
    assertFalse(d.getDevice().isPresent());
    assertFalse(d.getOperatingSystem().isPresent());
    assertFalse(d.getClient().isPresent());
  }

  @Test
  public void touchEnabled() {
    String userAgent =
        "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; ARM; Trident/6.0; Touch; ARMBJS)";
    Detection d = DD.detect(userAgent);

    assertTrue(d.isTouchEnabled());
  }

  @Test
  public void magicMethods() {
    String userAgent =
        "Mozilla/5.0 (Linux; Android 4.4.2; Nexus 4 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.136 Mobile Safari/537.36";
    Detection d = DD.detect(userAgent);

    assertTrue(d.isSmartphone());
    assertFalse(d.isFeaturePhone());
    assertFalse(d.isTablet());
    assertFalse(d.isPhablet());
    assertFalse(d.isCarBrowser());
    assertFalse(d.isSmartDisplay());
    assertFalse(d.isSmartSpeaker());
    assertFalse(d.isTV());
    assertFalse(d.isConsole());
    assertFalse(d.isPortableMediaPlayer());
    assertFalse(d.isCamera());

    assertTrue(d.isBrowser());
    assertFalse(d.isLibrary());
    assertFalse(d.isMediaPlayer());
    assertFalse(d.isMobileApp());
    assertFalse(d.isPIM());
    assertFalse(d.isFeedReader());
    assertFalse(d.isBot());
  }

  @Test
  public void device() {
    String userAgent =
        "Mozilla/5.0 (Linux; Android 4.4.2; Nexus 4 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.136 Mobile Safari/537.36";
    Detection d = DD.detect(userAgent);

    assertTrue(d.getDevice().isPresent());

    Device expected = new Device("smartphone", "Google", "Nexus 4");
    assertEquals(expected, d.getDevice().get());
    assertTrue(d.isMobile());
  }

  @Test
  public void operatingSystem() {
    String userAgent = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)";
    Detection d = DD.detect(userAgent);

    assertTrue(d.getOperatingSystem().isPresent());

    OperatingSystem expected = new OperatingSystem("Windows", "Windows", "x64", "7");
    assertEquals(expected, d.getOperatingSystem().get());
    assertTrue(d.isDesktop());
  }

  @Test
  public void client() {
    String userAgent = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)";
    Detection d = DD.detect(userAgent);

    assertTrue(d.getClient().isPresent());

    Client expected = new Browser("Internet Explorer", "9.0", null, null);
    assertEquals(expected, d.getClient().get());
  }
}
