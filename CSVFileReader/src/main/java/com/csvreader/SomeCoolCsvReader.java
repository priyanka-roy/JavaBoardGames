package com.csvreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class SomeCoolCsvReader {
		     
	private List<Person> persons;
	// Delimiter used in CSV file
	private static final String FileMetaData_COMMA_SEPARATOR = ",";
	private static final int INDX_FIRST_NAME = 0;
	private static final int INDX_LAST_NAME = 1;
	

	public SomeCoolCsvReader(File csvFile, Person person)  {
		BufferedReader br = null;

		if (!csvFile.isFile()) {
			return;
		}

		persons = new ArrayList<Person>();
		String line = " ";
		
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
			    // use comma as separator
			    String[] cols = line.split(FileMetaData_COMMA_SEPARATOR);
			    person = new Person();
			    person.setFirstName(cols[INDX_FIRST_NAME]);
			    person.setLastName(cols[INDX_LAST_NAME]);
			    persons.add(person);
			    
			}
		} catch (IOException e) {
			try {
				br.close();
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
