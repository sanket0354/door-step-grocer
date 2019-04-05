package com.doorstep.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;

public class ExchangeRate {

	private double getRate(String from, String to) {
		try {
			URL url = new URL("http://quote.yahoo.com/d/quotes.csv?f=l1&s=" + from + to + "=X");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = reader.readLine();
			if (line.length() > 0) {
				return Double.parseDouble(line);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return 0;
	}

	public double getEuroUsdRate() {
		return getRate("EUR", "USD");
	}

	public double getUsdEuroRate() {
		return getRate("USD", "EUR");
	}

	public double convertEuroToUsd(double price) {
		DecimalFormat df = new DecimalFormat("#.0");

		double rate = getEuroUsdRate();
		return Double.parseDouble(df.format(price * rate));

	}

	public double convertUsdToEuro(double price) {
		DecimalFormat df = new DecimalFormat("#.0");

		double rate = getUsdEuroRate();
		return Double.parseDouble(df.format(price * rate));

	}

	// public static void main(String args[]) {
	// ExchangeRate exchange = new ExchangeRate();
	//
	// double rateGold = exchange.getEuroUsdRate();
	// System.out.println("CAD/USD: " + rateGold);
	//
	// double rateUsd = exchange.getUsdEuroRate();
	// System.out.println("USD/CAD: " + rateUsd);
	// }

}