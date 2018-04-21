/************************************************************
 * Application Name: skillblazer
 * File Name: TwitterIntegration.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/16/2018
 * 
 * Description:
 * 
 * This class represents the integration of Twitter into the
 * skillblazer application that will allow the user to access
 * Twitter and tweet out accomplishments that they have met
 * or when they have completed a task. The functionality 
 * will be available to them from the start using the Options
 * menu and selecting the "Tweet" button.
 ***********************************************************/

package llamasoft.skillblazer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Application;
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

public class TwitterIntegration extends Application {

	// Consumer key token for allowing application to access Twitter
	private final static String CONSUMER_KEY = "34d1J2WyuSLYbvu4zeGLVmGRv";
	// Consumer secret token for allowing application to access Twitter
	private final static String CONSUMER_KEY_SECRET = "rmHY2LIUzvMVjGn2AGMx54rAGPx9zKSzlNpY0DSNl05mmqPjzW";

	Stage window;
	Button button;

	public static void main(String[] args) throws IOException {
		launch(args);
	} //end of main method

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Test Window");

		button = new Button("Click me");

		button.setOnAction(e -> {
			try {
				display();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		StackPane layout = new StackPane();
		layout.getChildren().add(button);
		Scene scene = new Scene(layout, 300, 250);
		window.setScene(scene);
		window.show();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void display() throws IOException {

		Label instructLabel; // label for "Instructions"
		Label instructions; // "Instructions"
		Label urlLabel; // label for "Auth URL"
		Label authURL; // "Auth URL"
		Label pinLabel; // label for "PIN"
		TextField pinTextField; // text field for "PIN"
		Label tweetLabel; // label for "Tweet" area
		TextArea tweetTextArea; // textArea for "Tweet" area
		Button submitButton; // button for user to submit tweet

		final int MAX_CHARS = 280;
		String authorizationURL = "";
		String pinCode = "";

		Stage twitterWindow = new Stage();
		twitterWindow.setTitle("Twitter Integrated Application");
		twitterWindow.getIcons().add(new Image("/Twitter.jpg"));

		boolean exists = determineFileExists();

		if (exists == false) {

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
			instructions = new Label();
			instructions.setTextFill(Color.web("#f5fffa"));
			// sets text for instructions
			instructions.setText("These are the instructions.");
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
			authURL = new Label();
			authURL.setTextFill(Color.web("#f5fffa"));
			// sets text for URL for user to use - EXAMPLE WILL REPLACE
			authURL.setText("https://twitter.auth.url.example565333424545234234.com");
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

					// TODO: Perform twitter integration code
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
			instructions = new Label();
			instructions.setTextFill(Color.web("#f5fffa"));
			// sets text for instructions
			instructions.setText("You have already authorized this application to post to your Twitter profile.\n"
					+ "Write and submit Tweet below.");
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

						String homePath = System.getProperty("user.home");

						BufferedReader readKeys = new BufferedReader(
								new FileReader(homePath + "\\Skillblazer\\twitterAccessFile.txt"));
						String keys = readKeys.readLine();
						String[] arOfKeys = keys.split(",");

						Twitter twitterReuse = new TwitterFactory().getInstance();
						twitterReuse.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
						final String accessKey = arOfKeys[0];
						final String accessSKey = arOfKeys[1];
						AccessToken oathAccessToken = new AccessToken(accessKey, accessSKey);
						twitterReuse.setOAuthAccessToken(oathAccessToken);
						String tweet = tweetTextArea.getText();
						twitterReuse.updateStatus(tweet);

						readKeys.close();
					} catch (IOException e) {
						// TODO ADD ERROR WINDOW
						System.out.println("Could not read file!");
					} catch (TwitterException e) {
						// TODO ADD ERROR WINDOW
						System.out.println("Could not send tweet. Try again!");
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

	} //end of display method

	private static boolean determineFileExists() throws IOException {
		// read user tokens from designated file
		String homePath = System.getProperty("user.home");
		File tmpFile = new File(homePath + "\\Skillblazer\\twitterAccessFile.txt");
		boolean exists = tmpFile.exists();

		if (exists == false) {
			tmpFile.createNewFile();
		}

		return exists;
	} //end of determineFileExists method

	/*
	 * 
	 */
	private void twitterInt() throws TwitterException, IOException {

		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		RequestToken requestToken = twitter.getOAuthRequestToken();
		System.out.println("Authorization URL: \n" + requestToken.getAuthorizationURL());

		AccessToken accessToken = null;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (null == accessToken) {
			try {
				System.out.print("Input PIN here: ");
				String pin = br.readLine();

				accessToken = twitter.getOAuthAccessToken(requestToken, pin);

			} catch (TwitterException te) {

				System.out.println("Failed to get access token, caused by: " + te.getMessage());

				System.out.println("Retry input PIN");

			}
		}

		System.out.println("Access Token: " + accessToken.getToken());
		System.out.println("Access Token Secret: " + accessToken.getTokenSecret());

		twitter.updateStatus("First Tweet using skillblazer application #LlamasForLife");

	}
} //end of TwitterIntegration class
