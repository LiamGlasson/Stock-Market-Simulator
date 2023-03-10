import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Account extends User {
    private final Portfolio assetList;
    private final Portfolio portfolio;
    private double balance;
    private int numberOfTrades;
    private double totalProfit;
    private boolean alreadyLoadedAssets;

    public Account(String firstName, String lastName) {
        super(firstName, lastName);
        this.balance = 0;
        this.numberOfTrades = 0;
        this.totalProfit = 0;
        this.alreadyLoadedAssets = false;
        this.assetList = new Portfolio();
        this.portfolio = new Portfolio();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getNumberOfTrades() {
        return numberOfTrades;
    }

    public void incrementNumberOfTrades() {
        this.numberOfTrades++;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double profitMade) {
        this.totalProfit = this.totalProfit - profitMade;
    }

    public boolean getAssetListStatus() {
        return alreadyLoadedAssets;
    }

    public void setAssetListStatus(boolean value) {
        this.alreadyLoadedAssets = value;
    }

    public Portfolio getAssetList() {
        return assetList;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }

    public void updateInformation() {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.getUsername() + ".acc");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the account information.");
        }
    }

    @Override
    public String toString() {
        return "$" + String.format("%.2f", balance);
    }
}