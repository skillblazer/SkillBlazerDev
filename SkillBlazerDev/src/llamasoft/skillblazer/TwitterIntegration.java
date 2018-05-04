/************************************************************
 * Application Name: skillblazer
 * File Name: TwitterIntegration.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/26/2018
 * 
 * Description:
 * 
 * This class represents the integration of Twitter into the
 * skillblazer application that will allow the user to access
 * Twitter and tweet out accomplishments that they have met
 * or when they have completed a task. The functionality 
 * will be available to them from the start using the Options
 * menu and selecting the "Submit Tweet" button.
 ***********************************************************/
// package
package llamasoft.skillblazer;

// imports
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterIntegration {

	// Consumer key token for allowing application to access Twitter
	private final static String CONSUMER_KEY = "34d1J2WyuSLYbvu4zeGLVmGRv";
	// Consumer secret token for allowing application to access Twitter
	private final static String CONSUMER_KEY_SECRET = "rmHY2LIUzvMVjGn2AGMx54rAGPx9zKSzlNpY0DSNl05mmqPjzW";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void display() throws IOException, TwitterException {

		Label instructLabel; // label for "Instructions"
		TextArea instructions; // Instructions
		Label urlLabel; // label for "Auth URL"
		TextArea authURL; // "Auth URL"
		Label pinLabel; // label for "PIN"
		TextField pinTextField; // text field for "PIN"
		Label tweetLabel; // label for "Tweet" area
		TextArea tweetTextArea; // textArea for "Tweet" area
		Button submitButton; // button for user to submit tweet

		final int MAX_CHARS = 280; // max characters for tweet

		Stage twitterWindow = new Stage();
		twitterWindow.setTitle("Twitter Integrated Application");
		twitterWindow.getIcons().add(new Image("/Twitter.jpg"));

		boolean exists = determineFileExists();

		if (exists == false) {

			// creates Twitter instance and sets consumer auth tokens for skillblazer
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
			// creates request for token from twitter for user
			RequestToken requestToken = twitter.getOAuthRequestToken();
			// creates auth URL that user will use to obtain PIN to authorize skillblazer to post
			String authorizationURL = requestToken.getAuthorizationURL();

			// hbox for instructions row
			HBox instructHbox = new HBox(10);
			instructHbox.setPadding(new Insets(15, 12, 15, 12));
			// pulls css styling information
			instructHbox.getStyleClass().add("progressButtonHboxes");
			// initializes instructLabel
			instructLabel = new Label();
			instructLabel.setTextFill(Color.web("#f5fffa"));
			// sets text for instructLabel
			instructLabel.setText("Instructions:");
			// initializes instructions
			instructions = new TextArea();
			// sets preferential size of text area
			instructions.setPrefSize(350, 150);
			// sets authURL to non-editable
			instructions.setEditable(false);
			// sets text for instructions
			instructions.setText("Please copy and paste the auth URL provided into a browser window."
								+ " Authorize skillblazer to access your Twitter page."
								+ " Copy and paste the PIN given to you in the PIN text box."
								+ " Write your Tweet and then click the Submit Tweet button.");
			instructions.setWrapText(true);
			// adds instructLabel to instructHbox
			instructHbox.getChildren().add(instructLabel);
			// adds instructions to instructHbox
			instructHbox.getChildren().add(instructions);

			// hbox for url row
			HBox urlHbox = new HBox(10);
			urlHbox.setPadding(new Insets(15, 12, 15, 12));
			// pulls css styling information
			urlHbox.getStyleClass().add("progressButtonHboxes");
			// initializes URL Label
			urlLabel = new Label();
			urlLabel.setTextFill(Color.web("#f5fffa"));
			// sets text for urlLabel
			urlLabel.setText("Auth URL:");
			// initializes authURL
			authURL = new TextArea(authorizationURL);
			// sets preferential size of text area
			authURL.setPrefSize(350, 75);
			// sets authURL to non-editable
			authURL.setEditable(false);
			// makes the authURL text area wrap text
			authURL.setWrapText(true);
			// adds components to urlHbox
			urlHbox.getChildren().add(urlLabel);
			urlHbox.getChildren().add(authURL);

			// hbox for PIN row
			HBox pinHbox = new HBox(10);
			pinHbox.setPadding(new Insets(15, 12, 15, 12));
			// pulls css styling information
			pinHbox.getStyleClass().add("progressButtonHboxes");
			// initializes pinLabel
			pinLabel = new Label();
			pinLabel.setTextFill(Color.web("#f5fffa"));
			// sets text for pinLabel
			pinLabel.setText("Enter PIN:");
			// initializes pinTextField
			pinTextField = new TextField();
			// sets max size of pinTextField
			pinTextField.setMaxSize(80, 80);

			/*
			 * This code uses a regex to make the PIN text field numeric. This
			 * means it will not accept any values but numbers.
			 * 
			 * @Author Entire changed() method below attained from
			 * www.tutorialsface.com with some specific skillblazer application
			 * alterations Source:
			 * http://www.tutorialsface.com/2016/12/how-to-make-numeric-decimal-
			 * textfield-in-javafx-example-tutorial/
			 */
			pinTextField.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d{0,7}?")) {
						pinTextField.setText(oldValue);
					}
				}
			});

			// adds components to pinHbox
			pinHbox.getChildren().add(pinLabel);
			pinHbox.getChildren().add(pinTextField);

			// hbox for tweet row
			HBox tweetHbox = new HBox(10);
			tweetHbox.setPadding(new Insets(15, 12, 15, 12));
			// pulls css styling information
			tweetHbox.getStyleClass().add("progressButtonHboxes");
			// initializes tweetLabel
			tweetLabel = new Label();
			tweetLabel.setTextFill(Color.web("#f5fffa"));
			// sets text for tweetLabel
			tweetLabel.setText("Tweet:");
			// initializes tweetTextArea
			tweetTextArea = new TextArea();
			// sets preferred size of tweetTextArea
			tweetTextArea.setPrefSize(390, 120);
			// sets limit on characters to 280 for normal tweet
			tweetTextArea.setTextFormatter(new TextFormatter<String>(
					change -> change.getControlNewText().length() <= MAX_CHARS ? change : null));
			tweetTextArea.setWrapText(true);
			// adds tweetLabel to tweetHbox
			tweetHbox.getChildren().add(tweetLabel);
			// adds tweetTextArea to tweetHbox
			tweetHbox.getChildren().add(tweetTextArea);

			// hbox for submit button row
			HBox submitTweetButtonHbox = new HBox(10);
			submitTweetButtonHbox.setPadding(new Insets(15, 12, 15, 12));
			// pulls css styling information
			submitTweetButtonHbox.getStyleClass().add("progressButtonHboxes");
			// initializes submitButton
			submitButton = new Button();
			submitButton.setStyle("-fx-background-color: #f5fffa;");
			// sets text for submitButton
			submitButton.setText("Submit Tweet");
			// event handler for submitButton
			submitButton.setOnAction(new EventHandler() {
				@Override
				public void handle(Event event) {

					try {
						// creates access token to use in authenticating user credentials
						AccessToken accessToken = null;
						// pulls PIN from pin text field
						String pin = pinTextField.getText();
						// sets access token to equal full user token given by Twitter with the PIN
						accessToken = twitter.getOAuthAccessToken(requestToken, pin);
						// creates and updates current user status on Twitter
						String tweet = tweetTextArea.getText();
						twitter.updateStatus(tweet);

						// pulls access token and access secret token from user accessToken
						String aToken = accessToken.getToken();
						String aSToken = accessToken.getTokenSecret();
						// records user home directory
						String osName = System.getProperty("os.name");
						String homePath = System.getProperty("user.home");
						
						// writes user's access token and access secret token to twitterAccessFile.txt
						// for later use by determining OS and writing to appropriate path
						BufferedWriter writeKeys = null;
						
						if (osName.contains("Windows")) {

							writeKeys = new BufferedWriter(
									new FileWriter(homePath + "\\Skillblazer\\twitterAccessFile.txt"));
				        }
				        else if (osName.contains("Mac") || osName.contains("Linux") ||
				                osName.contains("Unix")) {
				            
				        	writeKeys = new BufferedWriter(
									new FileWriter(homePath + "/Skillblazer/twitterAccessFile.txt"));
				        }
						
						writeKeys.write(aToken + "," + aSToken);
						
						// closes BufferedWriter
						writeKeys.close();
					} catch (TwitterException e) {
						
						/*
						 * Displays error window acknowleding Twitter error and asks
						 * the user to try again.
						 */
						
						Stage window = new Stage();
						window.getIcons().add(new Image("/Exclamation-mark-icon.jpg"));
						
						window.initModality(Modality.APPLICATION_MODAL);
						window.setTitle("Twitter Error");
						
						Label twitterError = new Label();
						twitterError.setText("Twitter is having unexpected server issues.");
						Label closeLab = new Label();
						closeLab.setText("Please close the window and try again in a few minutes.");
						Button closeButton = new Button ("Close");
						closeButton.setOnAction(tError -> window.close());
						
						VBox layout = new VBox(10);
						layout.setStyle("-fx-background-color: #00bfff");
						layout.setPadding(new Insets(30));
						Region emptyRegion1 = new Region();
						layout.getChildren().addAll(twitterError, closeLab, emptyRegion1, closeButton);
						layout.setAlignment(Pos.CENTER);
						
						Scene scene = new Scene(layout, 400, 150);
						window.setScene(scene);
						window.show();
					} catch (FileNotFoundException e) {
						
						/*
						 * Displays error window that file does not exist in the 
						 * Skillblazer directory.
						 */
						
						Stage window = new Stage();
						window.getIcons().add(new Image("/Exclamation-mark-icon.jpg"));
						
						window.initModality(Modality.APPLICATION_MODAL);
						window.setTitle("Twitter Error");
						
						Label fileError = new Label();
						fileError.setText("Unable to locate file.");
						Label closeLab = new Label();
						closeLab.setText("Please close the window and try again.");
						Button closeButton = new Button ("Close");
						closeButton.setOnAction(fnfError -> window.close());
						
						VBox layout = new VBox(10);
						layout.setStyle("-fx-background-color: #f5fffa");
						layout.setPadding(new Insets(30));
						Region emptyRegion1 = new Region();
						layout.getChildren().addAll(fileError, closeLab, emptyRegion1, closeButton);
						layout.setAlignment(Pos.CENTER);
						
						Scene scene = new Scene(layout, 400, 150);
						window.setScene(scene);
						window.show();
					} catch (IOException e) {
						
						/*
						 * This error window reports that it cannot write or 
						 * read the tokens from or to the twitterAccessFile.txt file.
						 */
						
						Stage window = new Stage();
						window.getIcons().add(new Image("/Exclamation-mark-icon.jpg"));
						
						window.initModality(Modality.APPLICATION_MODAL);
						window.setTitle("Twitter Error");
						
						Label ioError = new Label();
						ioError.setText("Unable to read or write to file.");
						Label closeLab = new Label();
						closeLab.setText("Please close the window and try again.");
						Button closeButton = new Button ("Close");
						closeButton.setOnAction(inouError -> window.close());
						
						VBox layout = new VBox(10);
						layout.setStyle("-fx-background-color: #f5fffa");
						layout.setPadding(new Insets(30));
						Region emptyRegion1 = new Region();
						layout.getChildren().addAll(ioError, closeLab, emptyRegion1, closeButton);
						layout.setAlignment(Pos.CENTER);
						
						Scene scene = new Scene(layout, 400, 150);
						window.setScene(scene);
						window.show();
					}

					twitterWindow.close(); // closes window
				}
			}); // end event handler

			// centers hbox that submitButton is placed in
			submitTweetButtonHbox.setAlignment(Pos.CENTER);
			// adds submitButton to submitTweetButtonHbox
			submitTweetButtonHbox.getChildren().add(submitButton);

			// new vbox layout
			VBox twitterVbox = new VBox(20);
			// pulls css specs from style sheet
			twitterVbox.getStyleClass().add("secondaryWindow");
			twitterVbox.setStyle("-fx-background: #00bfff;");
			// adds all of the hboxes to twitterVbox
			twitterVbox.getChildren().add(instructHbox);
			twitterVbox.getChildren().add(urlHbox);
			twitterVbox.getChildren().add(pinHbox);
			twitterVbox.getChildren().add(tweetHbox);
			twitterVbox.getChildren().add(submitTweetButtonHbox);
			
			// adds twitterVbox to twitterScene
			Scene twitterScene = new Scene(twitterVbox, 500, 450);
			// adds twitterScene to twitterWindow
			twitterWindow.setScene(twitterScene);
			// shows the stage; actually displays the scene
			twitterWindow.show();
		} else if (exists == true) {

			// hbox for instructions row
			HBox instructHbox = new HBox(5);
			instructHbox.setPadding(new Insets(15, 12, 15, 12));
			// pulls css styling information
			instructHbox.getStyleClass().add("progressButtonHboxes");
			// initializes instructLabel
			instructLabel = new Label();
			instructLabel.setTextFill(Color.web("#f5fffa"));
			// sets text for instructLabel
			instructLabel.setText("Instructions:");
			// initializes instructions
			instructions = new TextArea();
			// sets preferential size of text area
			instructions.setPrefSize(350, 75);
			// sets authURL to non-editable
			instructions.setEditable(false);
			// sets text for instructions
			instructions.setText("You have already authorized this application to post to your Twitter profile."
					+ " Write and submit Tweet below.");
			instructions.setWrapText(true);
			// adds instructLabel to instructHbox
			instructHbox.getChildren().add(instructLabel);
			// adds instructions to instructHbox
			instructHbox.getChildren().add(instructions);

			// hbox for tweet row
			HBox tweetHbox = new HBox(10);
			tweetHbox.setPadding(new Insets(15, 12, 15, 12));
			// pulls css styling information
			tweetHbox.getStyleClass().add("progressButtonHboxes");
			// initializes tweetLabel
			tweetLabel = new Label();
			tweetLabel.setTextFill(Color.web("#f5fffa"));
			// sets text for tweetLabel
			tweetLabel.setText("Tweet:");
			// initializes tweetTextArea
			tweetTextArea = new TextArea();
			// sets preferred size of tweetTextArea
			tweetTextArea.setPrefSize(390, 120);
			// sets limit on characters to 280 for normal tweet
			tweetTextArea.setTextFormatter(new TextFormatter<String>(
					change -> change.getControlNewText().length() <= MAX_CHARS ? change : null));
			tweetTextArea.setWrapText(true);
			// adds tweetLabel to tweetHbox
			tweetHbox.getChildren().add(tweetLabel);
			// adds tweetTextArea to tweetHbox
			tweetHbox.getChildren().add(tweetTextArea);

			// hbox for submit button row
			HBox submitTweetButtonHbox = new HBox(10);
			submitTweetButtonHbox.setPadding(new Insets(15, 12, 15, 12));
			// pulls css styling information
			submitTweetButtonHbox.getStyleClass().add("progressButtonHboxes");
			// initializes submitButton
			submitButton = new Button();
			submitButton.setStyle("-fx-background-color: #f5fffa;");
			// sets text for submitButton
			submitButton.setText("Submit Tweet");
			// event handler for submitButton
			submitButton.setOnAction(new EventHandler() {
				@Override
				public void handle(Event event) {
								
					try {
						
						// records user home directory and OS
						String osName = System.getProperty("os.name");
						String homePath = System.getProperty("user.home");
						BufferedReader readKeys = null;
						
						// reads user access keys from twitterAccessFile.txt to authenticate to Twitter
						// by determining OS and then reading the file from the correct path
						if (osName.contains("Windows")) {

							readKeys = new BufferedReader(
									new FileReader(homePath + "\\Skillblazer\\twitterAccessFile.txt"));
				        }
				        else if (osName.contains("Mac") || osName.contains("Linux") ||
				                osName.contains("Unix")) {
				            
				        	readKeys = new BufferedReader(
									new FileReader(homePath + "/Skillblazer/twitterAccessFile.txt"));
				        }
						
						String keys = readKeys.readLine();
						
						// splits string pulled from file to separate both keys
						String[] arOfKeys = keys.split(",");

						// creates Twitter instance and sets consumer access keys for skillblazer
						Twitter twitterReuse = new TwitterFactory().getInstance();
						twitterReuse.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
						// sets user access keys from string array pulled earlier
						final String accessKey = arOfKeys[0];
						final String accessSKey = arOfKeys[1];
						// creates auth token to be used to authenticate to Twitter servers
						AccessToken oathAccessToken = new AccessToken(accessKey, accessSKey);
						twitterReuse.setOAuthAccessToken(oathAccessToken);
						// sends tweet using twitter instance with auth token
						String tweet = tweetTextArea.getText();
						twitterReuse.updateStatus(tweet);
						
						// close BufferedReader
						readKeys.close();
					} catch (IOException e) {
						
						/*
						 * Displays error window that indicates that the program
						 * cannot read the tokens from the twitterAccessFile.txt file.
						 */
						
						Stage window = new Stage();
						window.getIcons().add(new Image("/Exclamation-mark-icon.jpg"));
						
						window.initModality(Modality.APPLICATION_MODAL);
						window.setTitle("Twitter Error");
						
						Label ioError = new Label();
						ioError.setText("Unable to read or write to file.");
						Label closeLab = new Label();
						closeLab.setText("Please close the window and try again.");
						Button closeButton = new Button ("Close");
						closeButton.setOnAction(inouError -> window.close());
						
						VBox layout = new VBox(10);
						layout.setStyle("-fx-background-color: #f5fffa");
						layout.setPadding(new Insets(30));
						Region emptyRegion1 = new Region();
						layout.getChildren().addAll(ioError, closeLab, emptyRegion1, closeButton);
						layout.setAlignment(Pos.CENTER);
						
						Scene scene = new Scene(layout, 400, 150);
						window.setScene(scene);
						window.show();
					} catch (TwitterException e) {
						
						/*
						 * Displays error window that tells the user that the 
						 * tweet did not go through and to please try again.
						 */
						
						Stage window = new Stage();
						window.getIcons().add(new Image("/Exclamation-mark-icon.jpg"));
						
						window.initModality(Modality.APPLICATION_MODAL);
						window.setTitle("Twitter Error");
						
						Label twitterError = new Label();
						twitterError.setText("Twitter is having unexpected server issues.");
						Label closeLab = new Label();
						closeLab.setText("Please close the window and try again in a few minutes.");
						Button closeButton = new Button ("Close");
						closeButton.setOnAction(tError -> window.close());
						
						VBox layout = new VBox(10);
						layout.setStyle("-fx-background-color: #00bfff");
						layout.setPadding(new Insets(30));
						Region emptyRegion1 = new Region();
						layout.getChildren().addAll(twitterError, closeLab, emptyRegion1, closeButton);
						layout.setAlignment(Pos.CENTER);
						
						Scene scene = new Scene(layout, 400, 150);
						window.setScene(scene);
						window.show();
					}

					twitterWindow.close(); // closes window
				}
			}); // end event handler

			// centers hbox that submitButton is placed in
			submitTweetButtonHbox.setAlignment(Pos.CENTER);
			// adds submitButton to submitTweetButtonHbox
			submitTweetButtonHbox.getChildren().add(submitButton);

			// new vbox layout
			VBox twitterVbox = new VBox(20);
			// pulls css specs from style sheet
			twitterVbox.getStyleClass().add("secondaryWindow");
			twitterVbox.setStyle("-fx-background: #00bfff;");
			// adds all of the hboxes to twitterVbox
			twitterVbox.getChildren().add(instructHbox);
			twitterVbox.getChildren().add(tweetHbox);
			twitterVbox.getChildren().add(submitTweetButtonHbox);

			// adds twitterVbox to twitterScene
			Scene twitterScene = new Scene(twitterVbox, 500, 320);
			// adds twitterScene to twitterWindow
			twitterWindow.setScene(twitterScene);
			// shows the stage; actually displays the scene
			twitterWindow.show();
		}

	} // end of display method

	private static boolean determineFileExists() throws IOException {
		// determines OS of current user
		String osName = System.getProperty("os.name");
		//determines home directory of current user
		String homePath = System.getProperty("user.home");
		//temp file
		File tmpFile = null;
		
		//determines correct file path depending on which OS is being used
		if (osName.contains("Windows")) {

			tmpFile = new File(homePath + "\\SkillBlazer\\twitterAccessFile.txt");
        }
        else if (osName.contains("Mac") || osName.contains("Linux") ||
                osName.contains("Unix")) {
            
        	tmpFile = new File(homePath + "/SkillBlazer/twitterAccessFile.txt");
        }
		
		//determines if file exists at the folder path above
		boolean exists = tmpFile.exists();
		
		//if file doesn't exist, create that file in the Skillblazer directory
		if (exists == false) {
			tmpFile.createNewFile();
		}

		return exists;

	} // end of determineFileExists method
} // end of TwitterIntegration class
