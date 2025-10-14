package dev.lundberg.acs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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

    public void testReturnBook_noLateFeeWhenReturnedOnTime() {
    Library library = new Library();
    library.borrowBook("Harry Potter");

    int lateFee = library.returnBook("Harry Potter");

    assertEquals(0, lateFee);
    }

    @Test

    public void testReturnBook_resetsBorrowedStatusAndCalculatesLateFee() {
        // Arrange
        Library library = new Library();
        library.borrowBook("Harry Potter");
        ArrayList<Book> borrowedBooks = library.listBorrowedBooks(false);

        // Anta att vi har minst en Harry Potter-bok
        Book borrowedBook = borrowedBooks.get(0);

        // Simulera att boken är sen (t.ex. 10 dagar)
        for (int i = 0; i < 10; i++) {
            borrowedBook.advanceDay();
        }

        // Act
        int lateFee = library.returnBook("Harry Potter");

        // Assert
        assertEquals(60, lateFee);
        assertFalse(borrowedBook.isBorrowed());
    }

    @Test
    public void listAvailableBooksPrintAllAvailableBooksInStock() {
        Library library = new Library();
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
        Library library = new Library();

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
}

