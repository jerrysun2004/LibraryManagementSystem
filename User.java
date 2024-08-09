import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//here I define all the attributes of a user, like their name, librarycard
//i make it implement serialiable because when they sign up
//their information gets stored in the file user.dat:
public class User implements LibraryUser, Comparable<User>, Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String libraryCardNumber;
    private String password;
    private boolean active;
    private List<Book> borrowedBooks;
    private static final int MAX_OVERDUE_BOOKS = 5;

    public User(String firstName, String lastName, String email, String libraryCardNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.libraryCardNumber = libraryCardNumber;
        this.password = password;
        this.active = true;
        this.borrowedBooks = new ArrayList<>();
    }

    @Override
    public void borrowBook(Book book) throws BookNotAvailableException, TooManyOverdueBooksException {
        if (book.isCheckedOut()) {
            throw new BookNotAvailableException("Book '" + book.getTitle() + "' is not available for check-out.");
        }
        if (getOverdueBooksCount() >= MAX_OVERDUE_BOOKS) {
            throw new TooManyOverdueBooksException("User has too many overdue books.");
        }
        book.setCheckedOut(true);
        borrowedBooks.add(book);
    }

    @Override
    public void returnBook(Book book) {
        book.setCheckedOut(false);
        borrowedBooks.remove(book);
    }

    public int getOverdueBooksCount() {
        // Assuming overdue books are those that have been borrowed for more than a certain period
        // Here we'll just return a dummy value for demonstration
        return 0; // Replace with actual logic to calculate overdue books
    }

    public String getPassword() {
        return password;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String getLibraryCardNumber() {
        return libraryCardNumber;
    }

    public String getFirstName() {
        return firstName;
    }
//these are all the methods that a user has, for example setters and getters like getEmail() and getFirstName().
    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int compareTo(User other) {
        return this.firstName.compareTo(other.firstName);
    }
}
