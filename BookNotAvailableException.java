public class BookNotAvailableException extends Exception {
    //this custom exception will be used when the books is not avalable, another user has already checked out the book
    public BookNotAvailableException(String message) {
        super(message);
    }
}
