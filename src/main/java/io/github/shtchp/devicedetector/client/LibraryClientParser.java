package io.github.shtchp.devicedetector.client;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LibraryClientParser extends AbstractClientParser<ClientRegex> {

  public LibraryClientParser() {
    super("library", "regexes/client/libraries.yml");
  }

  public LibraryClientParser(ObjectMapper objectMapper) {
    super("library", "regexes/client/libraries.yml", objectMapper);
  }
}
