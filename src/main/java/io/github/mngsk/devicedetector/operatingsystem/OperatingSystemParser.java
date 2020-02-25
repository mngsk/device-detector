package io.github.mngsk.devicedetector.operatingsystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Matcher;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.github.mngsk.devicedetector.util.AbstractParser;

public class OperatingSystemParser extends AbstractParser<OperatingSystem> {

	private List<OperatingSystemRegex> operatingSystems;
	private Map<String, List<String>> families;
	private List<PlatformRegex> platforms;

	public OperatingSystemParser()
			throws JsonParseException, JsonMappingException, IOException {
		this(new ObjectMapper(new YAMLFactory()));
	}

	public OperatingSystemParser(ObjectMapper objectMapper)
			throws JsonParseException, JsonMappingException, IOException {
		InputStream inputStream;
		CollectionType listType;

		inputStream = getClass().getClassLoader()
				.getResourceAsStream("regexes/oss.yml");
		listType = objectMapper.getTypeFactory().constructCollectionType(
				List.class, OperatingSystemRegex.class);
		this.operatingSystems = objectMapper.readValue(inputStream, listType);

		inputStream = getClass().getClassLoader()
				.getResourceAsStream("regexes/ossfamilies.yml");
		TypeReference<Map<String, List<String>>> mapType = new TypeReference<Map<String, List<String>>>() {};
		this.families = objectMapper.readValue(inputStream, mapType);

		inputStream = getClass().getClassLoader()
				.getResourceAsStream("regexes/ossplatforms.yml");
		listType = objectMapper.getTypeFactory()
				.constructCollectionType(List.class, PlatformRegex.class);
		this.platforms = objectMapper.readValue(inputStream, listType);
	}

	@Override
	public Optional<OperatingSystem> parse(String userAgent) {
		String name = null;
		String version = null;
		for (OperatingSystemRegex os : this.operatingSystems) {
			Matcher matcher = os.getPattern().matcher(userAgent);
			if (matcher.find()) {
				name = super.buildByMatch(os.getName(), matcher);

				version = super.buildVersion(os.getVersion().orElse(null),
						matcher);

				break;
			}
		}
		if (name == null) {
			return Optional.empty();
		}

		String family = null;
		for (Entry<String, List<String>> entry : this.families.entrySet()) {
			if (entry.getValue().contains(name)) {
				family = entry.getKey();
				break;
			}
		}

		String platform = null;
		for (PlatformRegex p : this.platforms) {
			Matcher matcher = p.getPattern().matcher(userAgent);
			if (matcher.find()) {
				platform = p.getName();
				break;
			}
		}

		return Optional
				.of(new OperatingSystem(name, family, platform, version));
	}

}
