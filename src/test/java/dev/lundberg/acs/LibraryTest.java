package dev.lundberg.acs;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;


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

}