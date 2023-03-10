import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MainWindow extends JPanel {
    private Account account;
    private boolean isLoggedIn = false;
    private int refreshCounter = 0;
    private final JLabel refreshCountdown;

    private final JTextArea console;
    private ArrayList<String> consoleText = new ArrayList<>();

    private final JLabel loginCaption;
    private final JButton loginButton;
    private final JButton registerButton;
    private final JButton depositButton;
    private final JButton withdrawButton;

    private final JLabel portfolioCaption;
    private final JList portfolioList;
    private ArrayList<String> portfolioListItems = new ArrayList<>();
    private final JButton viewInvestmentButton;

    private final JLabel investmentsCaption;
    private final JList investmentList;
    private ArrayList<String> investmentListItems = new ArrayList<>();
    private final JButton buyButton;

    public MainWindow() {
        setPreferredSize(new Dimension(785, 487));
        setLayout(null);

        refreshCountdown = new JLabel();
        refreshCountdown.setBounds(555, 455, 200, 25);
        add(refreshCountdown);

        console = new JTextArea(5, 5);
        console.setBounds(10, 40, 285, 440);
        add(console);

        loginCaption = new JLabel("You need to log in first!");
        loginCaption.setBounds(10, 10, 285, 25);
        add(loginCaption);

        loginButton = new JButton("Login");
        loginButton.addActionListener(evt -> {
            JFrame frame = new JFrame("My Account");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            if (!isLoggedIn) {
                frame.setTitle("Login");
                LoginWindow popup = new LoginWindow();

                popup.addSubmitListener(e -> {
                    if (popup.loadProfile() != null) {
                        account = popup.loadProfile();
                        isLoggedIn = true;

                        addPossibleInvestments();
                        updateComponents();
                        updateConsole("Successfully logged into account: " + account.getUsername() + ".");
                    }
                    else {
                        broadcastMessage("Invalid credentials!");
                    }
                    frame.dispose();
                });
                frame.getContentPane().add(popup);
            }
            else {
                AccountWindow popup = new AccountWindow(account);
                frame.getContentPane().add(popup);
            }
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
        });
        add(loginButton);
        loginButton.setBounds(565, 10, 100, 25);

        registerButton = new JButton("Register");
        registerButton.addActionListener(evt -> {
            if (!isLoggedIn) {
                JFrame frame = new JFrame("Register");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                RegisterWindow popup = new RegisterWindow();

                popup.addSubmitListener(e -> {
                    int operationResult = popup.createProfile();
                    switch (operationResult) {
                        case 0 -> broadcastMessage("Username already taken!");
                        case 1 -> broadcastMessage("Account created successfully!");
                        case 2 -> broadcastMessage("Invalid input when creating account!");
                    }
                    frame.dispose();
                });
                frame.getContentPane().add(popup);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setVisible(true);
            }
            else {
                account.updateInformation();
                isLoggedIn = false;
                consoleText = new ArrayList<>();
                clearConsole();
                portfolioListItems = new ArrayList<>();
                investmentListItems = new ArrayList<>();
                refreshLists();
                updateComponents();
            }
        });
        add(registerButton);
        registerButton.setBounds(670, 10, 100, 25);

        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(evt -> withdrawOrDeposit(true));
        withdrawButton.setToolTipText("Withdraw funds from your account.");
        withdrawButton.setBounds(425, 10, 100, 25);
        add(withdrawButton);

        depositButton = new JButton("Deposit");
        depositButton.addActionListener(evt -> withdrawOrDeposit(false));
        depositButton.setToolTipText("Add funds to your account.");
        depositButton.setBounds(320, 10, 100, 25);
        add(depositButton);

        portfolioCaption = new JLabel("Your current investments:");
        portfolioCaption.setBounds(305, 45, 200, 25);
        add(portfolioCaption);

        portfolioList = new JList(portfolioListItems.toArray());
        portfolioList.setBounds(305, 70, 240, 380);
        add(portfolioList);

        viewInvestmentButton = new JButton("View Investment");
        viewInvestmentButton.addActionListener(evt -> {
            int index = portfolioList.getSelectedIndex();
            JFrame frame = new JFrame("Information");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            if (portfolioList.getSelectedValue() != null) {
                Asset selected = account.getPortfolio().getAssetAtIndex(index);
                SellWindow popup = new SellWindow(selected);

                popup.addSubmitListener(e -> {
                    account.setBalance(account.getBalance() + selected.getValue());
                    account.setTotalProfit(selected.getOriginalPrice() - selected.getValue());
                    account.getPortfolio().removeAsset(selected);
                    account.incrementNumberOfTrades();
                    portfolioListItems.remove(index);
                    refreshLists();
                    broadcastMessage("Welcome, " + account.getFirstName() + ". Your balance is " + account.toString());
                    updateConsole("Successfully sold (" + selected.convertQuantityToString() + "x) " + selected.getName() + ".");
                    frame.dispose();
                });
                frame.getContentPane().add(popup);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setVisible(true);
            }
            else {
                updateConsole("Please select an asset to view.");
            }
        });
        viewInvestmentButton.setToolTipText("View the selected investment.");
        viewInvestmentButton.setBounds(305, 455, 240, 25);
        add(viewInvestmentButton);

        investmentsCaption = new JLabel("Possible investments:");
        investmentsCaption.setBounds(555, 45, 175, 25);
        add(investmentsCaption);

        investmentList = new JList(investmentListItems.toArray());
        investmentList.setBounds(555, 70, 225, 380);
        add(investmentList);

        buyButton = new JButton("Invest");
        buyButton.addActionListener(evt -> {
            JFrame frame = new JFrame("Invest");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            if (investmentList.getSelectedValue() != null) {
                Asset selected = account.getAssetList().getAssetAtIndex(investmentList.getSelectedIndex());
                InvestWindow popup = new InvestWindow(selected.getName(), selected.getPrice(), selected.getInvestmentID());

                popup.addSubmitListener(e -> {
                    if (popup.purchaseAsset() != null) {
                        if (account.getBalance() >= popup.purchaseAsset().getPrice() * popup.purchaseAsset().getQuantity()) {
                            account.setBalance(account.getBalance() - popup.purchaseAsset().getValue());
                            account.getPortfolio().addAsset(popup.purchaseAsset());
                            account.incrementNumberOfTrades();
                            portfolioListItems.add(popup.purchaseAsset().portfolioString());
                            refreshLists();
                            broadcastMessage("Welcome, " + account.getFirstName() + ". Your balance is " + account.toString());
                            updateConsole("Successfully purchased (" + popup.purchaseAsset().convertQuantityToString() + "x) " + popup.purchaseAsset().getName() + ".");
                        }
                        else if (account.getBalance() < popup.purchaseAsset().getPrice() * popup.purchaseAsset().getQuantity()) {
                            updateConsole("You do not have enough money!");
                        }
                    }
                    frame.dispose();
                });
                frame.getContentPane().add(popup);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setVisible(true);
            }
            else {
                updateConsole("Please select an asset to invest in.");
            }
        });
        buyButton.setToolTipText("Purchase selected investment.");
        buyButton.setBounds(680, 455, 99, 25);
        add(buyButton);

        updateComponents();
    }

    private void withdrawOrDeposit(boolean isWithdraw) {
        JFrame frame = new JFrame("Withdraw");
        if (!isWithdraw) {
            frame.setTitle("Deposit");
        }

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        MoneyWindow popup = new MoneyWindow(isWithdraw);

        popup.addSubmitListener(e -> {
            if (popup.getAmount() > 0) {
                if (isWithdraw && account.getBalance() >= popup.getAmount()) {
                    account.withdraw(popup.getAmount());
                    updateConsole("Withdrew $" + String.format("%.2f", popup.getAmount()) + " from your account!");
                }
                else if (!isWithdraw) {
                    account.deposit(popup.getAmount());
                    updateConsole("Deposited $" + String.format("%.2f", popup.getAmount()) + " into your account!");
                }
                else {
                    updateConsole("You do not have sufficient funds!");
                }
                broadcastMessage("Welcome, " + account.getFirstName() + ". Your balance is " + account.toString());
            }
            else {
                updateConsole("You have entered an invalid input!");
            }
            frame.dispose();
        });
        frame.getContentPane().add(popup);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void updateComponents() {
        console.setEnabled(isLoggedIn);
        depositButton.setEnabled(isLoggedIn);
        withdrawButton.setEnabled(isLoggedIn);
        portfolioCaption.setEnabled(isLoggedIn);
        portfolioList.setEnabled(isLoggedIn);
        viewInvestmentButton.setEnabled(isLoggedIn);
        investmentsCaption.setEnabled(isLoggedIn);
        investmentList.setEnabled(isLoggedIn);
        buyButton.setEnabled(isLoggedIn);

        if (isLoggedIn) {
            broadcastMessage("Welcome, " + account.getFirstName() + ". Your balance is " + account.toString());
            loginButton.setText("Account");
            loginButton.setToolTipText("View your account information.");
            registerButton.setText("Logout");
            registerButton.setToolTipText("End your current trading session.");
        }
        else {
            broadcastMessage("You need to log in first!");
            loginButton.setText("Login");
            loginButton.setToolTipText("Log into an existing account.");
            registerButton.setText("Register");
            registerButton.setToolTipText("Create a new account.");
            refreshCountdown.setText("");
        }
    }

    private void refreshLists() {
        investmentList.setListData(investmentListItems.toArray());
        portfolioList.setListData(portfolioListItems.toArray());
    }

    private void clearConsole() {
        console.setText("");
    }

    private void updateConsole(String newLine) {
        if (console.getLineCount() >= 26) {
            consoleText.remove(0);
        }

        consoleText.add("\n     " + newLine);
        clearConsole();
        for (String s : consoleText) {
            console.append(s);
        }
    }

    public void broadcastMessage(String message) {
        loginCaption.setText(message);
    }

    public void tick() {
        if (refreshCounter == 5) {
            if (isLoggedIn) {
                broadcastMessage("Welcome, " + account.getFirstName() + ". Your balance is " + account.toString());
                updateAssetPrices();
                account.updateInformation();
                refreshCountdown.setText("Prices Updated!");
            }
            refreshCounter = 0;
        }
        else {
            if (isLoggedIn) {
                refreshCountdown.setText("Update in " + (5 - refreshCounter) + " seconds.");
            }
            refreshCounter++;
        }
    }

    private double stockPriceFluctuation(Asset current) {
        Random number = new Random();
        double priceChange = (number.nextDouble() + 0.1) * current.getVolatility();
        return number.nextBoolean() ? current.getPrice() - priceChange : current.getPrice() + priceChange;
    }

    public void addPossibleInvestments() {
        if (account.getAssetListStatus()) {
            for (int i = 0; i < account.getAssetList().getArray().size(); i++) {
                Asset current = account.getAssetList().getAssetAtIndex(i);
                current.setInvestmentID(i);
                investmentListItems.add(current.assetString());
            }

            for (int j = 0; j < account.getPortfolio().getSize(); j++) {
                portfolioListItems.add(account.getPortfolio().getAssetAtIndex(j).portfolioString());
            }

            refreshLists();
        }
        else {
            account.getAssetList().addAsset(new Stock("Microsoft", 280.74, 1, 4));
            account.getAssetList().addAsset(new Stock("Apple", 165.57, 1, 5));
            account.getAssetList().addAsset(new Stock("Amazon", 3068.38, 1, 8));
            account.getAssetList().addAsset(new Stock("Tesla", 993.68, 1, 7));
            account.getAssetList().addAsset(new Stock("Nvidia", 216.70, 1, 5));
            account.getAssetList().addAsset(new Stock("Google", 2568.80, 1, 6));
            account.getAssetList().addAsset(new Stock("Meta", 212.08, 1, 3));
            account.getAssetList().addAsset(new Stock("Netflix", 339.45, 1, 6));
            account.getAssetList().addAsset(new Cryptocurrency("Bitcoin", 30234.14, 1, 150));
            account.getAssetList().addAsset(new Cryptocurrency("Ethereum", 2250.07, 1, 25));
            account.getAssetList().addAsset(new Cryptocurrency("Monero", 243.23, 1, 3));
            account.getAssetList().addAsset(new Cryptocurrency("Solana", 75.80, 1, 2));
            Currency GBP = new Currency("GBP", 1);
            Currency USD = new Currency("USD", 1.31);
            Currency EUR = new Currency("EUR", 1.21);
            Currency CAD = new Currency("CAD", 1.64);
            account.getAssetList().addAsset(new Forex(GBP, USD, 1, 1));
            account.getAssetList().addAsset(new Forex(GBP, EUR, 1, 1));
            account.getAssetList().addAsset(new Forex(USD, CAD, 1, 1));
            account.getAssetList().addAsset(new Forex(CAD, EUR, 1, 1));
            account.setAssetListStatus(true);

            addPossibleInvestments();
        }
    }

    private void updateAssetPrices() {
        if (account.getAssetListStatus()) {
            for (int i = 0; i < account.getAssetList().getArray().size(); i++) {
                Asset current = account.getAssetList().getAssetAtIndex(i);
                current.setPrice(stockPriceFluctuation(current));
                investmentListItems.set(i, current.assetString());
            }

            for (int j = 0; j < account.getPortfolio().getSize(); j++) {
                Asset current = account.getPortfolio().getAssetAtIndex(j);
                current.setPrice(account.getAssetList().getAssetAtIndex(current.getInvestmentID()).getPrice());
                portfolioListItems.set(j, current.portfolioString());
            }
        }

        refreshLists();
    }
}