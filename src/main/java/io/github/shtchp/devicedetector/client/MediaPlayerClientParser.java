package io.github.shtchp.devicedetector.client;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MediaPlayerClientParser extends AbstractClientParser<ClientRegex> {

  public MediaPlayerClientParser() {
    super("mediaplayer", "regexes/client/mediaplayers.yml");
  }

  public MediaPlayerClientParser(ObjectMapper objectMapper) {
    super("mediaplayer", "regexes/client/mediaplayers.yml", objectMapper);
  }
}
