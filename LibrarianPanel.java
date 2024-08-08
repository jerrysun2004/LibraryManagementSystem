import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
//LibrarianPanel has different functionality compared to
//regular user for example they can add and remove books from collection
public class LibrarianPanel extends JPanel {
    private LibraryManagementSystem mainFrame;
    private Library library;

    public LibrarianPanel(LibraryManagementSystem mainFrame, Library library) {
        this.mainFrame = mainFrame;
        this.library = library;
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Librarian Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;

        JButton addBookButton = createStyledButton("Add Book");
        JButton removeBookButton = createStyledButton("Remove Book");
        JButton disableUserButton = createStyledButton("Disable User");
        JButton enableUserButton = createStyledButton("Enable User");
        JButton listBooksButton = createStyledButton("List All Books");
        JButton backButton = createStyledButton("Back to Home Page");

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddBookDialog();
            }
        });

        removeBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRemoveBookDialog();
            }
        });

        disableUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardNumber = JOptionPane.showInputDialog("Enter user card number to disable:");
                if (cardNumber != null) {
                    LibraryUser user = library.findUserByLibraryCardNumber(cardNumber);
                    if (user != null && user.isActive()) {
                        ((User) user).setActive(false);
                        JOptionPane.showMessageDialog(mainFrame, "User disabled successfully.");
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "User not found or already disabled.");
                    }
                }
            }
        });

        enableUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardNumber = JOptionPane.showInputDialog("Enter user card number to enable:");
                if (cardNumber != null) {
                    LibraryUser user = library.findUserByLibraryCardNumber(cardNumber);
                    if (user != null && !user.isActive()) {
                        ((User) user).setActive(true);
                        JOptionPane.showMessageDialog(mainFrame, "User enabled successfully.");
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "User not found or already enabled.");
                    }
                }
            }
        });

        listBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showListBooksDialog();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("Login");
            }
        });

        buttonPanel.add(addBookButton, gbc);
        buttonPanel.add(removeBookButton, gbc);
        buttonPanel.add(disableUserButton, gbc);
        buttonPanel.add(enableUserButton, gbc);
        buttonPanel.add(listBooksButton, gbc);
        buttonPanel.add(backButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }

    private void showAddBookDialog() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField titleField = new JTextField(20);
        JTextField authorField = new JTextField(20);
        JTextField isbnField = new JTextField(20);

        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(new JLabel("ISBN:"));
        panel.add(isbnField);

        int result = JOptionPane.showConfirmDialog(mainFrame, panel, "Add Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            if (!title.isEmpty() && !author.isEmpty() && !isbn.isEmpty()) {
                Book book = new Book(title, author, isbn);
                library.addBook(book);
                JOptionPane.showMessageDialog(mainFrame, "Book added successfully.");
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please fill in all fields.");
            }
        }
    }

    private void showRemoveBookDialog() {
        List<Book> books = library.getBooks();

        JPanel panel = new JPanel(new GridLayout(books.size(), 1));
        List<JCheckBox> checkBoxes = new ArrayList<>();
        for (Book book : books) {
            JCheckBox checkBox = new JCheckBox(book.getTitle() + " by " + book.getAuthor());
            checkBox.putClientProperty("book", book);
            checkBoxes.add(checkBox);
            panel.add(checkBox);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        int result = JOptionPane.showConfirmDialog(mainFrame, scrollPane, "Remove Books", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    Book book = (Book) checkBox.getClientProperty("book");
                    library.getBooks().remove(book);
                }
            }
            JOptionPane.showMessageDialog(mainFrame, "Books removed successfully.");
        }
    }

    private void showListBooksDialog() {
        StringBuilder booksList = new StringBuilder("<html>Books in Library:<br><br>");
        for (Book book : library.getBooks()) {
            booksList.append("Title: ").append(book.getTitle())
                     .append("<br>Author: ").append(book.getAuthor())
                     .append("<br><br>");
        }
        booksList.append("</html>");
        JLabel label = new JLabel(booksList.toString());
        JOptionPane.showMessageDialog(mainFrame, label, "List All Books", JOptionPane.PLAIN_MESSAGE);
    }
}
