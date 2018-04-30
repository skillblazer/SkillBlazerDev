/************************************************************
 * Application Name: skillblazer
 * File Name: CumulativeHistoryStruct.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/28/2018
 * 
 * Description:
 * 
 * This class represents a unique data structure for the cumulative
 * tasks for Skillblazer. These tasks will not utilize the ArrayList
 * daysCompleted that the other types of task will use. Instead it will
 * use an ArrayList formed of objects of this class. Member fields for
 * this class include a Calendar object holding the date and a double
 * representing goal progress.
 ***********************************************************/
// package
package llamasoft.skillblazer;
// import
import java.util.Calendar;

public class CumulativeHistoryStruct {
    
    // member fields
    public Calendar date;               // calendar object holding the date
    public double progress;             // double field holding progress made toward goal

    // constructor
    CumulativeHistoryStruct() {
        date = null;
        progress = 0.0;
    } // end CumulativeHistoryStruct constructor

    // constructor
    public CumulativeHistoryStruct(Calendar date,double progress) {
        this.date = date;
        this.progress = progress;
    } // end CumulativeHistoryStruct constructor

    // Overriding equals() method to compare dates
    @Override
    public boolean equals(Object o) {
        if (o instanceof CumulativeHistoryStruct) {
            return date.equals(((CumulativeHistoryStruct)o).date);
        } else {
            return false;
        }
    } // end equals() method
    
} // end class CumulativeHistoryStruct
