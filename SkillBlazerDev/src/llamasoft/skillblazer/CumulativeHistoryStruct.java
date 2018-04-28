package llamasoft.skillblazer;

import java.util.Calendar;

public class CumulativeHistoryStruct {
    public Calendar date;               // calendar object holding the date
    public double progress;             // double field holding progress made toward goal

    // constructor
    CumulativeHistoryStruct() {
        date = null;
        progress = 0.0;
    }

    // constructor
    public CumulativeHistoryStruct(Calendar date,double progress) {
        this.date = date;
        this.progress = progress;
    }

    // Overriding equals() method to compare dates
    @Override
    public boolean equals(Object o) {
        if (o instanceof CumulativeHistoryStruct) {
            return date.equals(((CumulativeHistoryStruct)o).date);
        } else {
            return false;
        }
    } // end equals() method
}
