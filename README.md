# Device Detector

[![build](https://img.shields.io/github/workflow/status/mngsk/device-detector/build)](https://github.com/mngsk/device-detector/actions?query=workflow%3Abuild)
[![release](https://img.shields.io/github/v/release/mngsk/device-detector)](https://github.com/mngsk/device-detector/releases)
[![maven central](https://img.shields.io/maven-central/v/io.github.mngsk/device-detector)](https://search.maven.org/classic/#search|gav|1|g:"io.github.mngsk"%20AND%20a:"device-detector")
[![license](https://img.shields.io/github/license/mngsk/device-detector)](https://www.gnu.org/licenses/lgpl-3.0.html)

The Universal Device Detection library that parses User Agents and detects devices (desktop, tablet, mobile, tv, cars, console, etc.), clients (browsers, feed readers, media players, PIMs, ...), operating systems, brands and models.

This project is a Java adaptation of [Matomo Device Detector](https://github.com/matomo-org/device-detector).

## Usage

Using DeviceDetector with Maven is quite easy. Just add the following dependency in your project's pom.xml:

```xml
<dependency>
	<groupId>io.github.mngsk</groupId>
	<artifactId>device-detector</artifactId>
	<version>1.0.9</version>
</dependency>
```

And use some code like this one:

```java
import io.github.mngsk.devicedetector.DeviceDetector;
import io.github.mngsk.devicedetector.DeviceDetector.DeviceDetectorBuilder;

DeviceDetector dd = new DeviceDetectorBuilder().build();

// Assuming `request` is an instance of HttServletRequest
String userAgent = request.getHeader("User-Agent");

Detection detection = dd.detect(userAgent);
System.out.println(detection.getDevice().map(d -> d.toString()).orElse("unknown"));
System.out.println(detection.getOperatingSystem().map(d -> d.toString()).orElse("unknown"));
System.out.println(detection.getClient().map(d -> d.toString()).orElse("unknown"));

if (detection.getDevice().isPresent()) {
	System.out.println(detection.getDevice().get().getType()); // bot, browser, feed reader...
	System.out.println(detection.getDevice().get().getBrand().orElse("unknown"));
	System.out.println(detection.getDevice().get().getModel().orElse("unknown"));
}
```

Instead of using the full power of DeviceDetector it might in some cases be better to use only specific parsers.
If you aim to check if a given user agent is a bot and don't require any of the other information, you can enable only the bot parser.

```java
import io.github.mngsk.devicedetector.DeviceDetector;
import io.github.mngsk.devicedetector.DeviceDetector.DeviceDetectorBuilder;

DeviceDetector dd = new DeviceDetectorBuilder().disableEverything().enableBots().build();

Detection detection = dd.detect(userAgent);

if (detection.isBot()) {
	System.out.println(detection.getClient().get().getName().get());
}
```

### Caching

Contrary to the original PHP implementation, this project does not include any caching mechanism. Consider using an specialized tool for that purpose.

## Contributing

Please note that we use the fixture files directly from the original PHP project; if you want to contribute or fix a regex, please consider making a pull request on [that project](https://github.com/matomo-org/device-detector/pull/new/master), so that all derivative projects -including this one- can benefit from it.

On the other hand, contributions of java code are very welcome.

## Tests

This project uses the YAML fixture files of the original PHP version. To run the tests, execute `mvn test` from the project's root directory.
