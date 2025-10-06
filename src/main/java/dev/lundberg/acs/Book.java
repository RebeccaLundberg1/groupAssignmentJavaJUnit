package dev.lundberg.acs;

public class Book {

    public static final int MAX_BORROWED_DAYS = 7;
    public static final int FINE_PER_DAY = 20;

    private String name;
    private String genre;
    private String author;
    private boolean borrowed;
    private int daysBorrowed;

    public Book(String name, String genre, String author) {
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.borrowed = false;
        this.daysBorrowed = 0;
    }

    public int checkLateFee() {
        if (daysBorrowed > MAX_BORROWED_DAYS) {
            System.out.println("The book is " + daysBorrowed + " days late");
            return (daysBorrowed - MAX_BORROWED_DAYS) * FINE_PER_DAY;
        }
        return 0;
    }

    public void extendTime() {
        this.daysBorrowed = 0;
    }

    public void advanceDay() {
        this.daysBorrowed++;
    }

    public void borrowBook() {
        this.borrowed = true;
    }

    public void returnBook() {
        this.borrowed = false;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public int getDaysBorrowed() {
        return daysBorrowed;
    }
}
