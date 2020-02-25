package io.github.mngsk.devicedetector.device;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CarDeviceParser extends AbstractDeviceParser {

	public CarDeviceParser()
			throws JsonParseException, JsonMappingException, IOException {
		super("regexes/device/car_browsers.yml");
	}

	public CarDeviceParser(ObjectMapper objectMapper)
			throws JsonParseException, JsonMappingException, IOException {
		super("regexes/device/car_browsers.yml", objectMapper);
	}

}
