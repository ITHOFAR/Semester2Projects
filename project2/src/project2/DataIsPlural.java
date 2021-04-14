package project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * This class takes user input for a file and parses through the provided CSV file to obtain information about DataSets.
 * This program is interactive. The user can input a title, header, and/or links.
 * Contains a method to remove duplicates from a DataSetList
 * Inspired by Code from Joanna K used in Color Converter Class in her Project 1 posted on Ed
 * 
 * @author ithofar
 * @version 03/07/2021
 */
public class DataIsPlural {

	/**
	 * The main method of this program. Handles checking if file is there, valid, and readable. Handles the user interface. 
	 * @param args CSV file inputed by the user on the command line.
	 */
	public static void main(String[] args){
		//verify that the command line argument exist
		if (args.length == 0) { //TODO length != 1
			System.err.println("Usage Error: the program expects file name as an argument.\n");
			System.exit(1);
		}

		File dataFile = new File(args[0]);
		if (!(dataFile.exists())) {
			System.err.println("Error: the file " + dataFile.getAbsolutePath() + " does not exist.\n");
			System.exit(1);
		}

		if (!(dataFile.canRead())) {
			System.err.println("Error: the file " + dataFile.getAbsolutePath() + " cannot be opened for reading.\n");
			System.exit(1);
		}

		//opening the file for reading
		Scanner inData = null;

		try {
			inData = new Scanner(dataFile, "UTF-8");
		} 
		catch(FileNotFoundException e) {
			System.err.println("Error: the file " + dataFile.getAbsolutePath() + " cannot be opened for reading.\n");
			System.exit(1);
		}

		//read the content of the file and save the data in a DataSetList
		DataSetList dataList = new DataSetList();
		CSV csv = new CSV(inData);

		String title = null;
		String description = null;
		String hatTips = null;
		Date date = null;
		ArrayList<URL> links = null;
		DataSet current = null;

		csv.getNextRow(); //skip header row
		for (int i = 0; i < csv.getNumOfRows(); i++) {
			try {
				//checking if in header row, if so skip it
				ArrayList<String> currentRow = csv.getNextRow();
				try {
					//seeing if date exist
					if (currentRow.get(0) != null) {
						String[] dataValues = currentRow.get(0).split("\\.");
						date = new Date(Integer.parseInt(dataValues[0]), Integer.parseInt(dataValues[1]), Integer.parseInt(dataValues[2]));
					}
				} 
				catch(Exception e) {
					//do nothing and continue to next
				}
				try {
					//seeing if hatTips exist
					if (currentRow.get(5) != null) {
						hatTips = currentRow.get(5);
					}
				} 
				catch(Exception ex) {
					//do nothing and continue to next
				}
				//TODO revert back
				title = currentRow.get(2);
				description = currentRow.get(3);
				//Supported by all operating systems for splitting by new line character
				String[] stringLinks = currentRow.get(4).split("\\r?\\n");
				links = new ArrayList<URL>();
				for (String value: stringLinks) {
					links.add(new URL(value));
				}
			}
			catch (NoSuchElementException ex) {
				System.err.println("Error: incomplete or missformated line in input file");
			}
			catch (MalformedURLException er) {
				System.err.println("URL is invalid");
			} 
			catch (IllegalArgumentException err) {
				System.err.println(err);
			}

			try {
				current = new DataSet(title, description, links);
				current.setDate(date);
				current.setHatTips(hatTips);
				dataList.add(current);
			}
			catch(IllegalArgumentException erro) {
				System.err.println(erro);
			}
			catch(NullPointerException error) {
				//do nothing and continue to next
			}
		} 

		//Interactive Mode
		Scanner userInput = new Scanner(System.in);
		String userValue = "";
		//Welcome message for launch of program
		System.out.println("Welcome to the Data is Plural data explorer!" + "\n");
		System.out.println("You can use the following queries to search through the data:" + "\n"
				+ "    " + "title KEYWORD" + "\n" + "    " + "description KEYWORD" + "\n" + "    " + "url KEYWORD");
		System.out.println("You can comebine up to two queries to narrow down the results, for example:" + "\n" +
				"    " + "title KEYWORD1" + "  " + "url KEYWORD2" + "\n" + "\n");

		do {
			System.out.println("Enter query or \"quit\" to stop");
			//Getting user input
			userValue = userInput.nextLine();
			DataSetList outputList = null;

			if (!(userValue.equalsIgnoreCase("quit"))) {

				try {
					String[] splitUserValue = userValue.split(" ");

					//handling when more than 2 queries
					if (splitUserValue.length >= 5) { 
						System.out.println("This is not a valid query. Try again."); 
						continue;
					}

					//handing title first
					if (splitUserValue[0].equalsIgnoreCase("title") && splitUserValue.length >= 2) {
						//handling when second query is invalid
						if (splitUserValue.length == 3) {
							System.out.println("This is not a valid query. Try again."); 
							continue;
						}
						if (splitUserValue.length == 4 && !(splitUserValue[2].equalsIgnoreCase("description") || splitUserValue[2].equalsIgnoreCase("url"))) {
							System.out.println("This is not a valid query. Try again.");
							continue;
						}
						outputList = dataList.getByTitle(splitUserValue[1]);

						//handling second query if there and when its the description
						if (splitUserValue.length == 4  && splitUserValue[2].equalsIgnoreCase("description")) {
							outputList = outputList.getByDescription(splitUserValue[3]);

							if (outputList == null || outputList.isEmpty()) {
								System.out.println("No matches found. Try again.");
								continue;
							}
							outputList = removeDuplicates(outputList);
							for (DataSet ds: outputList) {
								System.out.println(ds.toString() + "\n" + "-----");
							}
							continue;
						}

						//handling second query if there and it is for links
						if (splitUserValue.length == 4 && splitUserValue[2].equalsIgnoreCase("url")) {
							outputList = outputList.getByURL(splitUserValue[3]);

							if (outputList == null || outputList.isEmpty()) {
								System.out.println("No matches found. Try again.");
								continue;
							}
							outputList = removeDuplicates(outputList);
							for (DataSet ds: outputList) {
								System.out.println(ds.toString() + "\n" + "-----");
							}
							continue;
						}

						if (outputList == null || outputList.isEmpty()) {
							System.out.println("No matches found. Try again.");
							continue;
						}
						outputList = removeDuplicates(outputList);
						for (DataSet ds: outputList) {
							System.out.println(ds.toString() + "\n" + "-----");
						}
						continue;
					}

					//handing description first
					if (splitUserValue[0].equalsIgnoreCase("description") && splitUserValue.length >= 2) {

						if (splitUserValue.length == 3) {
							System.out.println("This is not a valid query. Try again."); 
							continue;
						}
						if (splitUserValue.length == 4 && !(splitUserValue[2].equalsIgnoreCase("title") || splitUserValue[2].equalsIgnoreCase("url"))) {
							System.out.println("This is not a valid query. Try again.");
							continue;
						}
						outputList = dataList.getByDescription(splitUserValue[1]);

						//handling second query if there and it is for title
						if (splitUserValue.length == 4 && splitUserValue[2].equalsIgnoreCase("title")) {
							outputList = outputList.getByTitle(splitUserValue[3]);

							if (outputList == null || outputList.isEmpty()) {
								System.out.println("No matches found. Try again.");
								continue;
							}
							outputList = removeDuplicates(outputList);
							for (DataSet ds: outputList) {
								System.out.println(ds.toString() + "\n" + "-----");
							}
							continue;
						}

						//handling second query if there and is it for links
						if (splitUserValue.length == 4 && splitUserValue[2].equalsIgnoreCase("url")) {
							outputList = outputList.getByURL(splitUserValue[3]);

							if (outputList == null || outputList.isEmpty()) {
								System.out.println("No matches found. Try again.");
								continue;
							}
							outputList = removeDuplicates(outputList);
							for (DataSet ds: outputList) {
								System.out.println(ds.toString() + "\n" + "-----");
							}
							continue;
						}

						if (outputList == null || outputList.isEmpty()) {
							System.out.println("No matches found. Try again.");
							continue;
						}
						outputList = removeDuplicates(outputList);
						for (DataSet ds: outputList) {
							System.out.println(ds.toString() + "\n" + "-----");
						}
						continue;
					}

					//handing url first
					if (splitUserValue[0].equalsIgnoreCase("url") && splitUserValue.length >= 2) {

						if (splitUserValue.length == 3) {
							System.out.println("This is not a valid query. Try again."); 
							continue;
						}
						if (splitUserValue.length == 4 && !(splitUserValue[2].equalsIgnoreCase("description") || splitUserValue[2].equalsIgnoreCase("title"))) {
							System.out.println("This is not a valid query. Try again.");
							continue;
						}
						outputList = dataList.getByURL(splitUserValue[1]);

						//handling second query if there and it's description
						if (splitUserValue.length == 4 && splitUserValue[2].equalsIgnoreCase("description")) {
							outputList = outputList.getByDescription(splitUserValue[3]);

							if (outputList == null || outputList.isEmpty()) {
								System.out.println("No matches found. Try again.");
								continue;
							}
							outputList = removeDuplicates(outputList);
							for (DataSet ds: outputList) {
								System.out.println(ds.toString() + "\n" + "-----");
							}
							continue;
						}

						//handling second query if there and it's title
						if (splitUserValue.length == 4 && splitUserValue[2].equalsIgnoreCase("title")) {
							outputList = outputList.getByTitle(splitUserValue[3]);

							if (outputList == null || outputList.isEmpty()) {
								System.out.println("No matches found. Try again.");
								continue;
							}
							outputList = removeDuplicates(outputList);
							for (DataSet ds: outputList) {
								System.out.println(ds.toString() + "\n" + "-----");
							}
							continue;
						}

						if (outputList == null || outputList.isEmpty()) {
							System.out.println("No matches found. Try again.");
							continue;
						}
						outputList = removeDuplicates(outputList);
						for (DataSet ds: outputList) {
							System.out.println(ds.toString() + "\n" + "-----");
						}
						continue;
					}
					//handling when cases fail as it does not fit any valid pattern
					else {
						System.out.println("This is not a valid query. Try again.");
					}

				} 
				//NullPointerException caught if a value was not initialized, meaning nothing triggered it to initialize so nothing matched it.
				catch(NullPointerException err) {
					System.out.println("No matches found. Try again.");
					continue;
				}
				catch(IllegalArgumentException ex) {
					System.err.println(ex);
					continue;
				}
				catch(Exception e) {
					System.err.println(e);
					continue;
				} 
			}

		}
		while (!userValue.equalsIgnoreCase("quit")); 
		userInput.close(); 
	} 
	/**
	 * Used to remove duplicates from a DataSetList
	 * @param dsl DataSetList that we will remove duplicates from
	 * @return returns a DataSetList with no duplicates
	 */
	public static DataSetList removeDuplicates(DataSetList dsl) {
		DataSetList output = new DataSetList();
		for (DataSet ds: dsl) {
			if (!output.contains(ds)) {
				output.add(ds);
			}
		}
		return output;
	}

}
