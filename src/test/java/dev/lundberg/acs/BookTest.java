package dev.lundberg.acs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BookTest {
    @Test
    public void testAdvanceDay() {
        int daysBorrowed = 10;
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        assertEquals(0, book.getDaysBorrowed());
        for(int i = 0; i < daysBorrowed; i++) {
            book.advanceDay();
        }
        assertEquals(daysBorrowed, book.getDaysBorrowed());
    }

    @Test
    public void testExtendTime() {
        int daysBorrowed = 10;
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        for(int i = 0; i < daysBorrowed; i++) {
            book.advanceDay();
        }
        book.extendTime();
        assertEquals(0, book.getDaysBorrowed());
    }

    @Test
    public void testCheckLateFee() {
        int daysBorrowed = 20;
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        for(int i = 0; i < daysBorrowed; i++) {
            book.advanceDay();
        }
        assertEquals((daysBorrowed - Book.MAX_BORROWED_DAYS) * Book.FINE_PER_DAY, book.checkLateFee());
    }

    @Test
    public void testGetName() {
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        assertEquals("Pippi Långstrump", book.getName());
    }

    @Test
    public void testGetAuthor() {
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        assertEquals("Astrid Lindgren", book.getAuthor());
    }

    @Test
    public void testGenre() {
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        assertEquals("Barn", book.getGenre());
    }

    @Test
    public void testIsBorrowed() {
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        assertEquals(false, book.isBorrowed());
    }

    @Test
    public void testBorrowBook() {
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        book.borrowBook();
        assertEquals(true, book.isBorrowed());
    }

    @Test
    public void testReturnBook() {
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        book.returnBook();
        assertEquals(false, book.isBorrowed());
    }

}
