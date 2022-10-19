/*
 *     
 * 
 */
package za.co.nemesisnet.supreme.invention.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Calendar;

/**
 *
 * @author Peter B
 */
public class Dates {
    
    public static void main(String args[]) {
        
        Dates dates = new Dates();
        System.out.println(dates.getFormattedStartDate());
        System.out.println(dates.getFormattedEndDate());        
        
    }
    public String getFormattedCurrentDate() {
        LocalDate startingDate = LocalDate.now();
        return startingDate.toString();
        
    }
    public String getFormattedStartDate() {
        LocalDate startingDate = LocalDate.now();
        return startingDate.toString();
        
    }
    
    public String getFormattedEndDate() {
        LocalDate startingDate = LocalDate.now();
        LocalDate endingDate = startingDate.plusDays(7);
        return endingDate.toString();
        
    }
}
