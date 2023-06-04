package org.jasa.jreport.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Utility class used to format a value from a {@link ResultSet} and apply a
 * custom format for {@link Date}, {@link Boolean}, and Decimal({@link Float},
 * {@link Double}, {@link BigDecimal}) data types.
 * 
 * @author Leonardo Sanchez J.
 */
public class Formatter {

	/**
	 * Format a {@link Date} as a {@link String} from a {@link ResultSet} extracted
	 * by a specific index.
	 * 
	 * @param resultSet {@link ResultSet} to extract the value.
	 * @param index     Index to extract the value from the {@link ResultSet}.
	 * @param pattern   Pattern to format the {@link Date} as a {@link String}
	 * @return
	 * @throws SQLException
	 */
	public static String forDate(ResultSet resultSet, int index, String pattern) throws SQLException {
		Date date = resultSet.getDate(index);
		if (pattern != null && !pattern.isEmpty()) {
			SimpleDateFormat formato = new SimpleDateFormat(pattern);
			Timestamp timestamp = new Timestamp(date.getTime());
			return formato.format(timestamp);
		}
		return date.toString();
	}

	/**
	 * Format a Decimal({@link Float}, {@link Double}, {@link BigDecimal}) as a
	 * {@link String} from a {@link ResultSet} extracted by a specific index.
	 * 
	 * @param resultSet     {@link ResultSet} to extract the value.
	 * @param index         Index to extract the value from the {@link ResultSet}.
	 * @param decimalFormat {@link DecimalFormat} to format the Decimal as a
	 *                      {@link String}
	 * @return
	 * @throws SQLException
	 */
	public static String forDecimal(ResultSet resultSet, int index, DecimalFormat decimalFormat) throws SQLException {
		String formatted = "";
		try {
			Object obj = resultSet.getObject(index);
			java.text.DecimalFormat textDecimalFormat = null;
			if (decimalFormat != null && decimalFormat.getTextDecimalFormat() != null) {
				textDecimalFormat = decimalFormat.getTextDecimalFormat();
			}
			if (textDecimalFormat != null) {
				if (obj instanceof Float) {
					Float floatValue = (Float) obj;
					formatted = textDecimalFormat.format(floatValue);

				} else if (obj instanceof Double) {
					Double doubleValue = (Double) obj;
					formatted = textDecimalFormat.format(doubleValue);
				} else if (obj instanceof BigDecimal) {
					BigDecimal bigDecimalValue = (BigDecimal) obj;
					formatted = textDecimalFormat.format(bigDecimalValue);
				}
			} else {
				formatted = obj.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formatted;
	}

	/**
	 * Format a {@link Boolean} as a {@link String} from a {@link ResultSet}
	 * extracted by a specific index.
	 * 
	 * @param resultSet {@link ResultSet} to extract the value.
	 * @param index     Index to extract the value from the {@link ResultSet}.
	 * @param trueV     Value for the {@code true} condition.
	 * @param falseV    Value for the {@code false} condition.
	 * @return
	 * @throws SQLException
	 */
	public static String forBoolean(ResultSet resultSet, int index, String trueV, String falseV) throws SQLException {
		Boolean bool = resultSet.getBoolean(index);
		return (bool ? trueV : falseV);
	}
}