package dev.lundberg.acs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;

public class LibraryTest {
    
    Library library;

    Book book;
    Book book2;
    String bookTitle;
    String bookTitle2;

    @org.junit.Before
    public void setUp() {
        library = new Library();
        book = library.getBooksInStockList().get(0);
        bookTitle = book.getName();
        book2 = library.getBooksInStockList().get(9);
        bookTitle2 = book2.getName();
    }

    @Test
    public void BorrowBook_isBorrowedShouldBeTrueWhenBorrowed() {
        library.borrowBook(bookTitle);
        assertTrue(book.isBorrowed());
    }

    @Test
    public void returnBook_isBorrowedShouldBeFalseWhenReturned() {
        library.borrowBook(bookTitle);
        library.returnBook(bookTitle);

        assertFalse(book.isBorrowed());
    }

    @Test
    public void borrowBook_checkBookAddsToBorrowBooksArray() {
        // lånar en bok som finns i biblioteket
        library.borrowBook(bookTitle);
        boolean found = false;

        // Kontrollera att Ondskan finns i lånelistan
        for (Book book : library.getBorrowedBooksList()) { 
            if (book.getName().equalsIgnoreCase(bookTitle)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void shouldResultInFalse_ifAddMoreBooksThen_maxBorrowedBooksAtTime_inArrayList() {
        ArrayList<Book> listBorrowedBooks = library.getBorrowedBooksList();
        
        //Fill up the space in arrayList with maximum books borrowed at the same time
        for(int i = 0; i < Library.MAX_BORROWED_BOOKS_AT_SAME_TIME; i++) {
            Book book = new Book("Titel", "Genre", "Author");
            listBorrowedBooks.add(book);
        }

        //Equals false when try add one more book to list 
        assertFalse(library.borrowBook(bookTitle));
    }

    //@Test
    //public void returnBook_noLateFeeWhenReturnedOnTime() {
    //    library.borrowBook(bookTitle);
    //    Book book = library.getBorrowedBooksList().get(0);
//
    //    int lateFee = book.checkLateFee();
    //    assertEquals(0, lateFee);
    //}
//
    //@Test
    //public void returnBook_fineWhenReturnedThreeDaysLate_resultSixty() {
    //    int daysBorrowed = Book.MAX_BORROWED_DAYS + 3;
//
    //    library.borrowBook(bookTitle);
//
    //    for(int i = 0; i < daysBorrowed; i++) {
    //        library.advanceDay();
    //    }
//
    //    //int lateFee = library.returnBook(bookTitle);
    //    //assertEquals(60, lateFee);
    //}

    @Test
    public void returnMultipleBooksInOneDay() {
        ArrayList<Book> listBorrowedBooks = library.getBorrowedBooksList();
        
        //Fill up the space in arrayList with maximum books borrowed at the same time
        for(int i = 0; i < Library.MAX_BORROWED_BOOKS_AT_SAME_TIME; i++) {
            Book book = new Book("Titel" + i, "Genre", "Author");
            listBorrowedBooks.add(book);
        }

        library.returnBook(listBorrowedBooks.get(0).getName());
        
        assertTrue(library.returnBook(listBorrowedBooks.get(0).getName()));
    }

    @Test
    public void extendTime_daysResetToZero_ifBookIsBorrowed() {
        library.borrowBook(bookTitle);
        library.advanceDay();
        library.advanceDay();
        library.advanceDay();
        assertEquals(0, library.extendTime(bookTitle));
    }

    @Test
    public void extendTime_resultInIntegerMinValue_ifBookIsNotBorrowed() {
        library.borrowBook(bookTitle);
        library.advanceDay();
        library.advanceDay();
        library.advanceDay();
        assertEquals(Integer.MIN_VALUE, library.extendTime(bookTitle2));
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
    public void listBorrowedBooksByAuthor() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        String simulatedInput = "J.K Rowling\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Library library = new Library();
        library.borrowBook("Harry Potter");
        library.advanceDay();
        library.borrowBook("Ondskan");
        library.advanceDay();
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
    public void listBorrowedBooksByGenre() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        String simulatedInput = "Fantasy\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Library library = new Library();
        library.borrowBook("Harry Potter");
        library.advanceDay();
        library.borrowBook("Ondskan");
        library.advanceDay();
        library.borrowBook("Tempelriddaren");
        
        library.listBorrowedBooksBy(false);
        
        StringBuilder sb = new StringBuilder();
        sb.append("Genres: ");
        sb.append(System.lineSeparator());
        for(int i = 0; i < library.getBorrowedBooksList().size(); i++) {
            sb.append(library.getBorrowedBooksList().get(i).getGenre());
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
    @Test
    public void cannotBorrowSameBookTwice() {
        library.borrowBook(bookTitle);
        assertFalse(library.borrowBook(bookTitle));    
    }

    @Test
    public void borrowOneBook_shouldResult_sizeOne(){
        library.borrowBook(bookTitle);
        assertEquals(1, library.getBorrowedBooksList().size());
    }
    @Test
    public void borrowTwoBooksSameDay(){
        library.borrowBook(bookTitle);
        assertFalse(library.borrowBook(bookTitle2));

    }
    @Test
    public void BorrowBooksDifferentDay(){
        library.borrowBook(bookTitle);
        library.advanceDay();
        assertTrue(library.borrowBook(bookTitle2));
    }
    
}

