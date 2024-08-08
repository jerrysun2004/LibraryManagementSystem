public interface LibraryUser {
    void borrowBook(Book book) throws BookNotAvailableException, TooManyOverdueBooksException;
    void returnBook(Book book);
    String getLibraryCardNumber();
    boolean isActive();  // Add this method
}
