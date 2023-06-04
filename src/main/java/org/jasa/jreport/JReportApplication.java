package org.jasa.jreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author Leonardo Sanchez J.
 */
@SpringBootApplication
@ComponentScan("org.jasa.jreport")
public class JReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(JReportApplication.class, args);
	}

}
