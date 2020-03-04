package io.github.mngsk.devicedetector;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.maven.artifact.versioning.ComparableVersion;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.mngsk.devicedetector.client.Client;
import io.github.mngsk.devicedetector.client.browser.Browser;
import io.github.mngsk.devicedetector.device.Device;
import io.github.mngsk.devicedetector.operatingsystem.OperatingSystem;
import io.github.mngsk.devicedetector.operatingsystem.VendorFragmentParser;

public class Detection {

	private static VendorFragmentParser vendorFragmentParser = new VendorFragmentParser();
	private static Pattern touchEnabledPattern = Pattern
			.compile("(?:^|[^A-Z_-])(?:Touch)");
	private static Pattern androidTabletPattern = Pattern.compile(
			"(?:^|[^A-Z_-])(?:Android( [\\.0-9]+)?; Tablet;)",
			Pattern.CASE_INSENSITIVE);
	private static Pattern androidMobilePattern = Pattern.compile(
			"(?:^|[^A-Z_-])(?:Android( [\\.0-9]+)?; Mobile;)",
			Pattern.CASE_INSENSITIVE);
	private static Pattern chromeSmartphonePattern = Pattern.compile(
			"(?:^|[^A-Z_-])(?:Chrome/[\\.0-9]* Mobile)",
			Pattern.CASE_INSENSITIVE);
	private static Pattern chromeTabletPattern = Pattern.compile(
			"(?:^|[^A-Z_-])(?:Chrome/[\\.0-9]* (?!Mobile))",
			Pattern.CASE_INSENSITIVE);
	private static Pattern operaTabletPattern = Pattern.compile(
			"(?:^|[^A-Z_-])(?:Opera Tablet)", Pattern.CASE_INSENSITIVE);
	private static Pattern operaTvPattern = Pattern.compile(
			"(?:^|[^A-Z_-])(?:Opera TV Store)", Pattern.CASE_INSENSITIVE);
	private static List<String> mobileDeviceTypes = Arrays.asList(
			"feature phone", "smartphone", "tablet", "phablet", "camera",
			"portable media player");
	private static List<String> nonMobileDeviceTypes = Arrays.asList("tv",
			"smart display", "console");
	private static List<String> mobileOnlyBrowsers = Arrays.asList(
			"360 Phone Browser", "Oculus Browser", "Puffin", "Skyfire",
			"Mobile Safari", "Opera Mini", "Opera Mobile",
			"DuckDuckGo Privacy Browser", "dbrowser", "Streamy", "B-Line",
			"Isivioo", "Firefox Mobile", "Coast", "Aloha Browser",
			"Sailfish Browser", "Samsung Browser", "Firefox Rocket",
			"Web Explorer", "Hawk Turbo Browser", "Nox Browser",
			"Huawei Browser", "vivo Browser", "Realme Browser", "COS Browser",
			"Meizu Browser", "UC Browser Mini", "Firefox Focus", "Faux Browser",
			"Wear Internet Browser", "Minimo", "mCent", "Aloha Browser Lite",
			"Super Fast Browser", "EUI Browser", "eZ Browser",
			"UC Browser Turbo", "Delta Browser", "START Internet Browser");
	private static List<String> desktopOperatingSystems = Arrays.asList(
			"AmigaOS", "IBM", "GNU/Linux", "Mac", "Unix", "Windows", "BeOS",
			"Chrome OS");
	private static List<String> appleOperatingSystems = Arrays
			.asList("Apple TV", "iOS", "Mac");
	private static List<String> tvBrowsers = Arrays.asList("Kylo",
			"Espial TV Browser");
	private static ComparableVersion v2 = new ComparableVersion("2.0");
	private static ComparableVersion v3 = new ComparableVersion("3.0");
	private static ComparableVersion v4 = new ComparableVersion("4.0");
	private static ComparableVersion v8 = new ComparableVersion("8");

	private final String userAgent;

	private final Device device;
	private final OperatingSystem operatingSystem;
	private final Client client;

	public Detection(String userAgent, Device device,
			OperatingSystem operatingSystem, Client client) {
		this.userAgent = userAgent;

		this.operatingSystem = operatingSystem;
		this.client = client;
		this.device = fillDevice(device);
	}

	private Device fillDevice(Device device) {
		String type = null;
		String brand = null;
		String model = null;
		if (device != null) {
			type = device.getType();
			brand = device.getBrand().orElse(null);
			model = device.getModel().orElse(null);
		}

		if (brand == null) {
			brand = vendorFragmentParser.parse(this.userAgent).orElse(null);
		}

		// Assume all devices running iOS / Mac OS are from Apple
		if (brand == null) {
			if (this.operatingSystem != null && appleOperatingSystems
					.contains(this.operatingSystem.getName())) {
				brand = "Apple";
			}
		}

		// Chrome on Android passes the device type based on the keyword
		// 'Mobile'. If it is present the device should be a smartphone,
		// otherwise it's a tablet. See
		// https://developer.chrome.com/multidevice/user-agent#chrome_for_android_user_agent
		if (type == null && this.operatingSystem != null
				&& this.operatingSystem.getFamily().orElse("").equals("Android")
				&& this.client != null
				&& this.client.getType().equals("browser")
				&& ((Browser) this.client).getFamily().isPresent()
				&& ((Browser) this.client).getFamily().get().equals("Chrome")) {
			if (chromeSmartphonePattern.matcher(userAgent).find()) {
				type = "smartphone";
			} else if (chromeTabletPattern.matcher(userAgent).find()) {
				type = "tablet";
			}
		}

		// Some user agents simply contain the fragment 'Android; Tablet;' or
		// 'Opera Tablet', so we assume those devices as tablets
		if (type == null && (androidTabletPattern.matcher(userAgent).find()
				|| operaTabletPattern.matcher(userAgent).find())) {
			type = "tablet";
		}

		// Some user agents simply contain the fragment 'Android; Mobile;', so
		// we assume those devices as smartphones
		if (type == null && androidMobilePattern.matcher(userAgent).find()) {
			type = "smartphone";
		}

		Optional<ComparableVersion> osVersion = Optional.empty();
		if (this.operatingSystem != null
				&& this.operatingSystem.getVersion().isPresent()) {
			osVersion = Optional.of(new ComparableVersion(
					this.operatingSystem.getVersion().get()));
		}

		// Android up to 3.0 was designed for smartphones only. But as 3.0,
		// which was tablet only, was published too late, there were a bunch of
		// tablets running with 2.x.
		// With 4.0 the two trees were merged and it is for smartphones and
		// tablets.
		// So were are expecting that all devices running Android < 2 are
		// smartphones. Devices running Android 3.X are tablets. Device type of
		// Android 2.X and 4.X+ are unknown
		if (type == null && this.operatingSystem != null
				&& this.operatingSystem.getFamily().orElse("").equals("Android")
				&& osVersion.isPresent()) {
			if (osVersion.get().compareTo(v2) < 0) {
				type = "smartphone";
			} else if (osVersion.get().compareTo(v3) >= 0
					&& osVersion.get().compareTo(v4) < 0) {
				type = "tablet";
			}
		}

		// All detected feature phones running android are more likely a
		// smartphone
		if (type != null && type.equals("feature phone")
				&& this.operatingSystem != null && this.operatingSystem
						.getFamily().orElse("").equals("Android")) {
			type = "smartphone";
		}

		// According to
		// http://msdn.microsoft.com/en-us/library/ie/hh920767(v=vs.85).aspx
		// Internet Explorer 10 introduces the "Touch" UA string token. If this
		// token is present at the end of the UA string, the computer has touch
		// capability, and is running Windows 8 (or later).
		// This UA string will be transmitted on a touch-enabled system running
		// Windows 8 (RT)
		// As most touch enabled devices are tablets and only a smaller part are
		// desktops/notebooks we assume that all Windows 8 touch devices are
		// tablets.
		if (type == null && this.operatingSystem != null
				&& (this.operatingSystem.getName().equals("Windows RT")
						|| (this.operatingSystem.getName().equals("Windows")
								&& osVersion.isPresent()
								&& osVersion.get().compareTo(v8) >= 0))
				&& touchEnabledPattern.matcher(userAgent).find()) {
			type = "tablet";
		}

		// All devices running Opera TV Store are assumed to be a tv
		if (operaTvPattern.matcher(userAgent).find()) {
			type = "tv";
		}

		// Devices running Kylo or Espital TV Browsers are assumed to be a TV
		if (type == null && this.client != null
				&& tvBrowsers.contains(this.client.getName().orElse(""))) {
			type = "tv";
		}

		// set device type to desktop for all devices running a desktop os that
		// were not detected as an other device type
		if (type == null && isDesktop()) {
			type = "desktop";
		}

		if (type != null) {
			return new Device(type, brand, model);
		} else {
			return null;
		}
	}

	@JsonIgnore
	public String getUserAgent() {
		return this.userAgent;
	}

	public Optional<Device> getDevice() {
		return Optional.ofNullable(this.device);
	}

	public Optional<OperatingSystem> getOperatingSystem() {
		return Optional.ofNullable(this.operatingSystem);
	}

	public Optional<Client> getClient() {
		return Optional.ofNullable(this.client);
	}

	@JsonIgnore
	public boolean isTouchEnabled() {
		return touchEnabledPattern.matcher(userAgent).find();
	}

	@JsonIgnore
	public boolean isSmartphone() {
		return this.device != null ? this.device.getType().equals("smartphone")
				: false;
	}

	@JsonIgnore
	public boolean isFeaturePhone() {
		return this.device != null
				? this.device.getType().equals("feature phone") : false;
	}

	@JsonIgnore
	public boolean isTablet() {
		return this.device != null ? this.device.getType().equals("tablet")
				: false;
	}

	@JsonIgnore
	public boolean isPhablet() {
		return this.device != null ? this.device.getType().equals("phablet")
				: false;
	}

	@JsonIgnore
	public boolean isConsole() {
		return this.device != null ? this.device.getType().equals("console")
				: false;
	}

	@JsonIgnore
	public boolean isPortableMediaPlayer() {
		return this.device != null
				? this.device.getType().equals("portable media player") : false;
	}

	@JsonIgnore
	public boolean isCarBrowser() {
		return this.device != null ? this.device.getType().equals("car browser")
				: false;
	}

	@JsonIgnore
	public boolean isCamera() {
		return this.device != null ? this.device.getType().equals("camera")
				: false;
	}

	@JsonIgnore
	public boolean isTV() {
		return this.device != null ? this.device.getType().equals("tv") : false;
	}

	@JsonIgnore
	public boolean isSmartDisplay() {
		return this.device != null
				? this.device.getType().equals("smart display") : false;
	}

	@JsonIgnore
	public boolean isSmartSpeaker() {
		return this.device != null
				? this.device.getType().equals("smart speaker") : false;
	}

	@JsonIgnore
	public boolean usesMobileBrowser() {
		return this.client != null && this.client.getType().equals("browser")
				&& mobileOnlyBrowsers
						.contains(this.client.getName().orElse(""));
	}

	@JsonIgnore
	public boolean isMobile() {
		if (this.device != null
				&& mobileDeviceTypes.contains(this.device.getType())) {
			return true;
		}

		if (this.device != null
				&& nonMobileDeviceTypes.contains(this.device.getType())) {
			return false;
		}

		if (usesMobileBrowser()) {
			return true;
		}

		if (this.operatingSystem == null) {
			return false;
		}

		return !isBot() && !isDesktop();
	}

	@JsonIgnore
	public boolean isDesktop() {
		if (this.operatingSystem == null) {
			return false;
		}

		if (usesMobileBrowser()) {
			return false;
		}

		return desktopOperatingSystems
				.contains(this.operatingSystem.getFamily().orElse(""));
	}

	@JsonIgnore
	public boolean isBot() {
		return this.client != null ? this.client.getType().equals("bot")
				: false;
	}

	@JsonIgnore
	public boolean isBrowser() {
		return this.client != null ? this.client.getType().equals("browser")
				: false;
	}

	@JsonIgnore
	public boolean isFeedReader() {
		return this.client != null ? this.client.getType().equals("feed reader")
				: false;
	}

	@JsonIgnore
	public boolean isMobileApp() {
		return this.client != null ? this.client.getType().equals("mobile app")
				: false;
	}

	@JsonIgnore
	public boolean isPIM() {
		return this.client != null ? this.client.getType().equals("pim")
				: false;
	}

	@JsonIgnore
	public boolean isLibrary() {
		return this.client != null ? this.client.getType().equals("library")
				: false;
	}

	@JsonIgnore
	public boolean isMediaPlayer() {
		return this.client != null ? this.client.getType().equals("mediaplayer")
				: false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (this.device != null) {
			builder.append(this.device);
		}
		if (this.operatingSystem != null) {
			if (builder.length() > 0) {
				builder.append(", ");
			}
			builder.append(this.operatingSystem);
		}
		if (this.client != null) {
			if (builder.length() > 0) {
				builder.append(", ");
			}
			builder.append(this.client);
		}
		if (builder.length() == 0) {
			builder.append("unknown");
		}
		return builder.toString();
	}

}
