package com.csvreader;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;
/**
 * TODO-not complete
 * @author prianka 
 *
 */
public class SomeCoolCsvReaderTest extends TestCase{
	private SomeMayBeCoolCsvReader csvReader;
	private Person person;

	
	@Before
	public void setUp() throws Exception {
		File file = new File("test");
		person = new Person();
		csvReader = new SomeMayBeCoolCsvReader(file,person);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
	}

}
