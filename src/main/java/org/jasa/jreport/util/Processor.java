package org.jasa.jreport.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.jasa.jreport.core.DataList;
import org.jasa.jreport.core.DataQuery;
import org.jasa.jreport.core.Model;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.document.SyntaxKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

/**
 * Utility class for processing data sources of the report (value data, data
 * lists and data queries).
 * 
 * @author Leonardo Sanchez J.
 */
public class Processor {

	/**
	 * Process fields with HTML content to be parsed at runtime.
	 * 
	 * @param fieldsMetadata
	 * @param model
	 */
	private static void htmlFields(DataQuery dataQuery, FieldsMetadata fieldsMetadata) {
		for (HTMLField htmlField : dataQuery.getHTMLField()) {
			for (String key : htmlField.getHtmlFileds()) {
				fieldsMetadata.addFieldAsTextStyling(key, SyntaxKind.Html, true);
			}
		}
	}

	/**
	 * Process basic value data.
	 * 
	 * @param context
	 * @param model
	 */
	public static void valueData(IContext context, Model model) {
		if (!model.getValueData().isEmpty()) {
			for (Map.Entry<String, String> entry : model.getValueData().entrySet()) {
				context.put(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * Process data lists.
	 * 
	 * @param context
	 * @param fieldsMetadata
	 * @param report
	 * @param model
	 * @throws XDocReportException
	 */
	public static void dataList(IContext context, FieldsMetadata fieldsMetadata, IXDocReport report, Model model)
			throws XDocReportException {
		if (!model.getDataList().isEmpty()) {
			for (Object objDataList : model.getDataList()) {
				DataList dataList = (DataList) objDataList;
				fieldsMetadata.load(dataList.getKey(), dataList.getClazz(), true);
				context.put(dataList.getKey(), dataList.getData());
			}
		}
	}

	/**
	 * Process data queries.
	 * 
	 * @param context
	 * @param report
	 * @param fieldsMetadata
	 * @param model
	 * @throws XDocReportException
	 */
	public static void dataQuery(IContext context, FieldsMetadata fieldsMetadata, IXDocReport report, Model model)
			throws XDocReportException {
		try {
			if (!model.getDataQuery().isEmpty()) {
				for (DataQuery dataQuery : model.getDataQuery()) {
					htmlFields(dataQuery, fieldsMetadata);
					List<Map<String, Object>> results = execSQL(dataQuery.getDataSource(), dataQuery);
					context.put(dataQuery.getKey(), results);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static List<Map<String, Object>> execSQL(DataSource dataSource, DataQuery dataQuery) throws SQLException {
		String sql = dataQuery.getQuery();
		List<DataType> formats = dataQuery.getFormats();
		Connection connection = dataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		List<Map<String, Object>> results = new ArrayList<>();
		int columnCount = resultSet.getMetaData().getColumnCount();
		while (resultSet.next()) {
			Map<String, Object> row = new HashMap<>();
			for (int i = 1; i <= columnCount; i++) {
				// Column name
				String columnName = resultSet.getMetaData().getColumnName(i);
				DataType dataType = null;
				Optional<DataType> oDataType = formats.stream()
						.filter(dt -> dt.getColumnName().equals(columnName))
						.findFirst();
				if (oDataType.isPresent())
					dataType = oDataType.get();

				Object columnValue = null;
				if (dataType == null) {
					columnValue = resultSet.getString(i);
				} else {
					switch (dataType.getType().getName()) {
						case "DATE":
							columnValue = Formatter.forDate(resultSet, i, dataType.getPattern());
							break;
						case "DECIMAL":
							columnValue = Formatter.forDecimal(resultSet, i, dataType.getDecimalFormat());
							break;
						case "BOOLEAN":
							columnValue = Formatter.forBoolean(resultSet, i, dataQuery.getTrueValue(),
									dataQuery.getFalseValue());
							break;
						default:
							columnValue = resultSet.getString(i);
							break;
					}
				}
				row.put(columnName, columnValue);
			}
			results.add(row);
		}
		resultSet.close();
		statement.close();
		connection.close();
		return results;
	}
}