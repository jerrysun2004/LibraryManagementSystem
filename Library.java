import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum Library implements Serializable {
    INSTANCE;

    private List<Book> books;
    private List<LibraryUser> users;

    private Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        // Add default librarian
        addUser(new Librarian("Library", "Admin", "library@admin.com", "library"));

        // Add classic books
        addClassicBooks();
    }

    private void addClassicBooks() {
        books.add(new Book("Pride and Prejudice", "Jane Austen", "1111111111"));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "2222222222"));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "3333333333"));
        books.add(new Book("Moby Dick", "Herman Melville", "4444444444"));
        books.add(new Book("1984", "George Orwell", "5555555555"));
        books.add(new Book("War and Peace", "Leo Tolstoy", "6666666666"));
        books.add(new Book("The Odyssey", "Homer", "7777777777"));
        books.add(new Book("Crime and Punishment", "Fyodor Dostoevsky", "8888888888"));
        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", "9999999999"));
        books.add(new Book("The Brothers Karamazov", "Fyodor Dostoevsky", "1010101010"));
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<LibraryUser> getUsers() {
        return users;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addUser(LibraryUser user) {
        users.add(user);
    }

    public Book findBookByISBN(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public LibraryUser findUserByLibraryCardNumber(String libraryCardNumber) {
        for (LibraryUser user : users) {
            if (user.getLibraryCardNumber().equals(libraryCardNumber)) {
                return user;
            }
        }
        return null;
    }
}
