/************************************************************
 * Application Name: skillblazer
 * File Name: TwitterIntegration.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/10/2018
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
import java.io.IOException;
import java.io.InputStreamReader;
/*import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;*/
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterIntegration {
	
	//Consumer key token for allowing application to access Twitter
	private final static String CONSUMER_KEY = "";
	//Consumer secret token for allowing application to access Twitter
	private final static String CONSUMER_KEY_SECRET = "";
	
	@SuppressWarnings("unused")
	private boolean isEnabled; //determines if Twitter option was selected

	//Integrating window generation for Twitter here. Special 
	//case for this integration. Primary GUI will call this
	//and present the secondary Twitter window.
	
	/*
	 * public static void display(String title, String message) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		
		Label label1 = new Label();
		label1.setText(message);
		Button closeButton = new Button ("Close the window");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label1, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}*/
	
	private void readUserTokensFromFile() {
		//read user tokens from designated file
	}

	/*
	 * 
	 */
	private void start() throws TwitterException, IOException {

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
	
	/*
	 * 
	 */
	public void enableTwitterIntegration(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public static void main(String[] args) throws Exception {
		new TwitterIntegration().start();// run the Twitter client
	}
}
