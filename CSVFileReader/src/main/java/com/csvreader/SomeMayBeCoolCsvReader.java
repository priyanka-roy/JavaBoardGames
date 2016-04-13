package com.csvreader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class SomeMayBeCoolCsvReader {

	private List<Person> persons;
	// Delimiter used in CSV file
	private static final String FileMetaData_COMMA_SEPARATOR = ",";

	private static final String FIRST_NAME_INDX = "firstName";

	private static final String LAST_NAME_INDX = "lastName";

	public SomeMayBeCoolCsvReader(File csvFile, Person person) {
		FileReader fileReader = null;

		if (!csvFile.isFile()) {
			return;
		}

		persons = new ArrayList<Person>();

		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FIRST_NAME_INDX, LAST_NAME_INDX);

		CSVParser csvFileParser = null;
		List csvRecords;
		try {
			fileReader = new FileReader(csvFile);
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			csvRecords = csvFileParser.getRecords();
			for (int i = 0; i < csvRecords.size(); i++) {

				CSVRecord record = (CSVRecord) csvRecords.get(i);

				// Create a new person object and fill his data

				person = new Person();
				person.setFirstName(record.get(FIRST_NAME_INDX));
				person.setLastName(record.get(LAST_NAME_INDX));
				persons.add(person);

			}
		} catch (IOException e) {
			try {
				fileReader.close();
				csvFileParser.close();
			} catch (IOException e1) {

				e1.printStackTrace();
			}

			e.printStackTrace();
		}

	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

}
