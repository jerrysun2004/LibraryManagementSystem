public class Librarian extends User {
    private static final String DEFAULT_PASSWORD = "librarian";

    public Librarian(String firstName, String lastName, String email, String libraryCardNumber) {
        super(firstName, lastName, email, libraryCardNumber, DEFAULT_PASSWORD);
    }

    public void enableUser(User user) {
        user.setActive(true);
    }

    public void disableUser(User user) {
        user.setActive(false);
    }

    @Override
    public String toString() {
        return "Librarian{" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", libraryCardNumber='" + getLibraryCardNumber() + '\'' +
                '}';
    }
}
