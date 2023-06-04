package org.jasa.jreport.util;

import java.util.Arrays;
import java.util.List;

/**
 * Class that will store data from Clob or similar type queries, which
 * contain HTML code and will be formatted as such. Example:
 * "user.NOTE"
 * 
 * @author Leonardo Sanchez J.
 */
public class HTMLField {

	private List<String> htmlFileds;

	/**
	 * Stores the corresponding keys passed from a list.
	 * 
	 * @param htmlFileds List with keys.
	 */
	public HTMLField(List<String> htmlFileds) {
		this.htmlFileds = htmlFileds;
	}

	/**
	 * Stores the corresponding keys passed from a String.
	 * 
	 * @param htmlFileds Key.
	 */
	public HTMLField(String... htmlFileds) {
		this.htmlFileds = Arrays.asList(htmlFileds);
	}

	public List<String> getHtmlFileds() {
		return this.htmlFileds;
	}
}