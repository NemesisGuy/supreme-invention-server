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
 * Book class o Possible categories for fiction are adventure, comics, fairy tales, fantasy, historical, mystery, romance, science fiction, war. 
 * What about non-fiction? o To keep things simple, you may model the following:  only ONE category for a book  only ONE author writes a book  
 * the isbn is the primary key for the book  the book has a shelf number in the library  do not bother about catering for different library items like, periodicals, newspapers, magazines, books, (.pdf) eBooks and so forth.
 *  <<Lecturer may add more>> other things like storing a picture for a book etc?
 *
 *
 */
public class Book implements Serializable{

    private int bookshelfNumber;
    private String title;
    private String subTitle;
    private String ISBN;
    private String authors;
    private String description;
    private String category;
    private String imageLink;
    private boolean availableForLoan;

    public Book() {
    }

    public Book(int bookshelfNumber, String title, String subTitle, String ISBN, String authors, String description, String category, String imageLink, boolean availableForLoan) {
        this.bookshelfNumber = bookshelfNumber;
        this.title = title;
        this.subTitle = subTitle;
        this.ISBN = ISBN;
        this.authors = authors;
        this.description = description;
        this.category = category;
        this.imageLink = imageLink;
        this.availableForLoan = availableForLoan;
    }
    

    public boolean isAvailableForLoan() {
        return availableForLoan;
    }

    public void setAvailableForLoan(boolean availableForLoan) {
        this.availableForLoan = availableForLoan;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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
            ISBN = "book isbn getter set this";
        }
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    



}
