package io.github.mngsk.devicedetector.client;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ClientRegex {

	private String regex;
	private String name;
	private String version;

	@JsonIgnore
	private Pattern pattern;

	public String getRegex() {
		return this.regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
		this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Pattern getPattern() {
		return this.pattern;
	}

}
