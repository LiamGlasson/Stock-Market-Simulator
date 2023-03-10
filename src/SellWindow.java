import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SellWindow extends JPanel {
    private final JButton sellButton;

    public SellWindow(Asset selected) {
        setPreferredSize(new Dimension(247, 181));
        setLayout(null);

        JLabel assetNameLabel = new JLabel("Name of Asset: (" + selected.convertQuantityToString() + "x) " + selected.getName());
        add(assetNameLabel);
        assetNameLabel.setBounds(20, 15, 230, 25);

        JLabel originalPriceLabel = new JLabel("Bought for: $" + selected.format(selected.getOriginalPrice()));
        add(originalPriceLabel);
        originalPriceLabel.setBounds(20, 45, 230, 25);

        JLabel currentValueLabel = new JLabel("Current value: $" + selected.format(selected.getValue()));
        add(currentValueLabel);
        currentValueLabel.setBounds(20, 75, 230, 25);

        JLabel profitLabel = new JLabel("Total Profit: " + selected.convertProfitToString());
        add(profitLabel);
        profitLabel.setBounds(20, 105, 230, 25);

        sellButton = new JButton("Sell Now");
        add(sellButton);
        sellButton.setBounds(75, 145, 100, 20);
    }

    public void addSubmitListener(ActionListener listener) {
        sellButton.addActionListener(listener);
    }
}