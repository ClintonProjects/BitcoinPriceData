package com.example.demo;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.bitcoinprice.dataparsing.requests.NotePad;

public class NotepadTest {

	@Test
	public void testNotePad() throws IOException {
		// Note pad file test.
		assertTrue(new NotePad().read("TestFile").contains("TestFile"));
		// wrie test
		new NotePad().writeData("TestFile", "TestFile2");
		assertTrue(new NotePad().read("TestFile").contains("TestFile2"));
		//rEeset the data in the file.
		new NotePad().writeData("TestFile", "TestFile");
	}

}
