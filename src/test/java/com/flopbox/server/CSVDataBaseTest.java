package com.flopbox.server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.flopbox.exception.ControllerException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

public class CSVDataBaseTest {

	private final String testFile = "test.csv";
	private final String[] testHeader = { "id", "name", "lastname" };

	@Before
	public void setUp() throws Exception {
		CSVDataBase.initializeDatabase(testFile, testHeader);
	}

	@Test
	public void testConstructor() throws IOException, CsvValidationException {
		CSVDataBase db = new CSVDataBase(testFile, testHeader);
		List<String[]> row = db.getRecords();
		assertEquals(row.size(), 1);
	}

	@Test
	public void testInsertRecord() throws IOException, CsvValidationException {
		CSVDataBase db = new CSVDataBase(testFile, testHeader);
		String[] record = { "1", "Carry", "Bout" };
		db.insertRecord(record);
		List<String[]> row = db.getRecords();
		assertEquals(row.size(), 2);
	}

	@Test
	public void testUpdateRecord() throws IOException, CsvException, ControllerException {
		CSVDataBase db = new CSVDataBase(testFile, testHeader);
		String[] record = { "1", "Carry", "Bout" };
		db.insertRecord(record);
		List<String[]> row = db.getRecords();
		assertEquals(row.size(), 2);
		assertEquals(row.get(1)[1], "Carry");
		assertEquals(row.get(1)[2], "Bout");
		String[] record2 = { "1", "Tom", "Egérie" };
		db.updateRecord(record, record2, 0);
		row = db.getRecords();
		assertEquals(row.size(), 2);
		assertEquals(row.get(1)[1], "Tom");
		assertEquals(row.get(1)[2], "Egérie");
	}
}
