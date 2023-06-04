package org.jasa.jreport.util;

/**
 * Utility class for setting value formatting properties in relation to an
 * assigned type.
 * 
 * @author Leonardo Sanchez J.
 */
public final class DataType {

	private String columnName;
	private Type type;
	private String pattern;
	private DecimalFormat decimalFormat;

	private DataType(String columnName, Type type) {
		this.columnName = columnName;
		this.type = type;
	}

	private DataType(String columnName, Type type, String pattern) {
		this.columnName = columnName;
		this.type = type;
		this.pattern = pattern;
	}

	private DataType(String columnName, Type type, DecimalFormat decimalFormat) {
		this.columnName = columnName;
		this.type = type;
		this.decimalFormat = decimalFormat;
	}

	/**
	 * For {@link Type} equals {@code Type.DATE}
	 * 
	 * @param columnName Field identifier.
	 * @param type       Field {@link Type}.
	 * @param pattern    Pattern to be applied. Example:"dd/MM/yyyy HH:mm a"
	 * @return
	 */
	public static DataType forDate(String columnName, Type type, String pattern) {
		return new DataType(columnName, type, pattern);
	}

	/**
	 * For {@link Type} equals {@code Type.DECIMAL}
	 * 
	 * @param columnName    Field identifier.
	 * @param type          Field {@link Type}.
	 * @param decimalFormat Field {@link DecimalFormat} to be applied.
	 * @return
	 */

	public static DataType forDecimal(String columnName, Type type, DecimalFormat decimalFormat) {
		return new DataType(columnName, type, decimalFormat);
	}

	/**
	 * For {@link Type} equals {@code Type.BOOLEAN}
	 * 
	 * @param columnName Field identifier.
	 * @param type       Field {@link Type}.
	 * @return
	 */
	public static DataType forBoolean(String columnName, Type type) {
		return new DataType(columnName, type);
	}

	public String getColumnName() {
		return this.columnName;
	}

	public Type getType() {
		return this.type;
	}

	public String getPattern() {
		return this.pattern;
	}

	public DecimalFormat getDecimalFormat() {
		return this.decimalFormat;
	}
}