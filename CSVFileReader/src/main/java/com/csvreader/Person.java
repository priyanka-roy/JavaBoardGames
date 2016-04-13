package com.csvreader;

@FileMetaData(separator = ",")
public class Person {
	@CsvColumn(indx = 1)
	private String firstName;
	
	@CsvColumn(indx = 2)
	private String lastName;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
