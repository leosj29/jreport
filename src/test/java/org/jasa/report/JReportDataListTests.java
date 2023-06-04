package org.jasa.report;

import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jasa.jreport.JReportApplication;
import org.jasa.jreport.core.DataList;
import org.jasa.jreport.core.JReport;
import org.jasa.jreport.core.Model;
import org.jasa.report.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test for JReport with data lists values.
 * 
 * @author Leonardo Sanchez J.
 */
@SpringBootTest(classes = JReportApplication.class)
class JReportDataListTests {

	private final String OTD_PATH = (FileSystems.getDefault().getPath(System.getProperty("user.dir"), "odts"))
			.toString();

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

	@Test
	void dataList() {

		Model model = Model.builder()
				.in(FileSystems.getDefault().getPath(OTD_PATH))
				.inName("datalist")
				.valueData("date", LocalDate.now().toString())
				.dataList(DataList.builder()
						.key("characters")
						.data(characters())
						.clazz(Person.class)
						.build())
				.build();

		new JReport().generate(model);
	}
}