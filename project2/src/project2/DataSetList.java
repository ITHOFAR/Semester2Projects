package project2;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Used to store all the DataSet objects, has a default constructor only. 
 * Extends ArrayList<DataSet>
 * Contains three methods to search and sort a DataSetList.
 * Contains a private class for a comparator.
 * @author ithofar
 * @version 03/06/2021
 */
public class DataSetList extends ArrayList<DataSet> {

	private static final long serialVersionUID = 1L;

	/**
	 * private comparator to be used to sort using compareTo from DataSet class
	 * @author ithofar
	 * @version 03/07/2021
	 */
	private class DataSetComparator implements Comparator<DataSet> {
		@Override
		public int compare(DataSet ds1, DataSet ds2) {
			return ds1.compareTo(ds2);
		}
	}

	/**
	 * Returns a DataSetList containing keyword in Title and sorted by compareTo in DataSet class
	 * @param keyword keyword we want our DataSet to contain
	 * @return returns DataSetList containing keyword and sorted
	 * @throws IllegalArgumentException throws exception if keyword is null or empty
	 */
	public DataSetList getByTitle(String keyword) throws IllegalArgumentException{

		//checking invalid arguments
		if (keyword == null || keyword.length() == 0) {
			throw new IllegalArgumentException("Keyword is null or empty");
		}

		try {
			if (this.isEmpty() == true) {
				return null;
			}
		}
		catch(NullPointerException e) {
			return null;
		}

		DataSetList output = new DataSetList();
		//adding DataSets that contain keyword, not sorted
		for (DataSet ds: this) {
			String title = ds.getTitle();
			if (title == null) continue;
			if (title.toLowerCase().contains(keyword.toLowerCase())) {
				output.add(ds);
			}
		}
		//using a comparator to sort using compareTo from DataSet class
		Collections.sort(output, new DataSetComparator());
		if (output.isEmpty() == true) {
			return null;
		}
		return output;
	}

	/**
	 * Returns a DataSetList containing keyword in description and sorted by compareTo in DataSet class
	 * @param keyword keyword we want our DataSet to contain
	 * @return returns DataSetList containing keyword and sorted
	 * @throws IllegalArgumentException throws exception if keyword is null or empty
	 */
	public DataSetList getByDescription(String keyword) throws IllegalArgumentException{

		if (keyword == null || keyword.length() == 0) {
			throw new IllegalArgumentException("Keyword is null or empty");
		}

		try {
			if (this.isEmpty() == true) {
				return null;
			}
		}
		catch(NullPointerException e) {
			return null;
		}

		DataSetList output = new DataSetList();

		//adding DataSets that contain keyword, not sorted
		for (DataSet ds: this) {
			String description = ds.getDescription();
			if (description == null) continue;
			if (description.toLowerCase().contains(keyword.toLowerCase())) {
				output.add(ds);
			}
		}
		//using a comparator to sort using compareTo from DataSet class
		Collections.sort(output, new DataSetComparator());
		if (output.isEmpty() == true) {
			return null;
		}
		return output;
	}

	/**
	 * Returns a DataSetList containing keyword in links and sorted by compareTo in DataSet class
	 * @param keyword keyword we want our DataSet to contain
	 * @return returns DataSetList containing keyword and sorted
	 * @throws IllegalArgumentException throws exception if keyword is null or empty
	 */
	public DataSetList getByURL(String keyword) throws IllegalArgumentException{

		if (keyword == null || keyword.length() == 0) {
			throw new IllegalArgumentException("Keyword is null or empty");
		}

		try {
			if (this.isEmpty() == true) {
				return null;
			}
		}
		catch(NullPointerException e) {
			return null;
		}

		DataSetList output = new DataSetList();

		//adding DataSets that contain keyword, not sorted
		for (DataSet ds: this) {
			ArrayList<URL> urls = ds.getLinks();
			for (URL value: urls) {
				String urlVal = value.toString();
				if (urlVal == null) continue;
				if (urlVal.toLowerCase().contains(keyword.toLowerCase())) {
					output.add(ds);
				}
			}

		}
		//using a comparator to sort using compareTo from DataSet class
		Collections.sort(output, new DataSetComparator());
		if (output.isEmpty() == true) {
			return null;
		}
		return output;
	}


}
