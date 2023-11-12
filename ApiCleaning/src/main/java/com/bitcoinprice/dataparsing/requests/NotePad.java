package com.bitcoinprice.dataparsing.requests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class NotePad {

	public String read(String fileName) {
		String FullString = "";
		try {
			File myObj = new File("Notepads/" + fileName + ".txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				FullString += data;
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
//		System.out.println(FullString);
		return FullString;
	}

	public void writeData(String fileName, String Write) throws IOException {
		FileWriter myWriter = new FileWriter("Notepads/" + fileName + ".txt");
		myWriter.write(Write);
		myWriter.close();
	}
}
