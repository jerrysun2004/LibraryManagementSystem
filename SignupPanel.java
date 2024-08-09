import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
//this is the signup panel which will show up when a user clicks on the sign up button on the welcome page
//here they will enter their first and last name library id card and other data fields


public class SignupPanel extends JPanel implements Serializable {
    private LibraryManagementSystem mainFrame;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField cardNumberField;
    private JPasswordField passwordField;
    private static final String USER_DATA_FILE = "users.dat";

    public SignupPanel(LibraryManagementSystem mainFrame, Library library) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Set background color
        setBackground(new Color(70, 130, 180));
//background color is the same as welcome page
        JLabel titleLabel = new JLabel("Sign Up", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);
//state the dimensions of the signin page
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(createLabel("First Name:"), gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        firstNameField = new JTextField(20);
        add(firstNameField, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(createLabel("Last Name:"), gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        lastNameField = new JTextField(20);
        add(lastNameField, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(createLabel("Email:"), gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        emailField = new JTextField(20);
        add(emailField, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(createLabel("Library Card Number:"), gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        cardNumberField = new JTextField(20);
        add(cardNumberField, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(createLabel("Password:"), gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(70, 130, 180));
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.insets = new Insets(10, 5, 10, 5);
//create the buttons and specify the behavior
        JButton signupButton = createStyledButton("Sign Up");
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String libraryCardNumber = cardNumberField.getText();
                String password = new String(passwordField.getPassword());
                if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !libraryCardNumber.isEmpty() && !password.isEmpty()) {
                    User newUser = new User(firstName, lastName, email, libraryCardNumber, password);
                    Library.INSTANCE.addUser(newUser);
                    saveUserData(newUser);
                    JOptionPane.showMessageDialog(mainFrame, "Sign up successful.");
                    clearFields();
                    mainFrame.showPanel("Login");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Please fill in all fields.");
                }
            }
        });

        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
                mainFrame.showPanel("Login");
            }
        });

        gbcButton.gridx = 0;
        gbcButton.gridy = 0;
        buttonPanel.add(signupButton, gbcButton);

        gbcButton.gridx = 1;
        buttonPanel.add(backButton, gbcButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);
    }
//create labels and buttons
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(120, 40));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(70, 130, 180));
        button.setFocusPainted(false);
        return button;
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        cardNumberField.setText("");
        passwordField.setText("");
    }

    private void saveUserData(User user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_DATA_FILE, true))) {
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//load the user data into data file
    public static List<User> loadUserData() {
        List<User> users = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_DATA_FILE))) {
            while (true) {
                try {
                    User user = (User) ois.readObject();
                    users.add(user);
                } catch (EOFException eof) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }
}
