package org.jasa.jreport.util;

/**
 * Enum with data types for value formatting.
 * 
 * @author Leonardo Sanchez J.
 */
public enum Type {
	BOOLEAN("BOOLEAN"),
	DATE("DATE"),
	DECIMAL("DECIMAL"),
	STRING("STRING");

	private final String name;

	Type(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}