# Wallet API

A simple wallet API built using Spring Boot. This API allows users to create wallets, add funds, withdraw funds, and check balances via RESTful endpoints.

## Features

- Create wallets
- View wallet balances
- Deposit money into wallets
- Withdraw money from wallets
- Command-line interface for user interaction

## Technologies Used

- **Java 21**
- **Spring Boot 3.4.1**
- **Maven**

## Prerequisites

Before running the project, make sure you have the following installed:

- **Java 8** (or higher)
- **Maven**

## Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/SimeonValchev/Simple-Wallet-API.git
   cd simple-wallet-api\wallet
   
2. **Install the dependencies**:
   ```bash
   mvn clean install

3. **Run the application**:
   ```bash
   mvn spring-boot:run

The API will now be running on http://localhost:8080 and the command-line interface will open shortly after.

## Instructions

The menu displays six options, numbered 1–6. Choose an option by typing its corresponding number.

### Wallet Operations:

- **Create Wallet**:  Enter a unique name for your new wallet.

- **View Balance**:   Specify the wallet name to check its balance.

- **Deposit Funds**:  Specify the wallet name and the amount to deposit.

- **Withdraw Funds**: Specify the wallet name and the amount to withdraw.

### Customizable Output:

- The output messages are displayed in green by default. You can enable or disable this feature via the menu.

### Exiting:

- When exiting the application, both the command-line interface and the Spring Boot API will shut down cleanly.
