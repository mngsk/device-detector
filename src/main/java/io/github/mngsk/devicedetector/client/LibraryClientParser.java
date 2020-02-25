package io.github.mngsk.devicedetector.client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LibraryClientParser extends AbstractClientParser<ClientRegex> {

	public LibraryClientParser()
			throws JsonParseException, JsonMappingException, IOException {
		super("library", "regexes/client/libraries.yml");
	}

	public LibraryClientParser(ObjectMapper objectMapper)
			throws JsonParseException, JsonMappingException, IOException {
		super("library", "regexes/client/libraries.yml", objectMapper);
	}

}
