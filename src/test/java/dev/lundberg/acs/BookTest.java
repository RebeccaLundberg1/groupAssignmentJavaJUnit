package dev.lundberg.acs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BookTest {
    Book book;

    @org.junit.Before
    public void setUp() {
        book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");

    }

    @Test
    public void getDaysBorrowed_shouldBeZeroWhenBookIsCreated() {
        assertEquals(0, book.getDaysBorrowed());
    }

    @Test
    public void advanceDay_shouldIncreaseDaysBorrowed() {
        int daysBorrowed = 10;
        assertEquals(0, book.getDaysBorrowed());
        for(int i = 0; i < daysBorrowed; i++) {
            book.advanceDay();
        }
        assertEquals(daysBorrowed, book.getDaysBorrowed());
    }

    @Test
    public void extendTime_shouldResetDaysBorrowedToZero() {
        int daysBorrowed = 10;
        for(int i = 0; i < daysBorrowed; i++) {
            book.advanceDay();
        }
        book.extendTime();
        assertEquals(0, book.getDaysBorrowed());
    }

    @Test
    public void checkLateFee_maxBorrowedDaysResultZeroFine() {
        int daysBorrowed = Book.MAX_BORROWED_DAYS;
        for(int i = 0; i < daysBorrowed; i++) {
            book.advanceDay();
        }
        assertEquals((daysBorrowed - Book.MAX_BORROWED_DAYS) * 
            Book.FINE_PER_DAY, book.checkLateFee());
    }

    @Test
    public void checkLateFee_moreThanMaxBorrowDays_ResultInFineForTheExtraDays() {
        int daysBorrowed = Book.MAX_BORROWED_DAYS + 14;
        for(int i = 0; i < daysBorrowed; i++) {
            book.advanceDay();
        }
        assertEquals((daysBorrowed - Book.MAX_BORROWED_DAYS) * 
            Book.FINE_PER_DAY, book.checkLateFee());
    }

    @Test
    public void testGetName() {
        assertEquals("Pippi Långstrump", book.getName());
    }

    @Test
    public void testGetAuthor() {
        assertEquals("Astrid Lindgren", book.getAuthor());
    }

    @Test
    public void testGenre() {
        assertEquals("Barn", book.getGenre());
    }

    @Test
    public void isBorrowed_shouldBeFalseWhenCreated() {
        assertFalse(book.isBorrowed());
    }

    @Test
    public void borrowBook_isBorrowedShouldBeTrueWhenBorrowed() {
        book.borrowBook();
        assertTrue(book.isBorrowed());
    }

    @Test
    public void returnBook_isBorrowedShouldBeFalseWhenReturned() {
        book.borrowBook();
        book.returnBook();
        assertFalse(book.isBorrowed());
    }
}
