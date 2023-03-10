import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;

public class LoginWindow extends JPanel {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;

    public LoginWindow() {
        setPreferredSize(new Dimension(240, 130));
        setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel);
        usernameLabel.setBounds(15, 15, 100, 25);
        usernameField = new JTextField(5);
        add(usernameField);
        usernameField.setBounds(85, 15, 140, 25);

        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel);
        passwordLabel.setBounds(15, 50, 100, 25);
        passwordField = new JPasswordField(5);
        add(passwordField);
        passwordField.setBounds(85, 50, 140, 25);

        loginButton = new JButton("Login");
        add(loginButton);
        loginButton.setBounds(70, 90, 100, 25);
    }

    private Account deserialize(String username) {
        try {
            FileInputStream fileIn = new FileInputStream(username + ".acc");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Account account = (Account) in.readObject();
            in.close();
            fileIn.close();
            return account;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error occurred while deserializing the object.");
        }
        return null;
    }

    public Account loadProfile() {
        Account attempt = deserialize(usernameField.getText());
        try {
            if (usernameField.getText().equals(attempt.getUsername()) && Arrays.equals(passwordField.getPassword(), attempt.getPassword())) {
                return attempt;
            }
        } catch (NullPointerException e) {
            System.out.println("The specified account does not exist.");
        }
        return null;
    }

    public void addSubmitListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
}