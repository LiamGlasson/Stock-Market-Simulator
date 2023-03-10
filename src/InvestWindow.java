import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InvestWindow extends JPanel {
    private final String assetName;
    private final double assetPrice;
    private final int investmentID;
    private final JTextField quantityInput;
    private final JButton investButton;

    public InvestWindow(String name, double price, int investmentID) {
        this.assetName = name;
        this.assetPrice = price;
        this.investmentID = investmentID;

        setPreferredSize(new Dimension(234, 163));
        setLayout(null);

        JLabel assetNameLabel = new JLabel("Name of Asset: " + assetName);
        add(assetNameLabel);
        assetNameLabel.setBounds(15, 10, 205, 25);

        JLabel assetPriceLabel = new JLabel("Price per share: $" + String.format("%.2f", price));
        add(assetPriceLabel);
        assetPriceLabel.setBounds(15, 45, 205, 25);

        JLabel assetQuantity = new JLabel("Quantity to buy:");
        add(assetQuantity);
        assetQuantity.setBounds(15, 80, 205, 25);
        quantityInput = new JTextField(5);
        quantityInput.setToolTipText("How much would you like to buy?");
        add(quantityInput);
        quantityInput.setBounds(120, 80, 100, 25);

        investButton = new JButton("Buy Now");
        add(investButton);
        investButton.setBounds(65, 125, 100, 20);
    }

    public boolean validateInput() {
        return !quantityInput.getText().isEmpty() && !quantityInput.getText().equals("0");
    }

    public Asset purchaseAsset() {
        if (validateInput()) {
            try {
                Asset purchase = new Asset(assetName, assetPrice, Double.parseDouble(quantityInput.getText()));
                purchase.setOriginalPrice(assetPrice * Double.parseDouble(quantityInput.getText()));
                purchase.setInvestmentID(investmentID);
                return purchase;
            } catch (NumberFormatException e) {
                System.out.println("An error occurred while parsing the quantity input.");
            }
        }
        return null;
    }

    public void addSubmitListener(ActionListener listener) {
        investButton.addActionListener(listener);
    }
}