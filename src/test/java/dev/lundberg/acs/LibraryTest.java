package dev.lundberg.acs;
import static org.junit.Assert.assertEquals;
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
    public void listAvailableBooks_printAllAvailableBooksInStock() {
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

