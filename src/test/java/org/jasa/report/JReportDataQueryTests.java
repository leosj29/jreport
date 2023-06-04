package org.jasa.report;

import java.nio.file.FileSystems;
import java.time.LocalDate;

import javax.sql.DataSource;

import org.jasa.jreport.JReportApplication;
import org.jasa.jreport.core.DataQuery;
import org.jasa.jreport.core.JReport;
import org.jasa.jreport.core.Model;
import org.jasa.jreport.util.DecimalFormat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import oracle.jdbc.pool.OracleDataSource;

/**
 * Test for JReport with data queries values.
 * 
 * @author Leonardo Sanchez J.
 */
@SpringBootTest(classes = JReportApplication.class)
class JReportDataQueryTests {

	private final String OTD_PATH = (FileSystems.getDefault().getPath(System.getProperty("user.dir"), "odts"))
			.toString();

	private final String DB_URL = "YOUR DB_URL";
	private final String DB_USER = "YOUR DB_USER";
	private final String DB_PASS = "YOUR DB_PASS";

	private DataSource dataSouce() {
		DataSource dataSource = null;
		try {
			OracleDataSource dataSourceO = new OracleDataSource();
			dataSourceO.setURL(DB_URL);
			dataSourceO.setUser(DB_USER);
			dataSourceO.setPassword(DB_PASS);
			dataSource = (DataSource) dataSourceO;
		} catch (Exception e) {
		}
		return dataSource;
	}

	@Test
	void dataSouce1() {

		String query = "SELECT ID, NAME, CREATED FROM USER";

		Model model = Model.builder()
				.in(FileSystems.getDefault().getPath(OTD_PATH))
				.inName("datasource1")
				.valueData("date", LocalDate.now().toString())
				.dataQuery(DataQuery.builder()
						.dataSource(dataSouce())
						.key("users")
						.query(query)
						.build())
				.build();

		new JReport().generate(model);
	}

	@Test
	void dataSouceTwoOrMoreQuery() {

		String query = "SELECT ID, NAME, CREATED FROM USER";
		String query2 = "SELECT ID, NAME, WEIGHT, ACTIVE FROM ROL";

		Model model = Model.builder()
				.in(FileSystems.getDefault().getPath(OTD_PATH))
				.inName("datasource2")
				// Date param
				.valueData("date", LocalDate.now().toString())
				// Query 1
				.dataQuery(DataQuery.builder()
						.dataSource(dataSouce())
						.key("users")
						.query(query)
						.build())
				// Query 2
				.dataQuery(DataQuery.builder()
						.dataSource(dataSouce())
						.key("roles")
						.query(query2)
						.build())
				.build();

		new JReport().generate(model);
	}

	@Test
	void dataSouceCustomData() {

		String query = "SELECT ID, NAME, CREATED FROM USER";
		String query2 = "SELECT ID, NAME, WEIGHT, ACTIVE FROM ROL";

		// Decimal format for decimal values
		DecimalFormat decimalFormat = DecimalFormat.builder()
				.pattern("¤#,##0.00")
				.decimalSeparator('.')
				.groupingSeparator(',')
				.currencySymbol("$").build();

		Model model = Model.builder()
				.in(FileSystems.getDefault().getPath(OTD_PATH))
				.inName("datasource3")
				// Date param
				.valueData("date", LocalDate.now().toString())
				// Query 1
				.dataQuery(DataQuery.builder()
						.key("users")
						.dataSource(dataSouce())
						// For the CREATED value, we define it to be formatted as a date with its
						// respective pattern."
						.dateFormat("CREATED", "dd/MM/yyyy HH:mm a")
						// If you have a CLOB or similar text, you can use the .htmlField property to
						// have the HTML casted.
						// .htmlField(new HTMLField("user.NOTE"))
						.query(query)
						.build())
				// Query 2
				.dataQuery(DataQuery.builder()
						.key("roles")
						.query(query2)
						.dataSource(dataSouce())
						// For the WEIGHT value, we define it to be formatted as a decimal with its
						// respective format."
						.decimalFormat("WEIGHT", decimalFormat)
						// ACTIVE will be represented as a boolean (defaulting to "YES" and "NO")
						// You can use ".booleanValues("SI", "NO")" to change the default value.
						// .booleanValues("Yes", "No")
						.booleanFormat("ACTIVE")
						.build())
				.build();

		new JReport().generate(model);
	}
}