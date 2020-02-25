package io.github.mngsk.devicedetector.device;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DeviceRegex {

	private String regex;
	private String device;
	private String model;
	private List<ModelRegex> models;

	@JsonIgnore
	private Pattern pattern;

	public String getRegex() {
		return this.regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
		this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	}

	public String getDevice() {
		return this.device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public Optional<String> getModel() {
		return Optional.ofNullable(this.model);
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<ModelRegex> getModels() {
		return this.models;
	}

	public void setModels(List<ModelRegex> models) {
		this.models = models;
	}

	public Pattern getPattern() {
		return this.pattern;
	}

}
