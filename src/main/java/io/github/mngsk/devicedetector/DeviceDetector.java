package io.github.mngsk.devicedetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.github.mngsk.devicedetector.client.AbstractClientParser;
import io.github.mngsk.devicedetector.client.Client;
import io.github.mngsk.devicedetector.client.FeedReaderClientParser;
import io.github.mngsk.devicedetector.client.LibraryClientParser;
import io.github.mngsk.devicedetector.client.MediaPlayerClientParser;
import io.github.mngsk.devicedetector.client.MobileAppClientParser;
import io.github.mngsk.devicedetector.client.PersonalInformationManagerClientParser;
import io.github.mngsk.devicedetector.client.bot.BotParser;
import io.github.mngsk.devicedetector.client.browser.BrowserParser;
import io.github.mngsk.devicedetector.device.AbstractDeviceParser;
import io.github.mngsk.devicedetector.device.CameraDeviceParser;
import io.github.mngsk.devicedetector.device.CarDeviceParser;
import io.github.mngsk.devicedetector.device.ConsoleDeviceParser;
import io.github.mngsk.devicedetector.device.Device;
import io.github.mngsk.devicedetector.device.MobileDeviceParser;
import io.github.mngsk.devicedetector.device.NotebookDeviceParser;
import io.github.mngsk.devicedetector.device.PortableMediaPlayerDeviceParser;
import io.github.mngsk.devicedetector.device.TelevisionDeviceParser;
import io.github.mngsk.devicedetector.operatingsystem.OperatingSystem;
import io.github.mngsk.devicedetector.operatingsystem.OperatingSystemParser;

public class DeviceDetector {

	public static final String VERSION = "3.12.3";

	private List<AbstractDeviceParser> deviceParsers;
	private OperatingSystemParser operatingSystemParser;
	private List<AbstractClientParser<?>> clientParsers;

	private DeviceDetector(List<AbstractDeviceParser> deviceParsers,
			OperatingSystemParser operatingSystemParser,
			List<AbstractClientParser<?>> clientParsers) {
		this.deviceParsers = deviceParsers;
		this.operatingSystemParser = operatingSystemParser;
		this.clientParsers = clientParsers;
	}

	public Detection detect(String userAgent) {
		if (userAgent == null || userAgent.trim().isEmpty()) {
			throw new IllegalArgumentException("The user agent is required");
		}

		Optional<Device> device = Optional.empty();
		for (AbstractDeviceParser parser : this.deviceParsers) {
			Optional<Device> d = parser.parse(userAgent);
			if (d.isPresent()) {
				device = d;
				break;
			}
		}

		Optional<OperatingSystem> operatingSystem = Optional.empty();
		if (this.operatingSystemParser != null) {
			operatingSystem = this.operatingSystemParser.parse(userAgent);
		}

		Optional<Client> client = Optional.empty();
		for (AbstractClientParser<?> parser : this.clientParsers) {
			Optional<Client> c = parser.parse(userAgent);
			if (c.isPresent()) {
				client = c;
				break;
			}
		}

		return new Detection(userAgent, device.orElse(null),
				operatingSystem.orElse(null), client.orElse(null));
	}

	public static class DeviceDetectorBuilder {

		// Devices
		private boolean enableCameras = true;
		private boolean enableCars = true;
		private boolean enableConsoles = true;
		private boolean enableMobiles = true;
		private boolean enablePortableMediaPlayers = true;
		private boolean enableTelevisions = true;
		private boolean enableNotebooks = true;

		private boolean enableOperatingSystems = true;

		// Clients
		private boolean enableBots = true;
		private boolean enableBrowsers = true;
		private boolean enableFeedReaders = true;
		private boolean enableLibraries = true;
		private boolean enableMediaPlayers = true;
		private boolean enableMobileApps = true;
		private boolean enablePersonalInformationManagers = true;

		public DeviceDetectorBuilder enableEverything() {
			enableAllDevices();
			enableOperatingSystems();
			enableAllClients();
			return this;
		}

		public DeviceDetectorBuilder enableAllDevices() {
			this.enableCameras = true;
			this.enableCars = true;
			this.enableConsoles = true;
			this.enableMobiles = true;
			this.enablePortableMediaPlayers = true;
			this.enableTelevisions = true;
			this.enableNotebooks = true;
			return this;
		}

		public DeviceDetectorBuilder enableCameras() {
			this.enableCameras = true;
			return this;
		}

		public DeviceDetectorBuilder enableCars() {
			this.enableCars = true;
			return this;
		}

		public DeviceDetectorBuilder enableConsoles() {
			this.enableConsoles = true;
			return this;
		}

		public DeviceDetectorBuilder enableMobiles() {
			this.enableMobiles = true;
			return this;
		}

		public DeviceDetectorBuilder enablePortableMediaPlayers() {
			this.enablePortableMediaPlayers = true;
			return this;
		}

		public DeviceDetectorBuilder enableTelevisions() {
			this.enableTelevisions = true;
			return this;
		}

		public DeviceDetectorBuilder enableNotebooks() {
			this.enableNotebooks = true;
			return this;
		}

		public DeviceDetectorBuilder enableOperatingSystems() {
			this.enableOperatingSystems = true;
			return this;
		}

		public DeviceDetectorBuilder enableAllClients() {
			this.enableBots = true;
			this.enableBrowsers = true;
			this.enableFeedReaders = true;
			this.enableLibraries = true;
			this.enableMediaPlayers = true;
			this.enableMobileApps = true;
			this.enablePersonalInformationManagers = true;
			return this;
		}

		public DeviceDetectorBuilder enableBots() {
			this.enableBots = true;
			return this;
		}

		public DeviceDetectorBuilder enableBrowsers() {
			this.enableBrowsers = true;
			return this;
		}

		public DeviceDetectorBuilder enableFeedReaders() {
			this.enableFeedReaders = true;
			return this;
		}

		public DeviceDetectorBuilder enableLibraries() {
			this.enableLibraries = true;
			return this;
		}

		public DeviceDetectorBuilder enableMediaPlayers() {
			this.enableMediaPlayers = true;
			return this;
		}

		public DeviceDetectorBuilder enableMobileApps() {
			this.enableMobileApps = true;
			return this;
		}

		public DeviceDetectorBuilder enablePersonalInformationManagers() {
			this.enablePersonalInformationManagers = true;
			return this;
		}

		public DeviceDetectorBuilder disableEverything() {
			disableAllDevices();
			disableOperatingSystems();
			disableAllClients();
			return this;
		}

		public DeviceDetectorBuilder disableAllDevices() {
			this.enableCameras = false;
			this.enableCars = false;
			this.enableConsoles = false;
			this.enableMobiles = false;
			this.enablePortableMediaPlayers = false;
			this.enableTelevisions = false;
			this.enableNotebooks = false;
			return this;
		}

		public DeviceDetectorBuilder disableCameras() {
			this.enableCameras = false;
			return this;
		}

		public DeviceDetectorBuilder disableCars() {
			this.enableCars = false;
			return this;
		}

		public DeviceDetectorBuilder disableConsoles() {
			this.enableConsoles = false;
			return this;
		}

		public DeviceDetectorBuilder disableMobiles() {
			this.enableMobiles = false;
			return this;
		}

		public DeviceDetectorBuilder disablePortableMediaPlayers() {
			this.enablePortableMediaPlayers = false;
			return this;
		}

		public DeviceDetectorBuilder disableTelevisions() {
			this.enableTelevisions = false;
			return this;
		}

		public DeviceDetectorBuilder disableNotebooks() {
			this.enableNotebooks = false;
			return this;
		}

		public DeviceDetectorBuilder disableOperatingSystems() {
			this.enableOperatingSystems = false;
			return this;
		}

		public DeviceDetectorBuilder disableAllClients() {
			this.enableBots = false;
			this.enableBrowsers = false;
			this.enableFeedReaders = false;
			this.enableLibraries = false;
			this.enableMediaPlayers = false;
			this.enableMobileApps = false;
			this.enablePersonalInformationManagers = false;
			return this;
		}

		public DeviceDetectorBuilder disableBots() {
			this.enableBots = false;
			return this;
		}

		public DeviceDetectorBuilder disableBrowsers() {
			this.enableBrowsers = false;
			return this;
		}

		public DeviceDetectorBuilder disableFeedReaders() {
			this.enableFeedReaders = false;
			return this;
		}

		public DeviceDetectorBuilder disableLibraries() {
			this.enableLibraries = false;
			return this;
		}

		public DeviceDetectorBuilder disableMediaPlayers() {
			this.enableMediaPlayers = false;
			return this;
		}

		public DeviceDetectorBuilder disableMobileApps() {
			this.enableMobileApps = false;
			return this;
		}

		public DeviceDetectorBuilder disablePersonalInformationManagers() {
			this.enablePersonalInformationManagers = false;
			return this;
		}

		public DeviceDetector build() {
			ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory())
					.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

			List<AbstractDeviceParser> deviceParsers = new ArrayList<>();
			if (this.enableTelevisions) {
				deviceParsers.add(new TelevisionDeviceParser(objectMapper));
			}
			if (this.enableNotebooks) {
				deviceParsers.add(new NotebookDeviceParser(objectMapper));
			}
			if (this.enableConsoles) {
				deviceParsers.add(new ConsoleDeviceParser(objectMapper));
			}
			if (this.enableCars) {
				deviceParsers.add(new CarDeviceParser(objectMapper));
			}
			if (this.enableCameras) {
				deviceParsers.add(new CameraDeviceParser(objectMapper));
			}
			if (this.enablePortableMediaPlayers) {
				deviceParsers
						.add(new PortableMediaPlayerDeviceParser(objectMapper));
			}
			if (this.enableMobiles) {
				deviceParsers.add(new MobileDeviceParser(objectMapper));
			}

			OperatingSystemParser operatingSystemParser = null;
			if (this.enableOperatingSystems) {
				operatingSystemParser = new OperatingSystemParser(objectMapper);
			}

			List<AbstractClientParser<?>> clientParsers = new ArrayList<>();
			if (this.enableFeedReaders) {
				clientParsers.add(new FeedReaderClientParser(objectMapper));
			}
			if (this.enableMobileApps) {
				clientParsers.add(new MobileAppClientParser(objectMapper));
			}
			if (this.enableMediaPlayers) {
				clientParsers.add(new MediaPlayerClientParser(objectMapper));
			}
			if (this.enablePersonalInformationManagers) {
				clientParsers.add(new PersonalInformationManagerClientParser(
						objectMapper));
			}
			if (this.enableBrowsers) {
				clientParsers.add(new BrowserParser(objectMapper));
			}
			if (this.enableLibraries) {
				clientParsers.add(new LibraryClientParser(objectMapper));
			}
			if (this.enableBots) {
				clientParsers.add(new BotParser(objectMapper));
			}

			return new DeviceDetector(deviceParsers, operatingSystemParser,
					clientParsers);
		}

	}

}
