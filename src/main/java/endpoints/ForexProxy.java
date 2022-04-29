package endpoints;

import java.util.Scanner;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForexProxy {

	public static void main(String[] args) {
		try (Scanner s = new Scanner(System.in)) {
			runFirstApiProcess(s);

			System.out.println();
			System.out.println();

			runSecondApiProcess(s);
		}
	}


	// ---------------------------------- API PROCESS 1 --------------------------------------------------
	public static void runFirstApiProcess(Scanner s) {
		System.out.println("Using Currency Exchange API...");

		System.out.print("Enter the to currency: ");

		String to = s.next();

		System.out.print("Enter the from currency: ");

		String from = s.next();

		System.out.print("Enter amount: ");

		Double amount = s.nextDouble();

		Double exchangeRate = getCurrencyExchangeApiData(to, from, amount);

		System.out.println("Exchange rate = " + exchangeRate);    

		Double convertedAmount = calculateCurrencyExchange(amount, exchangeRate);

		System.out.println(from + amount + " is equivalent to " + to + convertedAmount);    
	}	

	public static Double getCurrencyExchangeApiData(final String to, final String from, final Double amount) {
		Request request = buildCurrencyExchangeApiRequest(to, from, amount);

		OkHttpClient client = new OkHttpClient();

		try {
			Response response = client.newCall(request).execute();

			String result = response.body().string();

			Double exchangeRate = Double.parseDouble(result);

			return exchangeRate;
		}catch(Exception e) {
			System.out.print("An exception has occured. Message: " + e.getMessage());
			return null;
		}
	}

	public static Double calculateCurrencyExchange(Double amount, Double exchangeRate) {
		return amount * exchangeRate;	
	}


	public static Request buildCurrencyExchangeApiRequest(final String to, final String from, final Double amount) {
		final String xRapidAPIKey = "c05e3b63e2msh2be8a3bf4301c53p1d7522jsn8377df478313";
		final String xRapidAPIHost = "currency-exchange.p.rapidapi.com";
		final String targetUrl = "https://currency-exchange.p.rapidapi.com/exchange";

		return new Request.Builder()
				.url(targetUrl + "?to=" + to + "&from=" + from + "&q=" + amount)
				.get()
				.addHeader("x-rapidapi-key", xRapidAPIKey)
				.addHeader("x-rapidapi-host", xRapidAPIHost)
				.build();
	}



	// ---------------------------------- API PROCESS 2 --------------------------------------------------

	public static void runSecondApiProcess(Scanner s) {
		try {
			System.out.println("Using Currency Scoop API...");

			System.out.print("Enter the currency: ");

			String currency = s.next();

			System.out.print("Enter the amount in USD: ");

			Double amount = s.nextDouble();		
			Double exchangeRate = getCurrencyScoopData(currency);

			Double convertedCurrency = convertCurrency(amount, exchangeRate);

			System.out.print("Currency in " + currency + " = " + convertedCurrency);

		}catch(Exception e) {
			System.out.print("An exception has occured. Message: " + e.getMessage());
		}
	}

	public static Request buildCurrencyScoopApiRequest() {
		final String xRapidAPIKey = "5c9d61451bmshe993566d3bfb3e0p186d51jsn3ea21b3d8a4e";
		final String xRapidAPIHost = "currencyscoop.p.rapidapi.com";
		final String targetUrl = "https://currencyscoop.p.rapidapi.com/latest";

		return new Request.Builder()
				.url(targetUrl)
				.get()
				.addHeader("x-rapidapi-key", xRapidAPIKey)
				.addHeader("x-rapidapi-host", xRapidAPIHost)
				.build();
	}

	public static Double getCurrencyScoopData(String currency) {
		Request request = buildCurrencyScoopApiRequest();

		OkHttpClient client = new OkHttpClient();

		try {
			Response response = client.newCall(request).execute();

			String result = response.body().string();

			JSONObject jsonResponse = new JSONObject(result);

			JSONObject responseJsonObj = jsonResponse.getJSONObject("response");
			JSONObject ratesJsonObj = responseJsonObj.getJSONObject("rates");

			return ratesJsonObj.getDouble(currency);
		}catch(Exception e) {
			System.out.print("An exception has occured. Message: " + e.getMessage());
			return null;
		}
	}

	public static Double convertCurrency(Double amount, Double exchangeRate) {
		return amount * exchangeRate;
	}

}
