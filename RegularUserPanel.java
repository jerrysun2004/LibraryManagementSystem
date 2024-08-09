import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
//this file describes the panel that regular users get
//when they sign in
//since they are not librarians they have a different panel
//they are not allowed to add or remove books from collection
// only librarian is allowed to do this
public class RegularUserPanel extends JPanel {
    private LibraryManagementSystem mainFrame;
    private Library library;
    private User currentUser;

    public RegularUserPanel(LibraryManagementSystem mainFrame, Library library, User currentUser) {
        this.mainFrame = mainFrame;
        this.library = library;
        this.currentUser = currentUser;
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("User Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
//make the buttons
        JButton checkoutBookButton = createStyledButton("Check Out Books");
        JButton returnBookButton = createStyledButton("Return Books");
        JButton listBooksButton = createStyledButton("List All Books");
        JButton backButton = createStyledButton("Back to Home Page");

        checkoutBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCheckoutBooksDialog();
            }
        });
//action listeners for when actions are performed in the panel
        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReturnBooksDialog();
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

        buttonPanel.add(checkoutBookButton, gbc);
        buttonPanel.add(returnBookButton, gbc);
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
//When the user is about to check out books
//this will pop up and allow users to check off which books they are checkingout
    private void showCheckoutBooksDialog() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : library.getBooks()) {
            if (!book.isCheckedOut()) {
                availableBooks.add(book);
            }
        }

        JPanel panel = new JPanel(new GridLayout(availableBooks.size(), 1));
        List<JCheckBox> checkBoxes = new ArrayList<>();
        for (Book book : availableBooks) {
            JCheckBox checkBox = new JCheckBox(book.getTitle() + " by " + book.getAuthor());
            checkBox.putClientProperty("book", book);
            checkBoxes.add(checkBox);
            panel.add(checkBox);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        int result = JOptionPane.showConfirmDialog(mainFrame, scrollPane, "Check Out Books", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    Book book = (Book) checkBox.getClientProperty("book");
                    try {
                        currentUser.borrowBook(book);
                        JOptionPane.showMessageDialog(mainFrame, "Books checked out successfully.");
                    } catch (BookNotAvailableException | TooManyOverdueBooksException ex) {
                        JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
//similar to checkout it will return a list of the books that the user has checked out and allow them to return the books they want to return
    private void showReturnBooksDialog() {
        List<Book> borrowedBooks = currentUser.getBorrowedBooks();

        JPanel panel = new JPanel(new GridLayout(borrowedBooks.size(), 1));
        List<JCheckBox> checkBoxes = new ArrayList<>();
        for (Book book : borrowedBooks) {
            JCheckBox checkBox = new JCheckBox(book.getTitle() + " by " + book.getAuthor());
            checkBox.putClientProperty("book", book);
            checkBoxes.add(checkBox);
            panel.add(checkBox);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        int result = JOptionPane.showConfirmDialog(mainFrame, scrollPane, "Return Books", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    Book book = (Book) checkBox.getClientProperty("book");
                    currentUser.returnBook(book);
                }
            }
            JOptionPane.showMessageDialog(mainFrame, "Books returned successfully.");
        }
    }
//If they want to see which books are avaialbe in the catalog this will give them a nicely
//formatted list of all the books avaialble at the library at the current time
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
