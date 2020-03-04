package io.github.mngsk.devicedetector.device;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Matcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.github.mngsk.devicedetector.util.AbstractParser;

public class AbstractDeviceParser extends AbstractParser<Device> {

	private Map<String, DeviceRegex> devices;

	public AbstractDeviceParser(String fixtureFile) {
		this(fixtureFile, new ObjectMapper(new YAMLFactory()));
	}

	public AbstractDeviceParser(String fixtureFile, ObjectMapper objectMapper) {
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(fixtureFile);
		MapType mapType = objectMapper.getTypeFactory().constructMapType(
				LinkedHashMap.class, String.class, DeviceRegex.class);
		try {
			this.devices = objectMapper.readValue(inputStream, mapType);
		} catch (IOException e) {
			throw new RuntimeException("Could not load " + fixtureFile, e);
		}
	}

	@Override
	public Optional<Device> parse(String userAgent) {
		for (Entry<String, DeviceRegex> device : this.devices.entrySet()) {
			Matcher matcher = device.getValue().getPattern().matcher(userAgent);
			if (matcher.find()) {
				Optional<Device> result = parseDevice(device, userAgent);
				if (result.isPresent()) {
					return result;
				}

				String type = device.getValue().getDevice();

				String brand = device.getKey();
				if (brand.equalsIgnoreCase("unknown")) {
					brand = null;
				}

				String model = buildModel(
						device.getValue().getModel().orElse(null), matcher);

				return Optional.of(new Device(type, brand, model));
			}
		}

		return Optional.empty();
	}

	private Optional<Device> parseDevice(Entry<String, DeviceRegex> device,
			String userAgent) {
		if (device.getValue().getModels() == null
				|| device.getValue().getModels().isEmpty()) {
			return Optional.empty();
		}

		for (ModelRegex regex : device.getValue().getModels()) {
			Matcher matcher = regex.getPattern().matcher(userAgent);
			if (matcher.find()) {
				String type = regex.getDevice()
						.orElse(device.getValue().getDevice());

				String brand = regex.getBrand().orElse(device.getKey());
				if (brand.equalsIgnoreCase("unknown")) {
					brand = null;
				}

				String model = buildModel(regex.getModel(), matcher);

				return Optional.of(new Device(type, brand, model));
			}
		}

		return Optional.empty();
	}

	private String buildModel(String model, Matcher matcher) {
		model = super.buildByMatch(model, matcher);
		if (model == null) {
			return null;
		}

		model = model.replace("_", " ");
		if (model.equals("Build")) {
			return null;
		}

		return model.trim().isEmpty() ? null : model.trim();
	}

}
