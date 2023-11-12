package com.bitcoinprice.dataparsing.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

import org.apache.http.NameValuePair;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

public class Requests {

	
	@Deprecated //honestly can't remember what this does, think it does post request and prints
	public String getRequest(String link) throws UnsupportedOperationException, IOException {
		URL url = new URL(link);
		URLConnection connection = url.openConnection();

		String result = "";
		try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			String line;
			while ((line = in.readLine()) != null) {
//				System.out.println(line);
				if (line != null && !line.isEmpty())
				result += line;
			}
		}
//		System.out.println(result);
		return result;
	}

	public static String sendPOST(String url, List<NameValuePair> httpHeaders) throws IOException {
		return null;
	}
}
