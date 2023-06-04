package org.jasa.jreport.core;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class with the required model to process and generate a {@link JReport}. In
 * this class, the data loading and various properties for the execution of the
 * {@link JReport} are defined."
 * 
 * @author Leonardo Sanchez J.
 */
public class Model {

	private final static String ODT = ".odt";
	private final static String PDF = ".pdf";

	private Path in;
	private Path out;
	private String inName;
	private String outName;

	private List<DataQuery> dataQuery;
	private List<DataList> dataList;
	private Map<String, String> valueData;

	private Model() {
		dataQuery = new ArrayList<>();
		dataList = new ArrayList<>();
		valueData = new HashMap<>();
	}

	public Path getIn() {
		return in;
	}

	public String getInName() {
		return inName;
	}

	public Path getOut() {
		return out;
	}

	public String getOutName() {
		return outName;
	}

	public File getInFile() {
		return (FileSystems.getDefault().getPath(in.toString(), inName + ODT)).toFile();
	}

	public File getOutFile() {
		Path des = out == null ? in : out;
		String name = outName == null ? inName : outName;
		return (FileSystems.getDefault().getPath(des.toString(), name + PDF)).toFile();
	}

	public List<DataList> getDataList() {
		return dataList;
	}

	public List<DataQuery> getDataQuery() {
		return dataQuery;
	}

	public Map<String, String> getValueData() {
		return valueData;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {

		private Model model;

		private Builder() {
			model = new Model();
		}

		/**
		 * {@link Path} with the folder location where the input file (ODT template)
		 * will be placed. Example: "/usr/local/otds"
		 * 
		 * @param in
		 * @return
		 */
		public Builder in(Path in) {
			model.in = in;
			return this;
		}

		/**
		 * Name of the input file (ODT template). Example: "mytemplate" (Don't use the
		 * extension).
		 * 
		 * @param inName
		 * @return
		 */
		public Builder inName(String inName) {
			model.inName = inName;
			return this;
		}

		/**
		 * {@link Path} with the folder location where the output file (PDF) will be
		 * generated. Example: "/usr/local/pdfs". Note: If not specified, it will be
		 * equal to {@code in}.
		 * 
		 * @param out
		 * @return
		 */
		public Builder out(Path out) {
			model.out = out;
			return this;
		}

		/**
		 * Name of the output file (PDF). Example: "mypdf" (Don't use the extension). If
		 * not specified, it will be equal to {@code inName}.
		 * 
		 * @param outName
		 * @return
		 */
		public Builder outName(String outName) {
			model.outName = outName;
			return this;
		}

		/**
		 * Add {@link DataQuery} to the {@link Model} for processing.
		 * 
		 * @param dataQuery
		 * @return
		 */
		public Builder dataQuery(DataQuery dataQuery) {
			model.dataQuery.add(dataQuery);
			return this;
		}

		/**
		 * Add {@code List<DataQuery>} to the {@link Model} for processing.
		 * 
		 * @param dataQuery {@code List<DataQuery>}
		 * @return
		 */
		public Builder dataQuery(List<DataQuery> dataQuery) {
			if (dataQuery != null)
				model.dataQuery.addAll(dataQuery);
			return this;
		}

		/**
		 * Add {@link DataList} to the {@link Model} for processing.
		 * 
		 * @param dataList
		 * @return
		 */
		public Builder dataList(DataList dataList) {
			model.dataList.add(dataList);
			return this;
		}

		/**
		 * Add {@code List<DataList>} to the {@link Model} for processing.
		 * 
		 * @param dataList {@code List<DataList>}
		 * @return
		 */
		public Builder dataList(List<DataList> dataList) {
			if (dataList != null)
				model.dataList.addAll(dataList);
			return this;
		}

		/**
		 * Add a key with its respective value to the {@link Model}.
		 * 
		 * @param key
		 * @param value
		 * @return
		 */
		public Builder valueData(String key, String value) {
			model.valueData.put(key, value);
			return this;
		}

		/**
		 * Add a {@code Map<String, String> valueData} to the {@link Model}.
		 * 
		 * @param valueData {@code Map<String, String>}
		 * @return
		 */
		public Builder valueData(Map<String, String> valueData) {
			if (valueData != null)
				model.valueData.putAll(valueData);
			return this;
		}

		public Model build() {
			return model;
		}
	}
}