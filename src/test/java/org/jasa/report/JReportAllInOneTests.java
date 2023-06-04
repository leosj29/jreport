package org.jasa.report;

import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.jasa.jreport.JReportApplication;
import org.jasa.jreport.core.DataList;
import org.jasa.jreport.core.DataQuery;
import org.jasa.jreport.core.JReport;
import org.jasa.jreport.core.Model;
import org.jasa.jreport.util.DecimalFormat;
import org.jasa.report.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import oracle.jdbc.pool.OracleDataSource;

/**
 * Test for JReport with all datas.
 * 
 * @author Leonardo Sanchez J.
 */
@SpringBootTest(classes = JReportApplication.class)
class JReportAllInOneTests {

	private final String OTD_PATH = (FileSystems.getDefault().getPath(System.getProperty("user.dir"), "odts"))
			.toString();

	private final String DB_URL = "YOUR DB_URL";
	private final String DB_USER = "YOUR DB_USER";
	private final String DB_PASS = "YOUR DB_PASS";

	private List<Person> characters() {
		List<Person> characters = new ArrayList<Person>();
		characters.add(new Person("Simon Petrikov", "Ice King", true));
		characters.add(new Person("Finn Mertens", "Finn the Human", true));
		characters.add(new Person("Jake", "Jake the Dog", true));
		characters.add(new Person("Bonnibel Bubblegum", "Princess Bubblegum", true));
		characters.add(new Person("BMO", "BMO", true));
		characters.add(new Person("Lady Rainicorn", "Lady Rainicorn", true));
		characters.add(new Person("Phoebe", "Flame Princess", true));
		characters.add(new Person("Gunter", "Gunter", true));
		return characters;
	}

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
	void allInOne() {

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
				.inName("allinone")
				// Params
				.valueData("name1", "Finn the human")
				.valueData("name2", "Jake the dog")
				.valueData("date", LocalDate.now().toString())
				// List Data
				.dataList(DataList.builder()
						.key("characters")
						.data(characters())
						.clazz(Person.class)
						.build())
				// Queries
				.dataQuery(DataQuery.builder()
						.dataSource(dataSouce())
						.key("users")
						.dateFormat("CREATED", "dd/MM/yyyy HH:mm a")
						.query(query)
						.build())
				.dataQuery(DataQuery.builder()
						.dataSource(dataSouce())
						.key("roles")
						.query(query2)
						.decimalFormat("WEIGHT", decimalFormat)
						.booleanValues("SI", "NO")
						.booleanFormat("ACTIVE")
						.build())
				.build();

		new JReport().generate(model);
	}
}