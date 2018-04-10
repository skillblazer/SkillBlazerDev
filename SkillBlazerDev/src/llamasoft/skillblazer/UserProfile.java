/************************************************************
 * Application Name: skillblazer
 * File Name: UserProfile.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/9/2018
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

	private String userName = ""; //username for user
    private Calendar userStartDate; //start date that user began using skillblazer
    public long taskNumber = 0; //increment number for task ID available

    /*
     * Default Class Constructor
     */
    public UserProfile() {
    	//Default Constructor - no implementation here
    } //end UserProfile constructor
    
    /*
     * Overloaded Class Constructor to instantiate userName
     */
    public UserProfile(String userName) {
    	this.userName = userName;
    } //end UserProfile constructor
    
    /*
     * Overloaded Class Constructor to instantiate userName and userStartDate
     */
    public UserProfile(String userName, Calendar userStartDate) {
        this.userName = userName;
        this.userStartDate = userStartDate;
    } //end UserProfile constructor

    /*
     * Calculates the current date and places in a mm/dd/yyyy format
     * for the user to read at the top left of the primary GUI window
     */
    public String getCurrentDate() {
    	DateFormat currentDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    	Calendar currentCalDate = Calendar.getInstance();
    	
    	String currentDate = currentDateFormat.format(currentCalDate.getTime());
    	
    	return currentDate;
    } //end getCurrentDate method
    
    /*
     * Accessor Method - userName
     */
    public String getUserName() {
        return userName;
    } //end getUserName method

    /*
     * Accessor Method - userStartDate
     */
    public Calendar getUserStartDate() {
        return userStartDate;
    } //end getUserStartDate method
    
    /*
     * Accessor Method - taskNumber
     */
    public long getTaskNumber() {
    	return taskNumber;
    } //end getTaskNumber method
    
    /*
     * Mutator Method - taskNumber
     */
    public void setTaskNumber(long taskNumber) {
    	this.taskNumber = taskNumber;
    } //end setTaskNumber method
} //end UserProfile class
