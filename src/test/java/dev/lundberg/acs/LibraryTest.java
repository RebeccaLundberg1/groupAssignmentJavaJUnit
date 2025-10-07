package dev.lundberg.acs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.io.PrintStream;
import org.junit.Test;

public class LibraryTest {
    
    Library library;

    @org.junit.Before
    public void setUp() {
        library = new Library();
    }

    @Test
    public void testBorrowBook() {

        // lånar en bok som finns i biblioteket
        ArrayList<Book> borrowedBooks = library.borrowBook("Ondskan");
        boolean found = false;

        // Kontrollera att Ondskan finns i lånelistan
        for (Book book : borrowedBooks) { 
            if (book.getName().equalsIgnoreCase("Ondskan")) {
                found = true;
                break;
            }
        }
        assertTrue("Ondskan", found);
    }

    @Test
    public void listAvailableBooks_printAllAvailableBooksInStock() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        library.listAvailableBooks();

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < library.getBooksInStockList().size(); i++) {
            if(!library.getBooksInStockList().get(i).isBorrowed()) {
                sb.append("Title: ");
                sb.append(library.getBooksInStockList().get(i).getName());
                sb.append(System.lineSeparator());
            }
        }

        assertEquals(sb.toString(), outputStream.toString());

        System.setOut(System.out);
    }

    @Test
    public void listAvailableBooks_printAllAvailableBooksInStock_afterBorrowingFiveBooks() {

        for(int i = 0; i < 5; i++) {
            library.getBooksInStockList().get(i).borrowBook();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        library.listAvailableBooks();

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < library.getBooksInStockList().size(); i++) {
            if(!library.getBooksInStockList().get(i).isBorrowed()) {
                sb.append("Title: ");
                sb.append(library.getBooksInStockList().get(i).getName());
                sb.append(System.lineSeparator());
            }
        }

        assertEquals(sb.toString(), outputStream.toString());

        System.setOut(System.out);
    }

    @Test
    public void testListBorrowedBooksBy() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        String simulatedInput = "J.K Rowling\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Library library = new Library();
        library.borrowBook("Harry Potter");
        library.borrowBook("Ondskan");
        library.borrowBook("Tempelriddaren");
        
        library.listBorrowedBooksBy(true);
        
        StringBuilder sb = new StringBuilder();
        sb.append("Authors: ");
        sb.append(System.lineSeparator());
        for(int i = 0; i < library.getBorrowedBooksList().size(); i++) {
            sb.append(library.getBorrowedBooksList().get(i).getAuthor());
            sb.append(System.lineSeparator());
        }
        sb.append("> ");
        for(int i = 0; i < library.getBorrowedBooksList().size(); i++) {
            if(library.getBorrowedBooksList().get(i).getAuthor().equals("J.K Rowling")) {
                sb.append("Title: ");
                sb.append(library.getBorrowedBooksList().get(i).getName());
                sb.append(System.lineSeparator());
            }
        }

        String expected = sb.toString().replace("\r\n", "\n").trim();
        String actual = outputStream.toString().replace("\r\n", "\n").trim();
        assertEquals(expected, actual);

        System.setOut(System.out);
        System.setIn(System.in);
    }

    @Test
    public void extendTime_daysResetToZero_ifBookIsBorrowed() {
        library.borrowBook("Harry Potter");
        library.advanceDay();
        library.advanceDay();
        library.advanceDay();
        assertEquals(0, library.extendTime("Harry Potter"));
    }

    @Test
    public void extendTime_resultInIntegerMinValue_ifBookIsNotBorrowed() {
        library.borrowBook("Harry Potter");
        library.advanceDay();
        library.advanceDay();
        library.advanceDay();
        assertEquals(Integer.MIN_VALUE, library.extendTime("Ondskan"));
    }

    @Test
    public void listBorrowedBooks_whenIncludeDaysBorrowed() {
        library.borrowBook("Harry Potter");
        library.borrowBook("Ondskan");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        library.listBorrowedBooks(true);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < library.getBorrowedBooksList().size(); i++) {
            sb.append("Title: ");
            sb.append(library.getBorrowedBooksList().get(i).getName());
            sb.append(", Days borrowed: ");
            sb.append(library.getBorrowedBooksList().get(i).getDaysBorrowed());
            sb.append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());

        assertEquals(sb.toString(), outputStream.toString());

        System.setOut(System.out);
    }

    @Test
    public void listBorrowedBooks_whenExcludeDaysBorrowed() {
        library.borrowBook("Harry Potter");
        library.borrowBook("Ondskan");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        library.listBorrowedBooks(false);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < library.getBorrowedBooksList().size(); i++) {
            sb.append("Title: ");
            sb.append(library.getBorrowedBooksList().get(i).getName());
            sb.append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());

        assertEquals(sb.toString(), outputStream.toString());

        System.setOut(System.out);
    }
}



