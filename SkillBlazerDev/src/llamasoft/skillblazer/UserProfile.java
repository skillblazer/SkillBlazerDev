/************************************************************
 * Application Name: skillblazer
 * File Name: UserProfile.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/24/2018
 * 
 * Description:
 * 
 * This class will contain information about the user as well
 * as obtain the current day when the user accesses the 
 * application.
 ***********************************************************/

package llamasoft.skillblazer;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.scene.control.Label;

public class UserProfile {

    private final String type = "userProfile"; //type of file
    private String preferredSaveLocation; //custom preferred save location if user wants
    private String userName = ""; //username for user
    Calendar userStartDate = new GregorianCalendar(); //start date that user began using skillblazer
    long taskNumber; //increment number for task ID available

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
     * Overloaded Class Constructor to instantiate userName and userStartDate and taskNumber
     * added by JME 13APR
     */
    public UserProfile(String userName, Calendar userStartDate, long taskNumber) {
        this.userName = userName;
        this.userStartDate = userStartDate;
        this.taskNumber = taskNumber;
    } //end UserProfile constructor

    public void setUserName(String newName) {
        this.userName = newName;
    }

    @Override
    public String toString() {
        return "UserProfile info is: \nUserName: " + this.userName +
                "\nLast Task Number created: " + this.taskNumber +
                "\nCalender info is as follows: \n" + this.userStartDate.toString();
    } //end method toString()

    /*
     * Calculates the current date and places in a mm/dd/yyyy format
     * for the user to read at the top left of the primary GUI window
     */
    public String calculateCurrentDate() {
    	DateFormat currentDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    	Calendar currentCalDate = Calendar.getInstance();
    	
    	String currentDate = currentDateFormat.format(currentCalDate.getTime());
    	
    	return currentDate;
    } //end calculateCurrentDate method
    
    /*
     * Determines if the folder for the skillblazer app has been created
     * and records the folder path for where to create new JSON files
     * for persistence of data.
     */
    public String determineOSFilePath() {
    	
    	String storageFolderPath = ""; //default initialization - path to send json data
    	File file = new File(""); //default initialization - will become directory to store data
    	
    	String osName = System.getProperty("os.name"); //pulls OS name
    	String homePath = System.getProperty("user.home"); //pulls home directory
    	
    	if (osName.contains("Windows") || osName.contains("Mac") || osName.contains("Linux") || 
    			osName.contains("Unix")) {
    		
    		file = new File(homePath + "\\Skillblazer");
    		//if folder doesn't exist, create skillblazerApp directory
            if (!file.exists()) {
                file.mkdir();
            }
            
    		storageFolderPath = (homePath + "\\Skillblazer");
    	}
    	
    	//if path returns empty, then window will need to appear stating that
    	//the OS they are using cannot use this application
    	return storageFolderPath; 
    } //end determineOSFilePath method

    /*
     * Increments taskNumber by one to provide
     * correct file names for JSON data files.
     */
    public long incrementTaskNumber(long taskNum) {
    	
        long taskNumber = taskNum;
    	
    	taskNumber = taskNumber + 1;
    	
    	return taskNumber;
    }
    
    /*
     * Determines username of user accessing skillblazer
     * application and presents it.
     */
    public Label determineUsername() {
    	  	
       	Label userNameLabel;
    	String userName;		
    	String homePath = System.getProperty("user.home"); //pulls home directory
    	String[] arOfKeys = homePath.split(":?\\\\"); //parses folder path
    	userName = arOfKeys[2]; //takes username from desktop path
    	userNameLabel = new Label(userName); 
    	
    	return userNameLabel; //returns Label with user name 
    } //end determineOSFilePath method
    
    /*
     * Accessor method - type
     */
    public String getType() {
        return this.type;
    } //end getType method

    /*
     * Accessor Method - userName
     */
    public String getUserName() {
        return this.userName;
    } //end getUserName method

    /*
     * Accessor Method - userStartDate
     */
    public Calendar getUserStartDate() {
        return this.userStartDate;
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
    
    /*
     * Mutator Method - preferredSaveLocation
     */
    public void setPreferredSaveLocation(String preferredSaveLocation) {
        this.preferredSaveLocation = preferredSaveLocation;
    } //end setPreferredSaveLocation method

    /*
     * Accessor Method - preferredSaveLocation
     */
    public String getPreferredSaveLocation(UserProfile userProfile) {
        return this.preferredSaveLocation;
    } //end getPreferredSaveLocation method
} //end UserProfile class
