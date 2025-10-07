package dev.lundberg.acs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BookTest {

    @Test
    public void getDaysBorrowed_shouldBeZeroWhenBookIsCreated() {
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        assertEquals(0, book.getDaysBorrowed());
    }

    @Test
    public void advanceDay_shouldIncreaseDaysBorrowed() {
        int daysBorrowed = 10;
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        assertEquals(0, book.getDaysBorrowed());
        for(int i = 0; i < daysBorrowed; i++) {
            book.advanceDay();
        }
        assertEquals(daysBorrowed, book.getDaysBorrowed());
    }

    @Test
    public void extendTime_shouldResetToZeroRegardlessToDaysBorrowed() {
        int daysBorrowed = 10;
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        for(int i = 0; i < daysBorrowed; i++) {
            book.advanceDay();
        }
        book.extendTime();
        assertEquals(0, book.getDaysBorrowed());
    }

    @Test
    public void checkLateFee_maxBorrowedDaysResultZeroFine() {
        int daysBorrowed = Book.MAX_BORROWED_DAYS;
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        for(int i = 0; i < daysBorrowed; i++) {
            book.advanceDay();
        }
        assertEquals((daysBorrowed - Book.MAX_BORROWED_DAYS) * Book.FINE_PER_DAY, book.checkLateFee());
    }

    @Test
    public void checkLateFee_moreThanMaxBorrowDays_ResultInFineForTheExtraDays() {
        int daysBorrowed = Book.MAX_BORROWED_DAYS + 14;
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
    public void IsBorrowed_shouldBeFalseWhenCreated() {
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        assertEquals(false, book.isBorrowed());
    }

    @Test
    public void BorrowBook_isBorrowedShouldBeTrueWhenBorrowed() {
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        book.borrowBook();
        assertEquals(true, book.isBorrowed());
    }

    @Test
    public void ReturnBook_isBorrowedShouldBeFalseWhenReturned() {
        Book book = new Book("Pippi Långstrump", "Barn", "Astrid Lindgren");
        book.borrowBook();
        book.returnBook();
        assertEquals(false, book.isBorrowed());
    }
}
