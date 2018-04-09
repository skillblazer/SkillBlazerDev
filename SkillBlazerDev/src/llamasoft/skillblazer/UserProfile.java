/************************************************************
 * Application Name: skillblazer
 * File Name: UserProfile.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/8/2018
 * 
 * Description:
 * 
 * This class will contain information about the user as well
 * as obtain the current day when the user accesses the 
 * application.
 ***********************************************************/

package llamasoft.skillblazer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserProfile {

	private String userName = ""; //
    private Calendar userStartDate; //
    public long taskNumber = 0; //increment number for task ID available

    /*
     * Default Class Constructor
     */
    public UserProfile() {
    	//Default Constructor - no implementation here
    }
    
    /*
     * 
     */
    public UserProfile(String userName) {
    	this.userName = userName;
    }
    
    /*
     * 
     */
    public UserProfile(String userName, Calendar userStartDate) {
        this.userName = userName;
        this.userStartDate = userStartDate;
    }

    /*
     * 
     */
    public String getCurrentDate() {
    	DateFormat currentDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    	Calendar currentCalDate = Calendar.getInstance();
    	
    	String currentDate = currentDateFormat.format(currentCalDate.getTime());
    	
    	return currentDate;
    }
    
    /*
     * 
     */
    public String getUserName() {
        return userName;
    }

    /*
     * 
     */
    public Calendar getUserStartDate() {
        return userStartDate;
    }
    
    /*
     * 
     */
    public long getTaskNumber() {
    	return taskNumber;
    }
    
    /*
     * 
     */
    public void setTaskNumber(long taskNumber) {
    	this.taskNumber = taskNumber;
    }
}
