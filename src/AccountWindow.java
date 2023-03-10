import javax.swing.*;
import java.awt.*;

public class AccountWindow extends JPanel {

    public AccountWindow(Account account) {
        setPreferredSize(new Dimension(233, 130));
        setLayout(null);

        JLabel fullNameLabel = new JLabel("Full Name: " + account.getFullName());
        add(fullNameLabel);
        fullNameLabel.setBounds(15, 40, 205, 25);

        JLabel usernameLabel = new JLabel("Username: " + account.getUsername());
        add(usernameLabel);
        usernameLabel.setBounds(15, 15, 205, 25);

        JLabel tradeLabel = new JLabel("Total trades made: " + account.getNumberOfTrades());
        add(tradeLabel);
        tradeLabel.setBounds(15, 65, 205, 25);

        JLabel profitLabel = new JLabel("Total profit made: $" + String.format("%.2f", account.getTotalProfit()));
        add(profitLabel);
        profitLabel.setBounds(15, 90, 205, 25);
    }
}