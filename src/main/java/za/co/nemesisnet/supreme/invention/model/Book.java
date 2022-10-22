/*
 *     
 * 
 */
package za.co.nemesisnet.supreme.invention.model;

import java.awt.Image;
import java.io.Serializable;

/**
 *
 * @author Peter Buckingham
 *
 * Book class o Possible categories for fiction are adventure, comics, fairy tales, fantasy, historical, mystery, romance, science fiction, war. What about non-fiction? o To keep things simple, you may model the following:  only ONE category for a book  only ONE author writes a book  the isbn is the primary key for the book  the book has a shelf number in the library  do not bother about catering for different library items like, periodicals, newspapers, magazines, books, (.pdf) eBooks and so forth.  <<Lecturer may add more>> other things like storing a picture for a book etc?
 *
 *
 */
public class Book implements Serializable {

    private int bookshelfNumber;
    private String title;
    private String ISBN;
    private String authors;
    private String category;
    private boolean availableForLoan;

    public Book() {
    }

    public Book(String ISBN, String title, String author, String category, boolean availableForLoan) {

        this.title = title;

        this.ISBN = ISBN;
        this.authors = author;

        this.category = category;

        this.availableForLoan = availableForLoan;
    }

    public boolean isAvailableForLoan() {
        return availableForLoan;
    }

    public void setAvailableForLoan(boolean availableForLoan) {
        this.availableForLoan = availableForLoan;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getBookshelfNumber() {
        return bookshelfNumber;
    }

    public void setBookshelfNumber(int bookshelfNumber) {
        this.bookshelfNumber = bookshelfNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        if (ISBN == null) {
            ISBN = "NO ISBN";
        }
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

}
