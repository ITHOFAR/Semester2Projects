package project2;
import java.net.URL;
import java.util.ArrayList;

/**
 * Represents a data set with a date, title/headline, description/text, list of links, and hatTip if applicable.
 * Implements comparable<DataSet>.
 * Contains methods to provide formatted String, compare, and check equality.
 * @author ithofar
 *@version 03/06/2021
 */
public class DataSet implements Comparable<DataSet>{
	private String title;
	private String description;
	private ArrayList<URL> links;
	private Date date;
	private String hatTips;

	/**
	 * Constructs a dataSet with a title, description, and links while checking if they are valid.
	 * @param title title should be non empty and not null
	 * @param description description should be non empty and not null
	 * @param links should be non empty and not null
	 * @throws IllegalArgumentException if any of the above are empty or null
	 */
	public DataSet(String title, String description, ArrayList<URL> links) throws IllegalArgumentException{
		if (title.length() == 0 || title == null) {
			throw new IllegalArgumentException("Title is not valid as it is empty");
		}
		if (description.length() == 0 || description == null) {
			throw new IllegalArgumentException("Description is not valid as it is empty");
		}
		if (links.size() == 0 || links == null) {
			throw new IllegalArgumentException("Links is not valid as it is empty");
		}
		this.title = title;
		this.description = description;
		this.links = links;
	}

	/**
	 * returns title of this object
	 * @return returns String of title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * returns description of this object
	 * @return returns String of description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * returns ArrayList<links> of this object
	 * @return returns ArrayList of URLS
	 */
	public ArrayList<URL> getLinks() {
		return links;
	}


	/**
	 * sets date based on input date while checking if valid
	 * @param date date object to set in DatesSet object
	 */
	public void setDate(Date date) {
		if (date != null && date.getYear() >= 2000) {
			this.date = date;
		}
		else {
			System.err.println("Error: Year is invalid, year should be greater than 1999");
		}
	}

	/**
	 * returns date object in this object
	 * @return returns date object
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * sets hatTips in DataSet object 
	 * If null, sets it to an empty string, otherwise to the value passed in.
	 * @param hatTips hatTips to be set in DataSet object
	 */
	public void setHatTips(String hatTips) {
		if (hatTips != null) {
			this.hatTips = hatTips;
		}
		else {
			this.hatTips = "";
		}
	}

	/**
	 * returns hatTips in object
	 * If hatTips is empty or null, returns an empty string.
	 * @return returns hatTips
	 */
	public String getHatTips() {
		if (this.hatTips == null || this.hatTips.length() == 0) {
			return "";
		}
		return this.hatTips;
	}

	/**
	 * compares DataSet object with another DataSet object first on date then on title if date is the same or null
	 * @param ds DataSet to be compared with
	 * @return int returns a negative, 0, or positive integer to show less, equal, or greater than
	 */
	@Override
	public int compareTo(DataSet ds) {
		if (this.date == null || ds.date == null || this.date.compareTo(ds.date) == 0) {
			return this.title.compareToIgnoreCase(ds.title);
		}
		else {
			return this.date.compareTo(ds.date);
		}

	}

	/**
	 * checks if both DataSets are equal based on title and date
	 * Has a check to see if dates are null. 
	 * @param takes in object to compare to current object
	 * @return returns true if equal, false if not
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;

		if (obj == null) return false;

		if (!(obj instanceof DataSet)) return false;

		DataSet other = (DataSet) obj;
		if (this.date != null && other.date != null) {
			if ((this.date.compareTo(other.date) != 0) || (this.title.compareToIgnoreCase(other.title) != 0)) return false;
		}
		else if (this.title.compareToIgnoreCase(other.title) != 0) {
			return false;
		}
		return true;
	}

	/**
	 * returns multi-line string containing date, name, description, and links
	 * @return returns a multi-line string containing date, name, description, and links
	 */
	@Override
	public String toString() {
		ArrayList<String> linkStringList = new ArrayList<String>();
		for (URL urls: this.links) {
			linkStringList.add(urls.toString());
		}
		if (this.date == null) {
			String output = this.title + "\n" + this.description + "\n";
			for (String val: linkStringList) {
				output += val + "\n";
			}
			return output;
		}
		else {
			String output = this.date + "\n" + this.title + "\n" + this.description + "\n";
			for (String val: linkStringList) {
				output += val + "\n";
			}
			return output;
		}
	}


}
