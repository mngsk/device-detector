package io.github.mngsk.devicedetector.client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PersonalInformationManagerClientParser
		extends AbstractClientParser<ClientRegex> {

	public PersonalInformationManagerClientParser()
			throws JsonParseException, JsonMappingException, IOException {
		super("pim", "regexes/client/pim.yml");
	}

	public PersonalInformationManagerClientParser(ObjectMapper objectMapper)
			throws JsonParseException, JsonMappingException, IOException {
		super("pim", "regexes/client/pim.yml", objectMapper);
	}

}
