package com.csvreader;

import java.io.File;
import java.util.List;

public class Application {

	public static void main(String[] args) {
		File file = new File("d://files//persons.csv");
		Person person = new Person();

		SomeMayBeCoolCsvReader csvReader1 = new SomeMayBeCoolCsvReader(file, person);
		List<Person> personsList1 = csvReader1.getPersons();
		for(Person eachPerson :personsList1){
			System.out.println("SomeCoolCsvReader :"+eachPerson.getFirstName()+" "+ eachPerson.getLastName());
		}
		System.out.println("\n");
		
		
		SomeCoolCsvReader csvReader2 = new SomeCoolCsvReader(file, person);
		List<Person> personsList2 = csvReader2.getPersons();
		for(Person eachPerson :personsList2){
			System.out.println("SomeMayBeCoolCsvReader :"+eachPerson.getFirstName()+" " +eachPerson.getLastName());
		}
		
	}

}
