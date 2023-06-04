package org.jasa.report;

import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.jasa.jreport.JReportApplication;
import org.jasa.jreport.core.JReport;
import org.jasa.jreport.core.Model;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test for JReport with params values.
 * 
 * @author Leonardo Sanchez J.
 */
@SpringBootTest(classes = JReportApplication.class)
class JReportParamTests {

	private final String OTD_PATH = (FileSystems.getDefault().getPath(System.getProperty("user.dir"), "odts"))
			.toString();

	@Test
	void params1() {
		Model model = Model.builder()
				.in(FileSystems.getDefault().getPath(OTD_PATH))
				.inName("params")
				.valueData("name1", "Finn the human")
				.valueData("name2", "Jake the dog")
				.valueData("date", LocalDate.now().toString())
				.build();

		new JReport().generate(model);
	}

	@Test
	void params2() {

		Map<String, String> params = new HashMap<>();
		params.put("name1", "Finn the human");
		params.put("name2", "Jake the dog");

		Model model = Model.builder()
				.in(FileSystems.getDefault().getPath(OTD_PATH))
				.inName("params")
				.outName("params2")
				.valueData(params)
				.valueData("date", LocalDate.now().toString())
				.build();

		new JReport().generate(model);
	}
}