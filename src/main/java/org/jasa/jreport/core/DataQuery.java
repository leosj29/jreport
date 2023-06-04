package org.jasa.jreport.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.jasa.jreport.util.DataType;
import org.jasa.jreport.util.DecimalFormat;
import org.jasa.jreport.util.HTMLField;
import org.jasa.jreport.util.Type;

/**
 * Class for handling data query type. For each {@link DataQuery} in the
 * {@link JReport}, a unique key, an SQL query for data extraction, and a
 * {@link DataSource} should be assigned. Additionally, it is possible to
 * specify which values should be formatted (DECIMAL, BOOLEAN and DATE) and
 * which fields contain HTML content.
 * 
 * @author Leonardo Sanchez J.
 */
public class DataQuery {

	private String key;
	private String query;
	private DataSource dataSource;
	private List<HTMLField> htmlField;
	private List<DataType> formats;
	private String trueValue = "Yes";
	private String falseValue = "No";

	private DataQuery() {
		this.formats = new ArrayList<>();
		htmlField = new ArrayList<>();
		dataSource = null;
	}

	public String getKey() {
		return this.key;
	}

	public String getQuery() {
		return this.query;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public List<HTMLField> getHTMLField() {
		return htmlField;
	}

	public List<DataType> getFormats() {
		return this.formats;
	}

	public String getTrueValue() {
		return this.trueValue;
	}

	public String getFalseValue() {
		return this.falseValue;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {

		private DataQuery dataQuery;

		private Builder() {
			dataQuery = new DataQuery();
		}

		/**
		 * Unique key to represent the data query.
		 * 
		 * @param key
		 * @return
		 */
		public Builder key(String key) {
			dataQuery.key = key;
			return this;
		}

		/**
		 * SQL query for data extraction.
		 * 
		 * @param query
		 * @return
		 */
		public Builder query(String query) {
			dataQuery.query = query;
			return this;
		}

		/**
		 * {@link DataSource} for data extraction.
		 * 
		 * @param dataSource
		 * @return
		 */
		public Builder dataSource(DataSource dataSource) {
			dataQuery.dataSource = dataSource;
			return this;
		}

		/**
		 * Fields contain HTML content.
		 * 
		 * @param htmlField
		 * @return
		 */
		public Builder htmlField(HTMLField htmlField) {
			dataQuery.htmlField.add(htmlField);
			return this;
		}

		/**
		 * Fields contain HTML content.
		 * 
		 * @param htmlFields
		 * @return
		 */
		public Builder htmlField(List<HTMLField> htmlFields) {
			if (htmlFields != null)
				dataQuery.htmlField.addAll(htmlFields);
			return this;
		}

		/**
		 * Format a {@link Boolean} value to a {@link String}. By default, the values
		 * are 'Yes' and 'No', but if you wish to modify them, you can use the
		 * {@code booleanValues(String, String)} method.
		 * 
		 * @param columnName Column name.
		 * @return
		 */
		public Builder booleanFormat(String columnName) {
			dataQuery.formats.add(DataType.forBoolean(columnName, Type.BOOLEAN));
			return this;
		}

		/**
		 * Format a {@link Date} value to a {@link String} using a valid date pattern.
		 * Example: "dd/MM/yyyy HH:mm a".
		 * 
		 * @param columnName Column name.
		 * @param pattern    Pattern.
		 * @return
		 */
		public Builder dateFormat(String columnName, String pattern) {
			dataQuery.formats.add(DataType.forDate(columnName, Type.DATE, pattern));
			return this;
		}

		/**
		 * Format a Decimal ({@link Float}, {@link Double}, {@link BigDecimal}) value to
		 * a {@link String} using a valid {@link DecimalFormat}.
		 * 
		 * @param columnName    Column name.
		 * @param decimalFormat {@link DecimalFormat}.
		 * @return
		 */
		public Builder decimalFormat(String columnName, DecimalFormat decimalFormat) {
			dataQuery.formats.add(DataType.forDecimal(columnName, Type.DECIMAL, decimalFormat));
			return this;
		}

		/**
		 * Override the default values of boolean fields ("Yes"/"No").
		 * 
		 * @param trueV  Value for the {@code true} condition.
		 * @param falseV Value for the {@code false} condition.
		 * @return
		 */

		public Builder booleanValues(String trueV, String falseV) {
			if (trueV != null && falseV != null) {
				dataQuery.trueValue = trueV;
				dataQuery.falseValue = falseV;
			}
			return this;
		}

		public DataQuery build() {
			return dataQuery;
		}
	}
}