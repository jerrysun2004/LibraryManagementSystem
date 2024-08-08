public class DatabaseAdministrator extends User {
    private static final String DEFAULT_PASSWORD = "admin";
//this code is very similiar to librarian
//therefore it provides no extra functionality to the program
//originally thought it was different from librarian
    public DatabaseAdministrator(String firstName, String lastName, String email, String libraryCardNumber) {
        super(firstName, lastName, email, libraryCardNumber, DEFAULT_PASSWORD);
    }

    @Override
    public String toString() {
        return "DatabaseAdministrator{" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", libraryCardNumber='" + getLibraryCardNumber() + '\'' +
                '}';
    }
}
