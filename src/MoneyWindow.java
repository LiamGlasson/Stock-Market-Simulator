import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MoneyWindow extends JPanel {
    private final JTextField promptInput;
    private JButton submitButton;

    public MoneyWindow(boolean isWithdraw) {
        setPreferredSize(new Dimension(250, 96));
        setLayout(null);

        JLabel promptLabel = new JLabel("Amount to withdraw:");
        submitButton = new JButton("Withdraw");

        if (!isWithdraw) {
            promptLabel.setText("Amount to deposit:");
            submitButton = new JButton("Deposit");
        }

        add(promptLabel);
        promptLabel.setBounds(10, 15, 125, 25);
        promptInput = new JTextField(5);
        add(promptInput);
        promptInput.setBounds(140, 15, 100, 25);

        submitButton.setBounds(20, 60, 210, 20);
        add(submitButton);
    }

    public double getAmount() {
        try {
            return Double.parseDouble(promptInput.getText());
        } catch (NumberFormatException ex) {
            System.out.println("An error occurred while parsing the amount.");
        }
        return 0;
    }

    public void addSubmitListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }
}