# Stock Market Simulator
A fully functional Investment Trading Application, written in Java.
Created as part of the ***Object-Oriented Programming*** module at *Queen Mary, University of London*.

Dependencies
---
Requires the JavaFX and Swing libraries to render the GUI.
*Developed using Java 17*.

Usage
---
- Extract the contents of the **`src`** folder.
- Build the project using:
    ```bash
    $    javac StockMarket.java
    ```
- Run the project using:
    ```bash
    $    java StockMarket
    ```

Features
---
**Investment Trading**
- This application enables users to buy and sell a variety of assets; including stocks, forex, and cryptocurrencies. Users can purchase fractional shares, and asset prices are updated every 5 seconds to ensure accurate and timely information. They can also sell their investments at any time for the current market price.

**Portfolio Management**
- This simulator allows users to track their investments in real-time. Users can view their portfolio, which includes a list of all their investments and also view details of these investments, such as the original purchase price, current market value, and profits earned. They are also to free to deposit and withdraw funds from their account as they please.

**Account Handling**
- Users can easily register, login, and manage their accounts. They can also view their account information, including their current balance, the total number of trades made, and total profits earned. Using a custom reader to provide serialization and deserialization of account data means that users can enjoy seamless transactions and instant access to investment information.
