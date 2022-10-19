/*
 *     
 * 
 */
package za.co.nemesisnet.supreme.invention.model;

/**
 *
 * @author Peter B
 *  Load object, used to work with loan data.
 */
public class Loan {
    private String loanId;
    private String userId;
    private String ISBN;
    private String loanFromDate;
    private String dueOnDate;
    private String returnedDate;

    public Loan(User user, Book book) {
        userId = user.getUserId();
        ISBN = String.valueOf(book.getISBN());
        Dates dates = new Dates();

        loanFromDate = dates.getFormattedStartDate();
        dueOnDate = dates.getFormattedEndDate();
    }

    public Loan(String loanId, String userId, String ISBN, String loanFromDate, String dueOnDate, String returnedDate) {
        this.loanId = loanId;
        this.userId = userId;
        this.ISBN = ISBN;
        this.loanFromDate = loanFromDate;
        this.dueOnDate = dueOnDate;
        this.returnedDate = returnedDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getLoanFromDate() {
        return loanFromDate;
    }

    public void setLoanFromDate(String loanFromDate) {
        this.loanFromDate = loanFromDate;
    }

    public String getDueOnDate() {
        return dueOnDate;
    }

    public void setDueOnDate(String dueOnDate) {
        this.dueOnDate = dueOnDate;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    
}
