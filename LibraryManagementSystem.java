import javax.swing.*;
import java.awt.*;

public class LibraryManagementSystem extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Library library;
    private User currentUser;  // Add a reference to the current user
//All of the GUI is defined in these classes
    public LibraryManagementSystem() {
        library = Library.INSTANCE;
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new LoginPanel(this), "Login");
        mainPanel.add(new SignupPanel(this, library), "Signup");
        // mainPanel.add(new RegularUserPanel(this, library), "RegularUser");  // Remove this line
        mainPanel.add(new LibrarianPanel(this, library), "Librarian");
//setting up the gui
        add(mainPanel);
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
//making the methods that we will use to display gui
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public void showUserPanel(User user) {
        currentUser = user;  // Set the current user
        mainPanel.add(new RegularUserPanel(this, library, currentUser), "RegularUser");
        showPanel("RegularUser");
    }
//main function
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryManagementSystem frame = new LibraryManagementSystem();
            frame.setVisible(true);
        });
    }
}
