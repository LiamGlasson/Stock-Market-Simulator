import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class RegisterWindow extends JPanel {
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton registerButton;

    public RegisterWindow() {
        setPreferredSize(new Dimension(240, 194));
        setLayout(null);

        JLabel firstNameLabel = new JLabel("First Name:");
        add(firstNameLabel);
        firstNameLabel.setBounds(15, 10, 100, 25);
        firstNameField = new JTextField(5);
        add(firstNameField);
        firstNameField.setBounds(85, 10, 140, 25);

        JLabel lastNameLabel = new JLabel("Last Name:");
        add(lastNameLabel);
        lastNameLabel.setBounds(15, 45, 100, 25);
        lastNameField = new JTextField(5);
        add(lastNameField);
        lastNameField.setBounds(85, 45, 140, 25);

        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel);
        usernameLabel.setBounds(15, 80, 100, 25);
        usernameField = new JTextField(5);
        add(usernameField);
        usernameField.setBounds(85, 80, 140, 25);

        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel);
        passwordLabel.setBounds(15, 115, 100, 25);
        passwordField = new JPasswordField(5);
        add(passwordField);
        passwordField.setBounds(85, 115, 140, 25);

        registerButton = new JButton("Register");
        add(registerButton);
        registerButton.setBounds(70, 155, 100, 25);
    }

    private boolean validateFields() {
        return firstNameField.getText().equals("") ||
                lastNameField.getText().equals("") ||
                usernameField.getText().equals("") ||
                Arrays.equals(passwordField.getPassword(), new char[]{});
    }

    public int createProfile() {
        if (!validateFields()) {
            File profile = new File(usernameField.getText() + ".acc");

            if (!profile.exists()) {
                Account newUser = new Account(firstNameField.getText(), lastNameField.getText());
                newUser.setUsername(usernameField.getText());
                newUser.setPassword(passwordField.getPassword());

                serialize(newUser, usernameField.getText());
                return 1;
            }
            return 0;
        }
        return 2;
    }

    private void serialize(Account profile, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName + ".acc");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(profile);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("An error occurred while serializing the object.");
        }
    }

    public void addSubmitListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }
}