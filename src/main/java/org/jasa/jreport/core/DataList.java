package org.jasa.jreport.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for handling data list type. For each {@link DataList} in the
 * {@link JReport}, a unique key, a data list source, and the corresponding
 * class for the objects in that collection should be assigned.
 * 
 * @author Leonardo Sanchez J.
 */
public class DataList {

	private String key;
	private List data;
	private Class clazz;

	private DataList() {
		this.data = new ArrayList();
	}

	public String getKey() {
		return key;
	}

	public List getData() {
		return data;
	}

	public Class getClazz() {
		return clazz;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {

		private DataList dataList;

		private Builder() {
			dataList = new DataList();
		}

		/**
		 * Unique key to represent the data list.
		 * 
		 * @param key
		 * @return
		 */
		public Builder key(String key) {
			dataList.key = key;
			return this;
		}

		/**
		 * Data source to process.
		 * 
		 * @param data
		 * @return
		 */
		public Builder data(List data) {
			if (data != null)
				dataList.data.addAll(data);
			return this;
		}

		/**
		 * Type of the data source class.
		 * 
		 * @param clazz
		 * @return
		 */
		public Builder clazz(Class clazz) {
			dataList.clazz = clazz;
			return this;
		}

		public DataList build() {
			return dataList;
		}
	}
}