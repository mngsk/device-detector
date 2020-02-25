package io.github.mngsk.devicedetector.client.browser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.github.mngsk.devicedetector.client.AbstractClientParser;
import io.github.mngsk.devicedetector.client.Client;

public class BrowserParser extends AbstractClientParser<BrowserRegex> {

	private List<EngineRegex> engines;
	private Map<String, List<String>> families;

	public BrowserParser()
			throws JsonParseException, JsonMappingException, IOException {
		this(new ObjectMapper(new YAMLFactory()));
	}

	public BrowserParser(ObjectMapper objectMapper)
			throws JsonParseException, JsonMappingException, IOException {
		super("browser", "regexes/client/browsers.yml", objectMapper);

		InputStream inputStream;
		CollectionType listType;

		inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("regexes/client/browser_engine.yml");
		listType = objectMapper.getTypeFactory()
				.constructCollectionType(List.class, EngineRegex.class);
		this.engines = objectMapper.readValue(inputStream, listType);

		inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("regexes/client/browser_family.yml");
		TypeReference<Map<String, List<String>>> mapType = new TypeReference<Map<String, List<String>>>() {};
		this.families = objectMapper.readValue(inputStream, mapType);
	}

	@Override
	public Optional<Client> parse(String userAgent) {
		for (BrowserRegex regex : super.regexes) {
			Matcher matcher = regex.getPattern().matcher(userAgent);
			if (matcher.find()) {
				String name = regex.getName();

				String version = super.buildVersion(regex.getVersion(),
						matcher);

				Engine engine = parseEngine(version, regex.getEngine(),
						userAgent);

				String family = null;
				for (Entry<String, List<String>> entry : this.families
						.entrySet()) {
					if (entry.getValue().contains(name)) {
						family = entry.getKey();
						break;
					}
				}

				return Optional.of(new Browser(name, version, engine, family));
			}
		}
		return Optional.empty();
	}

	private Engine parseEngine(String browserVersion,
			BrowserEngine browserEngine, String userAgent) {
		String engineName = parseEngineName(browserEngine, browserVersion);
		if (engineName == null) {
			engineName = parseEngineName(userAgent);
		}
		if (engineName == null) {
			return null;
		}

		String version = parseEngineVersion(engineName, userAgent);
		return new Engine(engineName, version);
	}

	private String parseEngineName(BrowserEngine browserEngine,
			String browserVersion) {
		if (browserEngine == null || browserEngine.getDefaultValue() == null) {
			return null;
		}

		if (browserEngine.getVersions() != null
				&& browserEngine.getVersions().containsKey(browserVersion)) {
			String engineName = browserEngine.getVersions().get(browserVersion);
			if (engineName != null) {
				return engineName;
			}
		}

		return browserEngine.getDefaultValue();
	}

	private String parseEngineName(String userAgent) {
		for (EngineRegex engine : this.engines) {
			Matcher matcher = engine.getPattern().matcher(userAgent);
			if (matcher.find()) {
				return engine.getName();
			}
		}
		return null;
	}

	private String parseEngineVersion(String engineName, String userAgent) {
		Pattern pattern = Pattern.compile(engineName
				+ "\\s*/?\\s*((?:(?=\\d+\\.\\d)\\d+[.\\d]*|\\d{1,7}(?=(?:\\D|$))))",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(userAgent);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

}
