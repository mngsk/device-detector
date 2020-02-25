package io.github.mngsk.devicedetector.device;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CameraDeviceParser extends AbstractDeviceParser {

	public CameraDeviceParser()
			throws JsonParseException, JsonMappingException, IOException {
		super("regexes/device/cameras.yml");
	}

	public CameraDeviceParser(ObjectMapper objectMapper)
			throws JsonParseException, JsonMappingException, IOException {
		super("regexes/device/cameras.yml", objectMapper);
	}

}
