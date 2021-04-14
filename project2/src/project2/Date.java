package project2;

/** 
 * This class represents a valid calendar date. It stores information about year, month, and day.
 * Checks if dates are valid, and has methods to compare, check equality, and return as String in valid format.
 * @author ithofar
 * @version 03/06/2021
 */

public class Date implements Comparable<Date>{

	private int year;
	private int month;
	private int day;

	/** 
	 * Constructs a Date with specified year, month, and day
	 * @param year year should be positive and 4 digits
	 * @param month month should be positive and less than 13
	 * @param day day should be positive and upper bound varies based on month
	 * @throws IllegalArgumentException if year, month, or day are out of bound 
	 */
	public Date(int year, int month, int day) throws IllegalArgumentException{
		//Checking if year, month, and day are valid
		if (!(year > 0 && year < 10000)) {
			throw new IllegalArgumentException("Year provided is not a valid year");
		}
		if (!(month > 0 && month < 13)) {
			throw new IllegalArgumentException("Month provided is not a valid month");
		}
		//using month to check if day is valid 
		switch(month) {
		case 1:
		case 3: 
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if (!(day > 0 && day < 32)) {
				throw new IllegalArgumentException("Day provided: " + day + " is not a valid day");
			}
		case 4:
		case 6: 
		case 9:
		case 11:
			if (!(day > 0 && day < 31)) {
				throw new IllegalArgumentException("Day provided: " + day + " is not a valid day");
			}

		case 2: //leap year algorithm for Feb
			if (year % 4 != 0) {
				if (!(day > 0 && day < 29)) {
					throw new IllegalArgumentException("Day provided is not a valid day"); //common
				}
			}
			else if (year % 100 != 0) {
				if (!(day > 0 && day < 30)) {
					throw new IllegalArgumentException("Day provided is not a valid day"); //leap
				}
			}
			else if (year % 400 != 0) {
				if (!(day > 0 && day < 29)) {
					throw new IllegalArgumentException("Day provided is not a valid day"); //common
				}
			}
			else {
				if (!(day > 0 && day < 30)) {
					throw new IllegalArgumentException("Day provided is not a valid day"); //leap
				}
			}
		}
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/** Compares this object with specified object on year, month, then day
	 *  @param o the object to be compared 
	 *  @return a negative int, zero, or a positive if smaller, equal, or greater than other object
	 */
	@Override
	public int compareTo(Date o) {
		if (this.year == o.year) {
			if (this.month == o.month) {
				return (this.day - o.day);
			}
			else {
				return (this.month - o.month);
			}
		}
		else {
			return (this.year - o.year);
		}
	}

	/**
	 * Indicates whether some object obj is equivalent to this object.
	 * Two Date objects are considered the same if year, month, and day are the same
	 * @param obj obj is the object we check equivalence for
	 * @return true if argument same as current object, false if they differ
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;

		if (obj == null) return false;

		if (!(obj instanceof Date)) return false;

		Date other = (Date) obj;
		if (this.compareTo(other) != 0) return false;

		return true;
	}

	/**
	 * Returns year, month, and day in YYYY-MM-DD
	 * @return returns a String in YYYY-MM-DD format
	 */
	@Override
	public String toString() {
		//use string.format maybe TODO
		String year = String.valueOf(this.year);
		String month = String.valueOf(this.month);
		String day = String.valueOf(this.day);

		if (this.year < 10) {
			year = "000" + year;
		}
		if (this.year < 100) {
			year = "00" + year;
		}
		if (this.year < 1000) {
			year = "0" + year;
		}
		if (this.month < 10) {
			month = "0" + month;
		}
		if (this.day < 10) {
			day = "0" + day;
		}
		String output = year + "-" + month + "-" + day;
		return output;
	}

	//No setters since I don't want anyone to set a date without using the constructor
	/**
	 * Returns the year component of Date object
	 * @return the year component of Date object
	 */
	public int getYear() {
		return year;
	}
	/**
	 * Returns the month component of Date object
	 * @return the month component of Date object
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * Returns the day component of Date object
	 * @return the day component of Date object
	 */
	public int getDay() {
		return day;
	}

}
