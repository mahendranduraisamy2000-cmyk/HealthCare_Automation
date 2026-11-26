package com.egypt.utills;

import java.util.Locale;

import net.datafaker.Faker;

/**
 * Generates realistic, unique test data with Datafaker.
 * Lengths respect the Office Location form limits (Facility 32, Address1 64, City 16).
 */
public final class TestDataGenerator {

	private static final Faker FAKER = new Faker(Locale.ENGLISH);

	private TestDataGenerator() {
	}

	private static String limit(String value, int max) {
		value = value.replaceAll("[^A-Za-z0-9 .\\-]", "").trim();
		return value.length() > max ? value.substring(0, max).trim() : value;
	}

	/** Unique facility name, e.g. "Auto Nile Clinic 48213" (max 32 chars). */
	public static String facilityName() {
		String suffix = " " + FAKER.number().digits(5);
		String base = "Auto " + FAKER.name().lastName() + " Clinic";
		return limit(base, 32 - suffix.length()) + suffix;
	}

	public static String firstName() {
		return limit(FAKER.name().firstName(), 16);
	}

	public static String lastName() {
		return limit(FAKER.name().lastName(), 16);
	}

	public static String address1() {
		return limit(FAKER.address().streetAddress(), 64);
	}

	/** Egyptian mobile number in the UI format "+20 XX XXX XXXX" (e.g. +20 10 123 4567). */
	public static String egyptPhone() {
		String[] prefixes = { "10", "11", "12", "15" };
		return "+20 " + prefixes[FAKER.number().numberBetween(0, prefixes.length)] + " "
				+ FAKER.number().digits(3) + " " + FAKER.number().digits(4);
	}

	/** Email limited to 32 characters (form maxlength). */
	public static String email(String firstName, String lastName) {
		String user = (firstName.charAt(0) + lastName + FAKER.number().digits(3)).toLowerCase().replaceAll("[^a-z0-9]", "");
		if (user.length() > 20) {
			user = user.substring(0, 20);
		}
		return user + "@example.com";
	}

	public static String postalCode() {
		return FAKER.number().digits(5);
	}
}
