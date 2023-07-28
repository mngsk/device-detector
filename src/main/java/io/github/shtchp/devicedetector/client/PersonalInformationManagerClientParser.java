package io.github.shtchp.devicedetector.client;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PersonalInformationManagerClientParser extends AbstractClientParser<ClientRegex> {

  public PersonalInformationManagerClientParser() {
    super("pim", "regexes/client/pim.yml");
  }

  public PersonalInformationManagerClientParser(ObjectMapper objectMapper) {
    super("pim", "regexes/client/pim.yml", objectMapper);
  }
}
