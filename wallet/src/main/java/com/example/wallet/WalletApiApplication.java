package com.example.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@SpringBootApplication
public class WalletApiApplication {

	static String color = "\033[32m";

	public static void main(String[] args) {


		ConfigurableApplicationContext context = SpringApplication.run(WalletApiApplication.class, args);

		// User input via console
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\n=== SV's Virtual Wallet ===");
			System.out.println("1. Create Wallet");
			System.out.println("2. View Balance");
			System.out.println("3. Deposit Money");
			System.out.println("4. Withdraw Money");
			System.out.println("- - - - - - - - - - - - -");
			System.out.println("5. " + (color.equals("\033[32m") ? "Disable " : "Enable ") +  "output text-coloring");
			System.out.println("6. Exit");
			System.out.print(color + "Choose an option: \033[0m");

			String temp = scanner.nextLine();
			try {
				int choice = Integer.parseInt(temp);
				switch (choice) {
					case 1 -> createWallet(scanner);
					case 2 -> viewBalance(scanner);
					case 3 -> depositMoney(scanner);
					case 4 -> withdrawMoney(scanner);
					case 5 -> color = color.equals("\033[0m") ?  "\033[32m" : "\033[0m";
					case 6 -> {
						System.out.println(color + "Exiting...\033[0m");
						scanner.close();
						SpringApplication.exit(context);
						return;
					}
					default -> System.out.println(color + "Invalid choice. Please try again.\033[0m");
				}
			} catch (Exception e) {
				System.out.println(color + "Error: " + e.getMessage() + "\033[0m");
			}
		}
	}

	private static void createWallet(Scanner scanner) throws Exception {
		System.out.print(color + "Enter wallet ID to create: \033[0m");
		String walletId = scanner.nextLine();

		HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/wallets/create").openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");

		try (OutputStream os = connection.getOutputStream()) {
			os.write(walletId.getBytes());
		}

		readResponse(connection);

	}

	private static void viewBalance(Scanner scanner) throws Exception {
		System.out.print(color + "Enter wallet ID to view balance: \033[0m");
		String walletId = scanner.nextLine();

		HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/wallets/" + walletId + "/balance").openConnection();
		connection.setRequestMethod("GET");

		readResponse(connection);

	}

	private static void depositMoney(Scanner scanner) throws Exception {
		System.out.print(color + "Enter wallet ID: \033[0m");
		String walletId = scanner.nextLine();

		System.out.print(color + "Enter amount to deposit: \033[0m");
		String temp = scanner.nextLine();
		double amount;

		try{
			amount = Double.parseDouble(temp);

		}catch (NumberFormatException e){
			System.out.println(color + "Invalid deposit: " + e.getMessage() + "\033[0m");
			return;
		}

		HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/wallets/" + walletId + "/deposit").openConnection();
		connection.setRequestMethod("PUT");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");

		try (OutputStream os = connection.getOutputStream()) {
			os.write(Double.toString(amount).getBytes());
		}

		readResponse(connection);

	}

	private static void withdrawMoney(Scanner scanner) throws Exception {
		System.out.print(color + "Enter wallet ID: \033[0m");
		String walletId = scanner.nextLine();

		System.out.print(color + "Enter amount to withdraw: \033[0m");
		String temp = scanner.nextLine();
		double amount;

		try{
			amount = Double.parseDouble(temp);

		}catch (NumberFormatException e){
			System.out.println(color + "Invalid withdraw: " + e.getMessage() + "\033[0m");
			return;
		}

		HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/wallets/" + walletId + "/withdraw").openConnection();
		connection.setRequestMethod("PUT");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");

		try (OutputStream os = connection.getOutputStream()) {
			os.write(Double.toString(amount).getBytes());
		}

		readResponse(connection);

	}

	// Reads the response and outputs it in the console
	private static void readResponse(HttpURLConnection connection) throws Exception {

		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {

			InputStream is = connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String inputLine;
			StringBuilder response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();
			System.out.println(color + "Response: " + response + "\033[0m");

		} else {
			System.out.println(color + "Failed to process request. Response: " + connection.getResponseMessage() + "\033[0m");
		}
	}

}

