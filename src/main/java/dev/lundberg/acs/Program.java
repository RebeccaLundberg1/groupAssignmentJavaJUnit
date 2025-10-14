package dev.lundberg.acs;

import java.util.Scanner;

public class Program {
    Scanner scanner = new Scanner(System.in);
    Library library;

    public void run() {
        library = new Library();

        while (true) {
            System.out.println("\n Welcome to the library! \n" +
                    "--------------------------------------- \n" +
                    "B. Borrow a book \n" +
                    "R. Return a book \n" +
                    "L. List all currently borrowed books \n" +
                    "A. List all currently borrowed books by an author \n" +
                    "G. List all currently borrowed books by genre \n" +
                    "E. Extend borrowing \n" +
                    "D. Go to new day \n" +
                    "Q. Exit");
            System.out.print("> ");
            String input = scanner.nextLine();
            System.out.println("");
            

            switch (input.toUpperCase()) {
                case "B":
                    borrowBook();
                    break;
                case "R":
                    returnBook();
                    break;
                case "L":
                    library.listBorrowedBooks(true);
                    break;
                case "A":
                    library.listBorrowedBooksBy(Library.AUTHOR);
                    break;
                case "G":
                    library.listBorrowedBooksBy(Library.GENRE);
                    break;
                case "E":
                    extendTime();
                    break;
                case "D": 
                    library.advanceDay();
                    System.out.println("New day!");
                    break;
                case "Q":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Your menu choice doesn´t exists");
                    break;
            }
        }
    }

    private void extendTime() {
        System.out.println("Which book do you want to extend?");
        library.listBorrowedBooks(false);
        System.out.print("> ");

        String input = scanner.nextLine();
        library.extendTime(input);
    }

    private void returnBook() {
        System.out.println("Which book do you want to return?");
        library.listBorrowedBooks(false);
        System.out.print("> ");

        String input = scanner.nextLine();

        library.returnBook(input);
    }

    private void borrowBook() {
        System.out.println("Which book do you want to borrow?");
        library.listAvailableBooks();
        System.out.print("> ");

        String input = scanner.nextLine();
        library.borrowBook(input);
    }

}
