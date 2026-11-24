package com.egypt.utills;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads src/test/resources/config.properties.
 * A -Dkey=value system property always overrides the file value.
 */
public final class ConfigReader {

	private static final Properties PROPS = new Properties();
	private static final Pattern PLACEHOLDER = Pattern.compile("\\$\\{([^}]+)}");

	static {
		try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
			if (in == null) {
				throw new IllegalStateException("config.properties not found in src/test/resources");
			}
			PROPS.load(in);
		} catch (IOException e) {
			throw new IllegalStateException("Unable to read config.properties", e);
		}
	}

	private ConfigReader() {
	}

	/** Returns the value for the key; fails fast if the key is missing. */
	public static String get(String key) {
		String value = System.getProperty(key, PROPS.getProperty(key));
		if (value == null) {
			throw new IllegalArgumentException("Missing key in config.properties: " + key);
		}
		return value.trim();
	}

	public static int getInt(String key) {
		return Integer.parseInt(get(key));
	}

	/** Replaces every ${key} in the text with its config value. Plain text is returned unchanged. */
	public static String resolve(String text) {
		if (text == null) {
			return null;
		}
		Matcher m = PLACEHOLDER.matcher(text);
		StringBuilder sb = new StringBuilder();
		while (m.find()) {
			m.appendReplacement(sb, Matcher.quoteReplacement(get(m.group(1))));
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
