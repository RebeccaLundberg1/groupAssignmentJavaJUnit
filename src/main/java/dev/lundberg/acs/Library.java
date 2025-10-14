package dev.lundberg.acs;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    private ArrayList<Book> booksInStockList;
    private ArrayList<Book> borrowedBooksList;
    private Scanner scanner;

    public static final boolean AUTHOR = true;
    public static final boolean GENRE = false;
    public static final int MAX_BORROWED_BOOKS_AT_SAME_TIME = 5;

    public Library() {
        this.booksInStockList = new ArrayList<Book>();
        this.borrowedBooksList = new ArrayList<Book>();
        scanner = new Scanner(System.in);
        stockLibrary();
    }

    public Boolean borrowBook(String title) {
        if(isBorrowBookAvailable(title)) {
            for (int i = 0; i < booksInStockList.size(); i++) {
                Book book = booksInStockList.get(i);
                if (book.getName().equalsIgnoreCase(title)) {
                    this.borrowedBooksList.add(book);
                    book.borrowBook();
                    System.out.println("You borrowed: " + title + "\n");
                    return true;
                }
            }
        }
        System.out.println("The book " + title + "isn't found\n");
        return false;
    }

    private boolean isBorrowBookAvailable(String bookName) {
        if(borrowedBooksList.size() >= 5) {
            System.out.println("Tyvärr, du har redan lånat 5 böcker \n");
            return false;
        }

        for(int i = 0; i < borrowedBooksList.size(); i++) {
            Book book = borrowedBooksList.get(i);
            if(book.getDaysBorrowed() == 0) {
                System.out.println("Tyvärr, du har redan lånat en bok idag \n");
                return false;
            }
            if(book.getName().equals(bookName)) {
                System.out.println("Tyvärr, du har redan lånat denna boken \n");
                return false;
            }
        }
        return true;
    }

    public Boolean returnBook(String title) {

        for (int i = 0; i < borrowedBooksList.size(); i++) {
            Book book = borrowedBooksList.get(i);
            if (book.getName().equalsIgnoreCase(title)) {
                int fee = book.checkLateFee();
                if(fee != 0) {
                    System.out.println("You owe us " + fee + " kr in late fees.");
                    System.out.println();
                }
                book.returnBook();
                borrowedBooksList.remove(i);
                System.out.println("Your book is returned");
                System.out.println();
                return true;
            }
        }
        System.out.println(title + " isn´t found in borrowed books list");
        System.out.println();
        return false;
    }

    public ArrayList<Book> listBorrowedBooks(boolean includeDaysBorrowed) {
        System.out.println("Your borrowed books:");
        for (int i = 0; i < borrowedBooksList.size(); i++) {
            Book book = borrowedBooksList.get(i);
            System.out.print("Title: " + book.getName());
            if (includeDaysBorrowed) {
                System.out.println(", Days borrowed: " + book.getDaysBorrowed());
            } else {
                System.out.println();
            }
        }
        System.out.println();
        return borrowedBooksList;
    }

    public int extendTime(String title) {
        for (int i = 0; i < borrowedBooksList.size(); i++) {
            Book book = borrowedBooksList.get(i);
            if (book.getName().equalsIgnoreCase(title)) {
                int fee = book.checkLateFee();
                if(fee != 0) {
                    System.out.println("You owe us " + fee + " kr in late fees.");
                    System.out.println();
                }
                book.extendTime();
                System.out.println("Your book is extended");
                System.out.println();

                return book.getDaysBorrowed();
            }
        }
        System.out.println(title + "doesn´t exists in borrowed books list");
        System.out.println();
        return Integer.MIN_VALUE;
    }

    public void listBorrowedBooksBy(boolean author) {
        if (author) {
            System.out.println("Authors: ");
            for (int i = 0; i < borrowedBooksList.size(); i++) {
                Book book = borrowedBooksList.get(i);
                System.out.println(book.getAuthor());
            }
            System.out.print("> ");

            String input = scanner.nextLine();
            for (int i = 0; i < borrowedBooksList.size(); i++) {
                Book book = borrowedBooksList.get(i);
                if (book.getAuthor().equals(input)) {
                    System.out.println("Title: " + book.getName());
                }
            }

        } else {
            System.out.println("Genres: ");
            for (int i = 0; i < borrowedBooksList.size(); i++) {
                Book book = borrowedBooksList.get(i);
                System.out.println(book.getGenre());
            }
            System.out.print("> ");

            String input = scanner.nextLine();
            for (int i = 0; i < borrowedBooksList.size(); i++) {
                Book book = borrowedBooksList.get(i);
                if (book.getGenre().equals(input)) {
                    System.out.println("Title: " + book.getName());
                }
            }
        }
    }

    public void advanceDay() {
        for (int i = 0; i < borrowedBooksList.size(); i++) {
            borrowedBooksList.get(i).advanceDay();
        }
    }

    public ArrayList<Book> listAvailableBooks() {
        for (int i = 0; i < booksInStockList.size(); i++) {
            if (!booksInStockList.get(i).isBorrowed()) {
                System.out.println("Title: " + booksInStockList.get(i).getName());
            }
        }
        return booksInStockList;
    }

    private void stockLibrary() {
        for (int i = 0; i < 5; i++) {
            Book book = new Book("Harry Potter", "Fantasy", "J.K Rowling");
            booksInStockList.add(book);
        }
        for (int i = 0; i < 10; i++) {
            Book book = new Book("Hitchhiker's guide to the galaxy", "Sci-Fi", "Douglas Adams");
            booksInStockList.add(book);
        }
        for (int i = 0; i < 3; i++) {
            Book book = new Book("It ends with us", "Romance", "Colleen Hoover");
            booksInStockList.add(book);
        }
        for (int i = 0; i < 4; i++) {
            Book book = new Book("Ondskan", "Fictional Autobiography", "Jan Guillou");
            booksInStockList.add(book);
        }
        for (int i = 0; i < 5; i++) {
            Book book = new Book("Tempelriddaren", "Historical Fiction", "Jan Guillou");
            booksInStockList.add(book);
        }
    }

    public ArrayList<Book> getBooksInStockList() {
        return booksInStockList;
    }

    public ArrayList<Book> getBorrowedBooksList() {
        return borrowedBooksList;
    }
}
